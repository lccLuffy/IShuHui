package lcc.ishuhui.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lcc.stateLayout.StateLayout;

import butterknife.Bind;
import lcc.ishuhui.R;
import lcc.ishuhui.constants.API;
import lcc.ishuhui.manager.ChapterListManager;
import lcc.ishuhui.model.ChapterListModel;
import lcc.ishuhui.utils.JsonUtil;
import lcc.ishuhui.utils.PreferencesUtil;

public class WebActivity extends BaseActivity {

    public static final String URL = "URL";
    public static final String TITLE = "TITLE";
    public static final String CHAPTER_NUM = "CHAPTER_NUM";
    public static final String BOOK_ID = "BOOK_ID";

    String mUrl, title;
    int chapterNo;

    @Bind(R.id.webView)
    WebView webView;

    @Bind(R.id.stateLayout)
    StateLayout stateLayout;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUrl = getIntent().getStringExtra(URL);
        title = getIntent().getStringExtra(TITLE);
        chapterNo = getIntent().getIntExtra(CHAPTER_NUM, 0);

        setTitle("第" + chapterNo + "章 " + title);

        stateLayout.setErrorAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new ViewClient());

        webView.clearHistory();
        webView.loadUrl(mUrl);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_copy:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(title, mUrl));
                toast(title + " 的图片地址已复制");
                return true;
            case R.id.action_share_link:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, mUrl);
                i.setType("text/plain");
                startActivity(i);
                return true;
            case R.id.action_bower:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mUrl));
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                webView.reload();
                return true;
            case R.id.action_next_chapter:

                ChapterListModel.ReturnEntity.Chapter chapter = ChapterListManager.instance().nextChapter();

                if (chapter != null) {
                    mUrl = API.URL_IMG_CHAPTER + chapter.Id;
                    title = chapter.Title;
                    chapterNo = chapter.ChapterNo;
                    setTitle("第" + chapterNo + "章 " + title);
                    webView.loadUrl(mUrl);
                    PreferencesUtil.getInstance().start()
                            .put("book" + chapter.BookId, JsonUtil.getInstance().toJson(chapter))
                            .put("book_chapter_" + chapter.BookId, chapter.ChapterNo)
                            .commit();
                } else {
                    toast("没有了");
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null)
            webView.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null)
            webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null)
            webView.onResume();
    }
    public static void jumpToMe(Context context, ChapterListModel.ReturnEntity.Chapter chapter) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WebActivity.URL, API.URL_IMG_CHAPTER + chapter.Id);
        intent.putExtra(WebActivity.TITLE, chapter.Title);
        intent.putExtra(WebActivity.CHAPTER_NUM, chapter.ChapterNo);
        context.startActivity(intent);
    }
    private class ChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }



        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100 && stateLayout != null) {
                stateLayout.showContentView();
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

            AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
            builder.setTitle("温馨提示");
            builder.setMessage(message)
                    .show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
            builder.setMessage(message)
                    .setTitle("提示")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    }).show();
            return true;
        }
    }

    private class ViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (stateLayout != null)
                stateLayout.showProgressView();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (stateLayout != null)
                stateLayout.showContentView();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            errorHappen();
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            errorHappen();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            errorHappen();
        }

        private void errorHappen() {
            stateLayout.showErrorView();
        }
    }
}
