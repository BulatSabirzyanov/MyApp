package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.myapplication.databinding.FragmentBlankBinding
import java.util.concurrent.TimeUnit


class BlankFragment : Fragment(R.layout.fragment_blank) {
    private lateinit var binding: FragmentBlankBinding
    private var counter: Int = 0
    private var titleText: String? = null
    private var messageSmallText: String? = null
    private var messageBigText: String? = null
    private var alltimetext: String? = null


    private var timeUntilShownInSeconds: Int = 0
    private var isCheckBoxChecked = false

    private var isTitleNotEmpty = false
    private var isMessageSmallNotEmpty = false
    private var isMessageBigEmpty = false
    private var isAllTimeNotEmpty = false


    private var allTime: Long = 0
    private var alarmManager: AlarmManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentBlankBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            eTtitleText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    checkIfAllNotEmpty()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isTitleNotEmpty = p0?.isNullOrBlank() != true



                }
            })
            eTmessageSmallText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    checkIfAllNotEmpty()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isMessageSmallNotEmpty = p0?.isNullOrBlank() != true
                }
            })
            eTallTime.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    checkIfAllNotEmpty()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isAllTimeNotEmpty = p0?.isNullOrBlank() != true
                }
            })
        }
        btnlistners()
    }

    private fun Validate(text: String): Boolean {
        return text.isNullOrEmpty()
    }

    private fun checkIfAllNotEmpty() {
        println(isTitleNotEmpty)
        println(isAllTimeNotEmpty)
        println(isMessageSmallNotEmpty)
        binding.notifai.isEnabled =
            isTitleNotEmpty && isMessageSmallNotEmpty && isAllTimeNotEmpty
    }

    private fun setNotifaiAlarm(
        timeUntilShownInSeconds: Int,
        titleText: String?,
        messageSmallText: String?,
        messageBigText: String?
    ) {
        alarmManager = activity?.baseContext?.getSystemService(ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntent(titleText, messageSmallText, messageBigText)
        allTime = SystemClock.elapsedRealtime() + 1
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
        Intent(activity?.baseContext, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.TITLE_TEXT, titleText)
            putExtra(AlarmReceiver.SMALL_TEXT, messageSmallText)
            putExtra(AlarmReceiver.BIG_TEXT, messageBigText)
        }.let { intent ->
            PendingIntent.getBroadcast(
                activity?.baseContext,
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
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
                    timeUntilShownInSeconds,
                    titleText,
                    messageSmallText,
                    messageBigText
                )


            }
            cancelNotify.setOnClickListener {
                val timediff = if (allTime != 0L) {
                    (allTime - SystemClock.elapsedRealtime()) / 1000
                } else {
                    0
                }
                if (allTime > 0L) {
                    alarmManager?.cancel(
                        getPendingIntent(
                            titleText,
                            messageSmallText,
                            messageBigText
                        )
                    )

                }
            }
            resetTime.setOnClickListener {
                val timeInMillis = SystemClock.uptimeMillis()
                val time = String.format(
                    "02%d min, %02d sec ",
                    TimeUnit.MILLISECONDS.toMinutes(timeInMillis),
                    TimeUnit.MILLISECONDS.toSeconds(timeInMillis) -
                            TimeUnit.MILLISECONDS.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    timeInMillis
                                )
                            )
                )
                Toast.makeText(requireContext(), time, Toast.LENGTH_SHORT).show()
            }

        }


    }
}