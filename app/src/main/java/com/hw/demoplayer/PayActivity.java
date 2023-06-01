package com.hw.demoplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.hw.demoplayer.network.API;
import com.hw.demoplayer.network.DefaultObservers;
import com.hw.demoplayer.network.PayResponse;
import com.hw.demoplayer.network.RetrofitUtil;
import com.hw.demoplayer.utils.LogUtil;

public class PayActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        init();
        getPayH5url(); //获取支付平台H5页面地址
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void init() {
        mWebView = findViewById(R.id.web_container);
        mProgressBar = findViewById(R.id.view_progressBar);
        mWebView.setFocusable(true);
        mWebView.requestFocus();
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(this, "android");
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    private void getPayH5url() {
        RetrofitUtil.hull(RetrofitUtil.createService(API.class).getPayUrl(
                "email", "strType", "timeBean", "startTime"
                        , "timeBean", "endTime"
                        , "hideBuy", 0, 666, "", "", ""))
                .subscribe(new DefaultObservers<PayResponse<String>>() {
                    @Override
                    public void onResponse(PayResponse<String> data) {
                        if (data != null) {
                            LogUtil.d("PayUrl ==> "+data.data);
                            mWebView.loadUrl(data.data);
                        }
                    }

                    @Override
                    public int onFailure(String code, String msg) {
                        LogUtil.d("onFailure! ==> Code: " + code + ", errorMsg: " + msg);
                        return super.onFailure(code, msg);
                    }
                });
    }
}