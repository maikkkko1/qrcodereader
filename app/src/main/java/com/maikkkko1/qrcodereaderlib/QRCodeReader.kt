package com.maikkkko1.qrcodereaderlib

import android.hardware.Camera
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_scanner.*
import me.dm7.barcodescanner.core.CameraUtils
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * Created by Maikon Ferreira on 16/09/20.
 */

class QRCodeReader : Fragment(), ZXingScannerView.ResultHandler {
    var onResult: ((resultText: String?) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onResume() {
        super.onResume()
        scanner.setResultHandler(this)
        scanner.startCamera()
    }

    override fun onPause() {
        super.onPause()

        closeScanner()
    }

    override fun handleResult(rawResult: Result) {
        onResult?.invoke(rawResult.text)

        scanner.resumeCameraPreview(this)
    }

    fun closeScanner() {
        scanner.stopCamera()

        val camera = CameraUtils.getCameraInstance()

        if (camera != null) {
            (camera as Camera).release()
        }
    }
}