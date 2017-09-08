package me.victor.jsbridge;

import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

import wendu.dsbridge.CompletionHandler;

public class JsApi {
    // for synchronous invocation
    @JavascriptInterface
    String testSyn(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("msg") + "［syn call］";
    }

    // for asynchronous invocation
    @JavascriptInterface
    void testAsyn(JSONObject jsonObject, CompletionHandler handler) throws JSONException {
        handler.complete(jsonObject.getString("msg") + " [asyn call]");
    }

    @JavascriptInterface
    String testNoArgSyn(JSONObject jsonObject) {
        return "［no arg syn call］";
    }

    @JavascriptInterface
    void testNoArgAsyn(JSONObject jsonObject, CompletionHandler handler) {
        handler.complete("［no arg asyn call］");
    }
}
