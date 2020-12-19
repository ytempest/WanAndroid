package com.ytempest.wanandroid.activity.main.home.article;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytempest.tool.adapter.CoreRecyclerAdapter;
import com.ytempest.tool.adapter.CoreViewHolder;
import com.ytempest.tool.helper.ActivityLauncher;
import com.ytempest.tool.util.ToastUtils;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.activity.article.ArticleDetailActivity;
import com.ytempest.wanandroid.activity.login.LoginActivity;
import com.ytempest.wanandroid.activity.main.home.HomePresenter;
import com.ytempest.wanandroid.http.bean.ArticleDetailBean;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;
import com.ytempest.wanandroid.utils.DateFormat;

/**
 * @author heqidu
 * @since 2020/12/17
 */
public class HomeArticleAdapter extends CoreRecyclerAdapter<HomeArticleBean.DatasBean> {

    private static final String TAG = HomeArticleAdapter.class.getSimpleName();

    private final HomePresenter mPresenter;

    public HomeArticleAdapter(HomePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected CoreViewHolder onCreateView(LayoutInflater inflater, ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.item_home_article_content, viewGroup, false);
        CoreViewHolder holder = new CoreViewHolder(view);
        holder.setNeedClick(true);
        return holder;
    }

    private final View.OnClickListener mOnCollectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mPresenter.isUserLogin()) {
                HomeArticleBean.DatasBean data = (HomeArticleBean.DatasBean) view.getTag();
                mPresenter.updateArticleCollectStatus(data);

            } else {
                Context context = view.getContext();
                ToastUtils.show(context, R.string.login_please);
                ActivityLauncher.startActivity(context, new Intent(context, LoginActivity.class));
            }

        }
    };

    @Override
    protected void onBindData(CoreViewHolder holder, HomeArticleBean.DatasBean data, int position) {
        holder.setTag(data);
        // TODO  heqidu: 优化 holder
        TextView userView = holder.getViewById(R.id.tv_item_home_article_user);
        String user = data.getShareUser();
        user = TextUtils.isEmpty(user) ? userView.getResources().getString(R.string.anonymous_develop) : user;
        userView.setText(user);
        TextView classifyView = holder.getViewById(R.id.tv_item_home_article_classify);
        classifyView.setText(data.getSuperChapterName() + "/" + data.getChapterName());
        TextView contentView = holder.getViewById(R.id.tv_item_home_article_content);
        contentView.setText(data.getTitle());

        TextView timeView = holder.getViewById(R.id.tv_item_home_article_time);
        timeView.setText(DateFormat.format(data.getShareDate()));

        ImageView collectView = holder.getViewById(R.id.iv_item_home_article_collect);
        collectView.setImageResource(data.isCollect() ? R.drawable.ic_collect_select : R.drawable.ic_collect_normal_gray);
        collectView.setTag(data);
        collectView.setOnClickListener(mOnCollectClickListener);
    }

    @Override
    protected void onItemClick(CoreViewHolder holder, View view, int position) {
        super.onItemClick(holder, view, position);
        HomeArticleBean.DatasBean data = (HomeArticleBean.DatasBean) holder.getTag();
        ArticleDetailActivity.start(view.getContext(), ArticleDetailBean.from(data));
    }
}
