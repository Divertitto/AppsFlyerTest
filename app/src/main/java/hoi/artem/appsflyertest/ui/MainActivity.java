package hoi.artem.appsflyertest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import hoi.artem.appsflyertest.AFApplication;
import hoi.artem.appsflyertest.R;

public class MainActivity extends AppCompatActivity implements AFApplication.AppsFlyerDataListener {

    private Map<String, Object> conversionData;
    private TextView logTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AFApplication application = (AFApplication) getApplication();
        conversionData = application.getConversionData();
        application.setAppsFlyerDataListener(this);

        setupUI();
        showConversionData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((AFApplication) getApplication()).setAppsFlyerDataListener(null);
    }

    private void setupUI() {
        logTextView = findViewById(R.id.logTextView);
    }

    private void showConversionData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (Map.Entry<String, Object> entry : conversionData.entrySet()) {
            stringBuilder.append("\"")
            .append(entry.getKey())
            .append("\" = ")
            .append(entry.getValue())
            .append(";\n");
        }
        stringBuilder.append("}");
        logTextView.post(() -> logTextView.setText(stringBuilder.toString()));
    }

    @Override
    public void notifyDataAdded() {
        showConversionData();
    }
}
