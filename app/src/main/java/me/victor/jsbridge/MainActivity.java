package me.victor.jsbridge;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import wendu.dsbridge.DWebView;
import wendu.dsbridge.OnReturnValue;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initWebView() {
        final DWebView webView = (DWebView) findViewById(R.id.web_view);
        webView.setJavascriptInterface(new JsApi());
        webView.clearCache(true);
        webView.loadUrl("file:///android_asset/test.html");
        // 官方原生异步执行javascript
        webView.evaluateJavascript("function t(p) {alert(p);return true;};t('aaa');", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                System.out.println(value);
            }
        });
        // 官方原生同步执行javascript
        webView.loadUrl("javascript:function t(p) {alert(p);}t('a');");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 官方原生动态注入javascript
                webView.loadUrl("javascript:function t(p) {alert(p);callAsyn();}");
                webView.loadUrl("javascript:t('bbb');");
                webView.callHandler("addValue", new Object[]{1, "hello"}, new OnReturnValue() {
                    @Override
                    public void onValue(String retValue) {
                        Log.d("jsbridge", "call succeed,return value is " + retValue);
                    }
                });
            }
        });
    }
}
