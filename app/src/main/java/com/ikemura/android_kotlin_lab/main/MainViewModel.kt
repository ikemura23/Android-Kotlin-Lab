package com.ikemura.android_kotlin_lab.main

import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.ikemura.android_kotlin_lab.navigation.NavigationEvent
import com.ikemura.android_kotlin_lab.navigation.NavigationEventLiveData

class MainViewModel : ViewModel() {
    val navigationEvent = NavigationEventLiveData<Nav>()

    fun detectBarcode(firebaseVisionImage: FirebaseVisionImage) {

        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                    FirebaseVisionBarcode.FORMAT_CODE_128
                )
                .build()

        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        detector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener {
                    var detected = false
                    for (firebaseBarcode in it) {
                        if (detected) return@addOnSuccessListener
                        when (firebaseBarcode.format) {
                            FirebaseVisionBarcode.FORMAT_CODE_128 -> firebaseBarcode.displayValue?.let { barcode ->
                                navigationEvent.value = Nav.GoToNext(barcode)
                                detected = true
                            }
                        }
                    }
                }
                .addOnFailureListener { err ->
                    err.message?.let { navigationEvent.value = Nav.Failure(it) }
                }
    }

    sealed class Nav : NavigationEvent {
        data class GoToNext(val barcodeNumber: String = "") : Nav()
        data class Failure(val message: String) : Nav()
    }
}
