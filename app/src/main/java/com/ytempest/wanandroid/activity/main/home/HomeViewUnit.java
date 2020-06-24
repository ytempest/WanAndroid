package com.ytempest.wanandroid.activity.main.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.unit.ViewUnit;

import butterknife.BindView;

/**
 * @author heqidu
 * @since 2020/6/24
 */
public class HomeViewUnit extends ViewUnit {

    @BindView(R.id.textView_homeFrag)
    TextView mTextView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView.setText("I see you");
    }
}
