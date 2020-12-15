package com.ytempest.wanandroid.http;

import com.ytempest.wanandroid.http.bean.BannerBean;
import com.ytempest.wanandroid.http.bean.BaseResp;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;
import com.ytempest.wanandroid.http.bean.LoginBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author heqidu
 * @since 2020/7/4
 */
public interface HttpApi {

    String BASE_URL = "https://www.wanandroid.com/";

    /**
     * 首页文章列表，一般每页20篇
     *
     * @param pageNum 页码
     * @return 首页文章列表
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResp<HomeArticleBean>> getHomeArticleList(@Path("pageNum") int pageNum);

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResp<LoginBean>> login(@Field("username") String account,
                                          @Field("password") String password);

    /**
     * 用户注册
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseResp<LoginBean>> register(@Field("username") String account,
                                             @Field("password") String pwd,
                                             @Field("repassword") String confirmPwd);

    /**
     * 首页Banner
     */
    @GET("banner/json")
    Observable<BaseResp<List<BannerBean>>> getBannerList();

}
