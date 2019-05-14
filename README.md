# Character Recognition Project

## How to build from scratch

### Environment

#### Python (Jupyter notebook)

- Python 3.7, TensorFlow 2.0, Anaconda+, sklearn, numpy, pandas, matplotlib

#### Android

- TensorflowLite+, finger-paint-view 0.10

### Step 1. Train and convert the model to TensorFlow Lite

Run all the code cells in CharacterRecognition.ipynb.

- If you are running Jupyter Notebook locally, a `semeion.tflite` file will be saved to the project directory.

### Step 2. Build Android app

Copy the `semeion.tflite` generated in Step 1 to `/android/app/src/main/assets`, then build and run the app.

```
implementation 'org.tensorflow:tensorflow-lite:1.13.1'
```

If you are building your own app, remember to add the following code to build.gradle to prevent compression for model files.

```
aaptOptions {
    noCompress "tflite"
}
```

## Credits

- The basic model architecture comes from [tensorflow-mnist-tutorial](https://github.com/GoogleCloudPlatform/tensorflow-without-a-phd/tree/master/tensorflow-mnist-tutorial).
- The official TensorFlow Lite [examples](https://github.com/tensorflow/examples/tree/master/lite/examples).
- The [FingerPaint](https://android.googlesource.com/platform/development/+/master/samples/ApiDemos/src/com/example/android/apis/graphics/FingerPaint.java) from Android API demo.
- The github project of [nex3z](https://github.com/nex3z/tflite-mnist-android) 

