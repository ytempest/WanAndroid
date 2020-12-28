package com.ytempest.wanandroid.http.bean;

import com.ytempest.wanandroid.utils.JSON;

/**
 * @author heqidu
 * @since 2020/12/16
 */
public class ArticleDetailBean {
    private long articleId;
    private String title;
    private String url;
    private boolean isCollected;

    private ArticleDetailBean(long articleId, String title, String url, boolean isCollected) {
        this.articleId = articleId;
        this.title = title;
        this.url = url;
        this.isCollected = isCollected;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    /*Ext*/

    private boolean isShowCollect = true;

    public boolean isShowCollect() {
        return isShowCollect;
    }

    public String toJson() {
        return JSON.toJson(this);
    }

    public static ArticleDetailBean from(String json) {
        return JSON.from(json, ArticleDetailBean.class);
    }

    public static ArticleDetailBean from(HomeArticleBean.DatasBean bean) {
        return new ArticleDetailBean(bean.getId(), bean.getTitle(), bean.getLink(), bean.isCollect());
    }

    public static ArticleDetailBean from(ProjectContentBean.DatasBean bean) {
        ArticleDetailBean result = new ArticleDetailBean(bean.getId(), bean.getTitle(), bean.getLink(), bean.isCollect());
        result.isShowCollect = false;
        return result;
    }
}
