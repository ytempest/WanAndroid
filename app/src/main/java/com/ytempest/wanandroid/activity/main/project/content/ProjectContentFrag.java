package com.ytempest.wanandroid.activity.main.project.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.fragment.MvpFragment;
import com.ytempest.wanandroid.http.bean.ProjectClassifyBean;
import com.ytempest.wanandroid.utils.JSON;

/**
 * @author heqidu
 * @since 2020/12/24
 */
@InjectLayout(R.layout.frag_project_content)
public class ProjectContentFrag extends MvpFragment<ProjectContentPresenter> implements IProjectContentContract.View {

    private static final String KEY_CLASSIFY_DATA = "classify_data";

    public static ProjectContentFrag newInstance(ProjectClassifyBean data) {
        ProjectContentFrag frag = new ProjectContentFrag();
        Bundle bundle = frag.getBundle();
        bundle.putString(KEY_CLASSIFY_DATA, JSON.toJson(data));
        return frag;
    }

    private Bundle getBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            setArguments(bundle);
        }
        return bundle;
    }

    private ProjectClassifyBean mClassifyBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassifyBean = JSON.from(getBundle().getString(KEY_CLASSIFY_DATA), ProjectClassifyBean.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.<TextView>findViewById(R.id.text).setText(JSON.toJson(mClassifyBean));
    }
}
