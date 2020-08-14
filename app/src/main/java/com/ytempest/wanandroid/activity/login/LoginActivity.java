package com.ytempest.wanandroid.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.ytempest.layoutinjector.annotation.InjectLayout;
import com.ytempest.wanandroid.R;
import com.ytempest.wanandroid.base.activity.MvpActivity;
import com.ytempest.wanandroid.http.ErrCode;
import com.ytempest.wanandroid.http.bean.LoginBean;
import com.ytempest.wanandroid.listener.PasswordStatusChangeListener;
import com.ytempest.wanandroid.listener.TextWatcherListener;
import com.ytempest.wanandroid.utils.RegexUtils;
import com.ytempest.wanandroid.widget.ModifiableButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author heqidu
 * @since 2020/8/11
 */
@InjectLayout(R.layout.activity_login)
public class LoginActivity extends MvpActivity<LoginPresenter> implements ILoginContract.View {

    @BindView(R.id.et_login_account)
    EditText mAccountET;
    @BindView(R.id.et_login_password)
    EditText mPasswordET;
    @BindView(R.id.view_login_pwd_status)
    View mPwdStatusView;
    @BindView(R.id.bt_login_confirm)
    ModifiableButton mLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPwdStatusView.setOnClickListener(new PasswordStatusChangeListener(mPasswordET));

        // EditText空格过滤器
        InputFilter spaceFilter = (source, start, end, dest, dstart, dend) -> source.toString().equals(" ") ? "" : null;
        InputFilter[] filters = new InputFilter[]{spaceFilter};
        mAccountET.setFilters(filters);
        mPasswordET.setFilters(filters);

        // 输入监听
        TextWatcherListener textWatcher = new TextWatcherListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (mAccountET.length() > 0 && mPasswordET.length() > 0) {
                    mLoginButton.setNormalStatus();
                } else {
                    mLoginButton.setDisableStatus();
                }
            }
        };
        mAccountET.addTextChangedListener(textWatcher);
        mPasswordET.addTextChangedListener(textWatcher);
    }

    @OnClick(R.id.tv_login_register)
    void onRegisterClick() {
        // TODO  heqidu: 注册操作
    }

    @OnClick(R.id.bt_login_confirm)
    void onLoginClick() {
        String account = mAccountET.getText().toString();
        if (!RegexUtils.isPhone(account) && !RegexUtils.isEmail(account)) {
            showToast(R.string.account_format_err);
            return;
        }

        String pwd = mPasswordET.getText().toString();
        mPresenter.login(account, pwd);
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        // TODO  heqidu: 登录成功后的操作
    }

    @Override
    public void onLoginFail(@ErrCode int code) {
        if (code == ErrCode.NET_ERR) {
            showToast(R.string.net_err);

        } else if (code == ErrCode.DATA_ERR) {
            showToast(R.string.account_pwd_err);

        } else {
            showToast(R.string.unknown_err);
        }
    }
}
