package com.fengniao.news.bean;

import java.util.List;

/**
 * Created by a1 on 2017/3/27.
 */

public class News {


    /**
     * images : ["https://pic1.zhimg.com/v2-43c6f2cff22e6b2afeae20e11e7232b4.jpg"]
     * type : 0
     * id : 9314824
     * ga_prefix : 032718
     * title : 新用户享低价，老用户却是高价，这么做不会被骂吗？
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
