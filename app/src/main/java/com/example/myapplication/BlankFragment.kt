package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import com.example.myapplication.databinding.FragmentBlankBinding


class BlankFragment : Fragment(R.layout.fragment_blank) {
    private lateinit var binding: FragmentBlankBinding
    private var counter: Int = 0
    private var titleText: String? = null
    private var messageSmallText: String? = null
    private var messageBigText: String? = null
    private var alltimetext: String? = null


    private var timeUntilShownInSeconds: Int = 0
    private var isCheckBoxChecked = false

    private var isTitleEmpty = true
    private var isMessageSmallEmpty = true
    private var isMessageBigEmpty = true
    private var isAllTimeEmpty = true


    private var allTime: Long = 0
    private var alarmManager: AlarmManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentBlankBinding.bind(view)


        super.onViewCreated(view, savedInstanceState)
        btnlistners()
        with(binding) {
            eTtitleText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (Validate(eTtitleText.text.toString())) {
                        isTitleEmpty = false
                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
            })
        }
        with(binding) {
            eTmessageSmallText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (Validate(eTmessageSmallText.text.toString())) {
                        isMessageSmallEmpty = false
                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
            })
        }
        with(binding) {
            eTallTime.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (Validate(eTallTime.text.toString())) {
                        isAllTimeEmpty = false
                    }
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
            })
        }
    }

    private fun Validate(text: String): Boolean {
        return text.isNullOrEmpty()
    }


    private fun checkIfAllNotEmpty() {
        binding.notifai.isEnabled =
            !isTitleEmpty && !isMessageSmallEmpty && !isMessageBigEmpty
    }


    private fun setNotifaiAlarm(
        timeUntilShownInSeconds: Int,
        titleText: String?,
        messageSmallText: String?,
        messageBigText: String?
    ) {
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

        val pendingIntent = getPendingIntent(titleText, messageSmallText, messageBigText)

        allTime = SystemClock.elapsedRealtime() + timeUntilShownInSeconds * 1000


        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            allTime,
            pendingIntent
        )


    }

    private fun getPendingIntent(
        titleText: String?,
        messageSmallText: String?,
        messageBigText: String?
    ): PendingIntent =
        Intent(requireContext(), AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.TITLE_TEXT, titleText)
            putExtra(AlarmReceiver.SMALL_TEXT, messageSmallText)
            putExtra(AlarmReceiver.BIG_TEXT, messageBigText)
        }.let { intent ->
            PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

    private fun btnlistners() {
        with(binding) {
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                eTmessageBigText.isEnabled = isChecked
                isCheckBoxChecked = isChecked
            }
            notifai.setOnClickListener {
                titleText = eTtitleText.text.toString()
                messageSmallText = eTmessageSmallText.text.toString()
                messageBigText = if (isCheckBoxChecked) {
                    eTmessageBigText.text.toString()
                } else {
                    null
                }

                timeUntilShownInSeconds = Integer.valueOf(eTallTime.text.toString())

                setNotifaiAlarm(
                    titleText,
                    messageSmallText,
                    messageBigText
                )


            }
            cancelNotify.setOnClickListener{
                val timediff = if(allTime != 0L) {(allTime - SystemClock.elapsedRealtime())/1000} else{}
                if(allTime > 0L) {
                    alarmManager?.cancel(getPendingIntent(
                        titleText,
                        messageSmallText,
                        messageBigText

                    ))
                }

                }
            }





    }


}