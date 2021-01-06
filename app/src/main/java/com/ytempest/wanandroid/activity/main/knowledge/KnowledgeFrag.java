package com.ytempest.wanandroid.activity.main.knowledge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.tool.util.LogUtils;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.load.LoaderFrag;
import com.ytempest.wanandroid.base.fragment.load.ViewType;
import com.ytempest.wanandroid.http.bean.KnowledgeArchitectureBean;
import com.ytempest.wanandroid.utils.JSON;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author heqidu
 * @since 2020/6/23
 */
@InjectLayout(R.layout.frag_knowledge)
public class KnowledgeFrag extends LoaderFrag<KnowledgePresenter> implements IKnowledgeContract.View {

    private static final String TAG = KnowledgeFrag.class.getSimpleName();

    @BindView(R.id.group_knowledge_list)
    RecyclerView mRecyclerView;
    private ContentAdapter mAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContentAdapter();
        mRecyclerView.setAdapter(mAdapter);
        loadKnowledgeArchitecture();
    }

    private void loadKnowledgeArchitecture() {
        getLoader().showView(ViewType.LOAD);
        mPresenter.getKnowledgeArchitecture();
    }

    @Override
    protected void onReloadClick() {
        super.onReloadClick();
        loadKnowledgeArchitecture();
    }

    @Override
    public void onKnowledgeArchitectureReceived(List<KnowledgeArchitectureBean> list) {
        getLoader().hideAll();
        Collections.sort(list, (o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder()));
        mAdapter.display(list);
        for (int i = 0; i < list.size(); i++) {
            LogUtils.d(TAG, "onKnowledgeArchitectureReceived: bean=" + JSON.toJson(list.get(i)));
        }
    }

    @Override
    public void onKnowledgeArchitectureFail(int code) {
        getLoader().showView(ViewType.ERR);
    }
}
