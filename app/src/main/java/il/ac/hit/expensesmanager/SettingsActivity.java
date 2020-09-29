package il.ac.hit.expensesmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.WebView;

import il.ac.hit.expensesmanager.Exceptions.ManagerExpensesException;

/**
 * Represents Settings Activity
 */
public class SettingsActivity extends Activity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            IBridgeJavaJS bridgeJavaJS = new BridgeJavaJS(getBaseContext(), this);
            ISettingsBridgeJavaJS settingsBridgeJavaJS = new SettingsBridgeJavaJS(this);
            webView = new WebView(this);
            webView.loadUrl("file:///android_asset/settings.html");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.addJavascriptInterface(bridgeJavaJS, getResources().getString(R.string.web_bridge));
            webView.addJavascriptInterface(settingsBridgeJavaJS, getResources().getString(R.string.web_bridge_settings));
            setContentView(webView);
        }catch (ManagerExpensesException ce){

            new AlertDialog.Builder(getBaseContext())
                    .setTitle("Error!")
                    .setMessage(ce.getMessage())
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }
}
