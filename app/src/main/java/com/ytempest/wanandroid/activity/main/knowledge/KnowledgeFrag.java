package com.ytempest.wanandroid.activity.main.knowledge;

import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;

/**
 * @author heqidu
 * @since 2020/6/23
 */
public class KnowledgeFrag extends MvpFragment<KnowledgePresenter> implements IKnowledgeContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.frag_knowledge;
    }
}
