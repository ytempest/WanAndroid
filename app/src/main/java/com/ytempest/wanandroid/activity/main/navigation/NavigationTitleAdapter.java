package com.ytempest.wanandroid.activity.main.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytempest.tool.adapter.CoreRecyclerAdapter;
import com.ytempest.tool.adapter.CoreViewHolder;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.http.bean.NavigationListBean;

/**
 * @author heqidu
 * @since 2020/12/29
 */
public class NavigationTitleAdapter extends CoreRecyclerAdapter<NavigationListBean> {

    @Override
    protected CoreViewHolder onCreateView(LayoutInflater inflater, ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.item_navigation_title, viewGroup, false);
        CoreViewHolder holder = new CoreViewHolder(view);
        holder.setNeedClick(true);
        return holder;
    }

    @Override
    protected void onBindData(CoreViewHolder holder, NavigationListBean data, int position) {
        holder.setText(R.id.tv_item_navigation_title, data.getName());
        if (mSelectView == null) {
            onItemSelected(holder.getRootView(), position);
        }
    }

    @Override
    protected void onItemClick(CoreViewHolder holder, View view, int position) {
        super.onItemClick(holder, view, position);
        onItemSelected(view, position);
    }

    private View mSelectView;
    private int mSelectPosition;

    public void setSelectPosition(int position) {
        mSelectPosition = position;
        getRecyclerView().smoothScrollToPosition(mSelectPosition);

        RecyclerView.LayoutManager manager = getRecyclerView().getLayoutManager();
        View view = manager.findViewByPosition(mSelectPosition);
        if (view != null) {
            onItemSelected(view, mSelectPosition);
        }
    }

    private void onItemSelected(View view, int position) {
        if (mSelectView != null) {
            mSelectView.setSelected(false);
        }
        mSelectView = view;
        mSelectView.setSelected(true);
    }
}
