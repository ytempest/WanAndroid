package com.ytempest.wanandroid.interactor;

import com.ytempest.wanandroid.http.bean.BannerBean;
import com.ytempest.wanandroid.http.bean.BaseResp;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;
import com.ytempest.wanandroid.http.bean.LoginBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author heqidu
 * @since 2020/6/28
 */
public interface HttpHelper {

    Observable<BaseResp<HomeArticleBean>> getHomeArticleList(int pageNum);

    Observable<BaseResp<LoginBean>> login(String account, String password);

    Observable<BaseResp<LoginBean>> register(String account, String pwd, String confirmPwd);

    Observable<BaseResp<List<BannerBean>>> getBannerList();

}
