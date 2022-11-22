package com.example.myapplication


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentSecondBinding


class SecondFragment() : Fragment(R.layout.fragment_second) {
    private lateinit var binding: FragmentSecondBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSecondBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnCamera.setOnClickListener {
                val permission = android.Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(
                        requireActivity(),
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(permission),
                        REQUEST_CAMERA_PERMISSION_CODE
                    )

                }

            }
        }
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.i("test", "231")
            if (it.resultCode == Activity.RESULT_OK) {
                Log.i("test", "228")
                Toast.makeText(context, "nice photo", Toast.LENGTH_SHORT)
            }
        }

    private fun openCamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
//        if (activity?.let { intent.resolveActivity(it.packageManager) } != null)
//        getResult.launch(intent)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION_CODE -> {
                val permission = if (permissions.isNotEmpty()) permissions.first() else return
                if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
//                    openCamera()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION_CODE = 12101
    }

}