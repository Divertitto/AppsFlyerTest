package hoi.artem.appsflyertest;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

import static hoi.artem.appsflyertest.utils.Constants.AF_DEV_KEY;

public class AFApplication extends Application {

    private Map<String, Object> conversionData = new HashMap<>();
    private AppsFlyerDataListener appsFlyerDataListener;

    @Override
    public void onCreate() {
        super.onCreate();

        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {

            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                AFApplication.this.conversionData.putAll(conversionData);
                if(appsFlyerDataListener != null) {
                    appsFlyerDataListener.notifyDataAdded();
                }

                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("LOG_TAG", "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);
    }

    public Map<String, Object> getConversionData() {
        return conversionData;
    }

    public void setAppsFlyerDataListener(AppsFlyerDataListener appsFlyerDataListener) {
        this.appsFlyerDataListener = appsFlyerDataListener;
    }

    public interface AppsFlyerDataListener {
        void notifyDataAdded();
    }
}
