package com.ytempest.wanandroid.http;

import com.ytempest.wanandroid.http.bean.BaseResp;
import com.ytempest.wanandroid.http.bean.HomeArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
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

}
