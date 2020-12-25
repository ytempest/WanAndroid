package com.ytempest.wanandroid.activity.main.project.content.list;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ytempest.tool.adapter.CoreRecyclerAdapter;
import com.ytempest.tool.adapter.CoreViewHolder;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.http.bean.ProjectContentBean;
import com.ytempest.wanandroid.utils.DateFormat;
import com.ytempest.wanandroid.utils.ImgLoader;

/**
 * @author heqidu
 * @since 2020/12/25
 */
public class ContentListAdapter extends CoreRecyclerAdapter<ProjectContentBean.DatasBean> {

    @Override
    protected CoreViewHolder onCreateView(LayoutInflater inflater, ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.item_project_content_list, viewGroup, false);
        return new CoreViewHolder(view);
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
    }
}
