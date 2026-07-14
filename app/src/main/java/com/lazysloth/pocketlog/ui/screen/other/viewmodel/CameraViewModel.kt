package com.lazysloth.pocketlog.ui.screen.other.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.view.CameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.lazysloth.pocketlog.data.Transaction
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class CameraViewModel(
    val geminiModel: GeminiModel
) : ViewModel() {

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest = _surfaceRequest.asStateFlow()
    private var surfaceMeteringPointFactory: SurfaceOrientedMeteringPointFactory? = null
    private var cameraControl: CameraControl? = null
    private lateinit var cameraController: CameraController
    private val _bitmap = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmap = _bitmap.asStateFlow()
    var isLoading by mutableStateOf(false)
    var response by mutableStateOf("")
    var geminiFinished by mutableStateOf(false)

    private var imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        .build()
    var imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build();

    fun onTakePhoto(bitmap: Bitmap) {
        Log.d("CameraBit", "Photo received")
        _bitmap.value += bitmap
        Log.d(
            "Bitmap",
            "width=${bitmap.width}, height=${bitmap.height}"
        )
        Log.d("Camera", "Bitmap count = ${_bitmap.value.size}")
    }

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            Log.d(
                "Camera",
                "Preview Resolution = ${newSurfaceRequest.resolution}"
            )
            _surfaceRequest.update { newSurfaceRequest }
            surfaceMeteringPointFactory = SurfaceOrientedMeteringPointFactory(
                newSurfaceRequest.resolution.width.toFloat(),
                newSurfaceRequest.resolution.height.toFloat()
            )

        }
    }
    var prompt by mutableStateOf("")

    // function for text recognition with ml kit
    @OptIn(ExperimentalGetImage::class)
    private fun processImage(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val detectedText = visionText.text
                    prompt = detectedText

                }
                .addOnFailureListener { e ->
                    Log.e("MLKit", "Text recognition failed", e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }

        }
    }

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        val executor = ContextCompat.getMainExecutor(appContext)
        imageAnalysis.setAnalyzer(executor) { imageProxy ->
            processImage(imageProxy)

        }


        val camera = processCameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
            cameraPreviewUseCase,
            imageCapture,
            imageAnalysis
        )
        cameraControl = camera.cameraControl


        // Cancellation signals we're done with the camera
        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
            cameraControl = null
        }
    }

    fun tapToFocus(tapCoords: Offset) {
        val point = surfaceMeteringPointFactory?.createPoint(tapCoords.x, tapCoords.y)
        if (point != null) {
            val meteringAction = FocusMeteringAction.Builder(point).build()
            cameraControl?.startFocusAndMetering(meteringAction)
        }
    }


    fun capturePhoto(
        context: Context
    ) {
        isLoading = true
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    imageAnalysis.clearAnalyzer()
                    image.use { image ->
                        onTakePhoto(image.toBitmap())
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "couldn't take photo", exception)
                }
            }
        )
        viewModelScope.launch {
            geminiModel.analyze(prompt)
            Log.d("Gemini", "gemini finished")
            geminiFinished = true
        }
    }
}


