package felip.udesc.characterrecognition;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

import org.tensorflow.lite.Interpreter;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;


class Classifier {
    private static final String LOG_TAG = Classifier.class.getSimpleName();

    private static final String MODEL_NAME = "converted_model.tflite";

    private static final int BATCH_SIZE = 1;
    static final int IMG_HEIGHT = 16;
    static final int IMG_WIDTH = 16;
    private static final int NUM_CHANNEL = 1;
    private static final int NUM_CLASSES = 10;

    private final Interpreter interpreter;
    private final ByteBuffer imageData;
    private final int[] imagePixels = new int[IMG_HEIGHT * IMG_WIDTH];
    private final float[][] result = new float[1][NUM_CLASSES];

    Classifier(Activity activity) throws IOException {
        Interpreter.Options options = new Interpreter.Options();
        interpreter = new Interpreter(loadModelFile(activity), options);
        imageData = ByteBuffer.allocateDirect(
                4 * BATCH_SIZE * IMG_HEIGHT * IMG_WIDTH * NUM_CHANNEL);
        imageData.order(ByteOrder.nativeOrder());
    }

    Result classify(Bitmap bitmap) {
        convertBitmapToByteBuffer(bitmap);
        long startTime = SystemClock.uptimeMillis();
        interpreter.run(imageData, result);
        long endTime = SystemClock.uptimeMillis();
        long timeCost = endTime - startTime;
        Log.v(LOG_TAG, "classify(): result = " + Arrays.toString(result[0])
                + ", timeCost = " + timeCost);
        return new Result(result[0]);
    }

    private MappedByteBuffer loadModelFile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_NAME);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private void convertBitmapToByteBuffer(Bitmap bitmap) {
        if (imageData == null) {
            return;
        }
        imageData.rewind();

        bitmap.getPixels(imagePixels, 0, bitmap.getWidth(), 0, 0,
                bitmap.getWidth(), bitmap.getHeight());

        int pixel = 0;
        for (int i = 0; i < IMG_WIDTH; ++i) {
            for (int j = 0; j < IMG_HEIGHT; ++j) {
                int value = imagePixels[pixel++];
                imageData.putFloat(convertPixel(value));
            }
        }
    }

    private static float convertPixel(int color) {
        return (255 - (((color >> 16) & 0xFF) * 0.299f
                + ((color >> 8) & 0xFF) * 0.587f
                + (color & 0xFF) * 0.114f)) / 255.0f;
    }
}

