package com.ytempest.wanandroid.activity.main.project.content.list;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ytempest.tool.adapter.CoreRecyclerAdapter;
import com.ytempest.tool.adapter.CoreViewHolder;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.activity.article.ArticleDetailActivity;
import com.ytempest.wanandroid.activity.main.project.content.ProjectContentPresenter;
import com.ytempest.wanandroid.http.bean.ArticleDetailBean;
import com.ytempest.wanandroid.http.bean.ProjectContentBean;
import com.ytempest.wanandroid.utils.DateFormat;
import com.ytempest.wanandroid.utils.ImgLoader;

/**
 * @author heqidu
 * @since 2020/12/25
 */
public class ContentListAdapter extends CoreRecyclerAdapter<ProjectContentBean.DatasBean> {

    private final ProjectContentPresenter mPresenter;
    private final View.OnClickListener mCollectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProjectContentBean.DatasBean data = (ProjectContentBean.DatasBean) v.getTag();
            mPresenter.addProjectArticleCollect(data);
        }
    };

    public ContentListAdapter(ProjectContentPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected CoreViewHolder onCreateView(LayoutInflater inflater, ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.item_project_content_list, viewGroup, false);
        CoreViewHolder holder = new CoreViewHolder(view);
        holder.setNeedClick(true);
        return holder;
    }

    @Override
    protected void onBindData(CoreViewHolder holder, ProjectContentBean.DatasBean data, int position) {
        ImageView imgView = holder.getViewById(R.id.iv_item_project_content_img);
        ImgLoader.loadTo(imgView, data.getEnvelopePic());

        String user = data.getShareUser();
        user = TextUtils.isEmpty(user) ? holder.itemView.getResources().getString(R.string.anonymous_develop) : user;
        holder.setText(R.id.tv_item_project_content_user, user);
        holder.setText(R.id.tv_item_project_content_title, data.getTitle());
        holder.setText(R.id.tv_item_project_content_desc, data.getDesc());
        holder.setText(R.id.tv_item_project_content_time, DateFormat.format(data.getPublishTime()));

        // TODO : ProjectContentBean.DatasBean#isCollect()这个方法未能获取正确的收藏状态，需要等服务器支持后再做处理
        View collectView = holder.getViewById(R.id.tv_item_project_content_collect);
        collectView.setTag(data);
        collectView.setOnClickListener(mCollectClickListener);
    }

    @Override
    protected void onItemClick(CoreViewHolder holder, View view, int position) {
        super.onItemClick(holder, view, position);
        ProjectContentBean.DatasBean data = getData(position);
        ArticleDetailActivity.start(view.getContext(), ArticleDetailBean.from(data));
    }
}
