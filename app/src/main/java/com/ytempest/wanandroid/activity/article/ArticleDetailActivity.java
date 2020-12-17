package com.ytempest.wanandroid.activity.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.tool.helper.ActivityLauncher;
import com.ytempest.tool.util.IntentUtils;
import com.ytempest.tool.util.LogUtils;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.activity.login.LoginActivity;
import com.ytempest.wanandroid.base.activity.MvpActivity;
import com.ytempest.wanandroid.http.bean.ArticleDetailBean;
import com.ytempest.wanandroid.utils.JSON;
import com.ytempest.wanandroid.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * @author heqidu
 * @since 2020/12/15
 */
@InjectLayout(R.layout.activity_article_detail)
public class ArticleDetailActivity extends MvpActivity<ArticleDetailPresenter> implements IArticleDetailContract.View {

    private static final String TAG = ArticleDetailActivity.class.getSimpleName();
    private static final String KEY_ARTICLE_DETAIL = "key_article_detail";

    public static void start(Context context, ArticleDetailBean bean) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(KEY_ARTICLE_DETAIL, bean.toJson());
        ActivityLauncher.startActivity(context, intent);
    }

    @BindView(R.id.toolbar_article_detail)
    Toolbar mToolbar;
    @BindView(R.id.webView_article_detail_content)
    WebView mWebView;
    private ArticleDetailBean mArticleDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);
        String json = IntentUtils.getString(getIntent(), KEY_ARTICLE_DETAIL, null);
        mArticleDetail = ArticleDetailBean.from(json);
        LogUtils.d(TAG, "onCreate: 文章信息：" + JSON.toJson(mArticleDetail));

        // 标题要在setSupportActionBar()前设置
        mToolbar.setTitle(mArticleDetail.getTitle());
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);
        refreshCollectArticleView(mArticleDetail.isCollected());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_article_detail_collect:
                onArticleCollectClick(item);
                break;
            case R.id.item_article_detail_share:
                // TODO  heqidu: 分享文章
                break;
            case R.id.item_article_detail_browser:
                // TODO  heqidu: 在浏览器打开
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onArticleCollectClick(MenuItem item) {
        if (mPresenter.isUserLogin()) {
            boolean isCollected = !item.isChecked();
            mPresenter.updateArticleCollectStatus(isCollected, mArticleDetail.getArticleId());

        } else {
            ActivityLauncher.startActivity(this, new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onArticleCollectSuccess(boolean isCollect, long articleId) {
        refreshCollectArticleView(isCollect);
    }

    private void refreshCollectArticleView(boolean isCollect) {
        MenuItem item = mToolbar.getMenu().findItem(R.id.item_article_detail_collect);
        item.setIcon(isCollect ? R.drawable.ic_collect_select : R.drawable.ic_collect_normal);
        item.setChecked(isCollect);
    }

    @Override
    public void onArticleCollectFail(boolean isCollect, long articleId, int errCode) {
        showToast(isCollect ? R.string.collect_fail : R.string.cancel_fail);
    }
}
