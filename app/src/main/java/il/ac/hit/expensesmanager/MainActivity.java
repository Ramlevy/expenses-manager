package il.ac.hit.expensesmanager;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.WebView;

import il.ac.hit.expensesmanager.Exceptions.ManagerExpensesException;

//TODO: Make the gui look better
//TODO: Bonuses...

//TODO: extract css style to external file

/**
 * Represents MainActivity of ExpensesManager
 */
public class MainActivity extends Activity {

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            IBridgeJavaJS bridgeJavaJS = new BridgeJavaJS(this, this);
            bridgeJavaJS.getCategories();

            webView = new WebView(this);
            webView.loadUrl("file:///android_asset/index.html");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.addJavascriptInterface(bridgeJavaJS, getResources().getString(R.string.web_bridge));
            setContentView(webView);
        } catch (ManagerExpensesException ce) {

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

    /**
     * When back pressed change page
     */
    @Override
    public void onBackPressed() {
        webView.loadUrl("javascript:indexRouting.back()");
    }

}
