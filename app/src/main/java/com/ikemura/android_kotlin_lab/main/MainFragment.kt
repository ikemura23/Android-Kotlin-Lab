package com.ikemura.android_kotlin_lab.main

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.FrameProcessor
import com.otaliastudios.cameraview.Grid
import com.otaliastudios.cameraview.PictureResult
import kotlinx.android.synthetic.main.main_fragment.cameraView
import kotlinx.android.synthetic.main.main_fragment.framePreview
import kotlinx.android.synthetic.main.main_fragment.imagePreview
import java.nio.ByteBuffer

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        cameraView.open()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCameraView()
    }

    var lastFrameTimeElapsed: Long = 0L
    var previewRate: Long = 0L
    private fun setupCameraView() {
        cameraView.setLifecycleOwner(this)
        cameraView.grid = Grid.DRAW_3X3
        cameraView.addCameraListener(object : CameraListener() {
            override fun onCameraOpened(options: CameraOptions) {
                super.onCameraOpened(options)
                Log.d("Detected", "onCameraOpened")
            }

            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                Log.d("Detected", "onPictureTaken")
                result.toBitmap { bitmap ->
                    bitmap?.let {
                        runBarcodeScanner(it)
                        imagePreview.setImageBitmap(bitmap)
                        showPreview()
                    }
                }
            }
        })
        val frameProcessor = FrameProcessor { frame ->
            val now = System.currentTimeMillis()
            if (now - lastFrameTimeElapsed < previewRate) {
                return@FrameProcessor
            }

            lastFrameTimeElapsed = now
            val metadata = FirebaseVisionImageMetadata.Builder()
                    .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                    .setWidth(frame.size.width)
                    .setHeight(frame.size.height)
                    .setRotation(frame.rotation / 90)
                    .build()
            val byteBuffer: ByteBuffer = ByteBuffer.wrap(frame.data)
            val image = FirebaseVisionImage.fromByteBuffer(byteBuffer, metadata)
            runBarcodeScanner(image)
        }
        cameraView.addFrameProcessor(frameProcessor)
//        cameraView.addFrameProcessor { frame ->
//            val options = BitmapFactory.Options().apply {
//                inJustDecodeBounds = true
//            }
//            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(frame.data, 0, frame.data.size, options)
//            if (bitmap == null) {
//                Log.d("addFrameProcessor", "bitmap == null")
//            }
//            val metadata = FirebaseVisionImageMetadata.Builder()
//                    .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
//                    .setWidth(frame.size.width)
//                    .setHeight(frame.size.height)
//                    .setRotation(frame.rotation / 90)
//                    .build()
//            val firebaseVisionImage = FirebaseVisionImage.fromByteArray(frame.data, metadata)
//
//        }
    }

    private fun runBarcodeScanner(firebaseVisionImage: FirebaseVisionImage) {

        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                    FirebaseVisionBarcode.FORMAT_ALL_FORMATS
                )
                .build()

        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        detector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener {
                    for (firebaseBarcode in it) {
                        Log.d("Detected", it.toString())
                        when (firebaseBarcode.valueType) {
                            FirebaseVisionBarcode.FORMAT_CODE_128 -> firebaseBarcode.displayValue?.let { it1 -> showToast(it1) }
                            else -> firebaseBarcode.displayValue?.let { it1 -> showToast(it1) }
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
//                    Toast.makeText(context, "addOnCompleteListener", Toast.LENGTH_SHORT).show()
                }
    }

    private fun runBarcodeScanner(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Optional : Define what kind of barcodes you want to scan
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                    //Detect all kind of barcodes
                    FirebaseVisionBarcode.FORMAT_ALL_FORMATS
                    //Or specify which kind of barcode you want to detect
                    /*
                        FirebaseVisionBarcode.FORMAT_QR_CODE,
                    FirebaseVisionBarcode.FORMAT_AZTEC
                     */
                )
                .build()

        //Get access to an instance of FirebaseBarcodeDetector
        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        //Use the detector to detect the labels inside the image
        detector.detectInImage(image)
                .addOnSuccessListener {
                    //                    qrList.clear()
//                    adapter.notifyDataSetChanged()
                    // Task completed successfully
                    for (firebaseBarcode in it) {
                        Log.d("Detected", it.toString())
                        when (firebaseBarcode.valueType) {
                            //Handle the URL here
//                            FirebaseVisionBarcode.TYPE_URL ->
//                                qrList.add(QrCode("URL", firebaseBarcode.displayValue))
//                            // Handle the contact info here, i.e. address, name, phone, etc.
//                            FirebaseVisionBarcode.TYPE_CONTACT_INFO ->
//                                qrList.add(QrCode("Contact", firebaseBarcode.contactInfo?.title))
//                            // Handle the wifi here, i.e. firebaseBarcode.wifi.ssid, etc.
//                            FirebaseVisionBarcode.TYPE_WIFI ->
//                                qrList.add(QrCode("WiFi", firebaseBarcode.wifi?.ssid))
//                            // Handle the driver license barcode here, i.e. City, Name, Expiry, etc.
//                            FirebaseVisionBarcode.TYPE_DRIVER_LICENSE ->
//                                qrList.add(QrCode("Driver License", firebaseBarcode.driverLicense?.licenseNumber))
                            FirebaseVisionBarcode.FORMAT_CODE_128 -> firebaseBarcode.displayValue?.let { it1 -> showToast(it1) }
                            //Handle more types
                            else -> firebaseBarcode.displayValue?.let { it1 -> showToast(it1) }
//                                qrList.add(QrCode("Generic", firebaseBarcode.displayValue))
                            //None of the above type was detected, so extract the value from the barcode
                        }
                    }
//                    adapter.notifyDataSetChanged()
//                    progressBar.visibility = View.GONE
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                .addOnFailureListener {
                    // Task failed with an exception
//                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
                    //                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "addOnCompleteListener", Toast.LENGTH_SHORT).show()
                }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        Log.d("Detected", text)
    }

    protected fun showPreview() {
        framePreview.visibility = View.VISIBLE
        cameraView.visibility = View.GONE
    }

    protected fun hidePreview() {
        framePreview.visibility = View.GONE
        cameraView.visibility = View.VISIBLE
    }
}
