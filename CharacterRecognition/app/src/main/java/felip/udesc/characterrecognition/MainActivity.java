package felip.udesc.characterrecognition;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {

    protected Button resetButton;
    protected DrawingView drawingView;
    protected ImageView bitmapTester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        resetButton = findViewById(R.id.reset);
        resetButton.setOnClickListener(v -> reset());

        drawingView = findViewById(R.id.drawingView);
        bitmapTester = findViewById(R.id.bitmapTester);
    }


    private void reset() {
        drawingView.reset();
        bitmapTester.setVisibility(View.GONE);
        drawingView.setVisibility(View.VISIBLE);
    }

//    private void loadModel() {
//        executor.execute(() -> {
//            try {
//                classifier = Classifier.create(getApplicationContext().getAssets(),
//                        MODEL_FILE,
//                        LABEL_FILE,
//                        INPUT_SIZE,
//                        INPUT_NAME,
//                        OUTPUT_NAME);
//            } catch (final Exception e) {
//                throw new RuntimeException("Error initializing TensorFlow!", e);
//            }
//        });
//    }
}
