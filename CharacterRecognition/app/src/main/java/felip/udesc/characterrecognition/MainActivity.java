package felip.udesc.characterrecognition;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.fingerpaintview.FingerPaintView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.reset)
    protected Button resetButton;
    @BindView(R.id.classify)
    protected Button classifyButton;
    @BindView(R.id.log)
    protected TextView result_view;

    @BindView(R.id.fpv_paint)
    protected FingerPaintView board;

    private Classifier mClassifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        try {
            mClassifier = new Classifier(this);
        } catch (IOException e) {
            Toast.makeText(this, R.string.failed_to_create_classifier, Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "init(): Failed to create Classifier", e);
        }
    }

    @OnClick(R.id.reset)
    void reset() {
        board.clear();
        result_view.setText(R.string.reset_result);
    }

    @OnClick(R.id.classify)
    void onClassify() {
        if (mClassifier == null) {
            Log.e(LOG_TAG, "onDetectClick(): Classifier is not initialized");
            return;
        } else if (board.isEmpty()) {
            Toast.makeText(this, R.string.please_write_a_digit, Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap image = board.exportToBitmap(
                Classifier.IMG_WIDTH, Classifier.IMG_HEIGHT);
        Result result = mClassifier.classify(image);
        renderResult(result);
    }

    private void renderResult(Result result) {
        String result_text = result.getResult() + " - " + result.getProbability();
        result_view.setText(result_text);
    }
}

