package com.fengniao.news.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZhiHuArticleDetail {
    public String body;
    public String title;
    public String image;
    public int id;
    public Section section;
    @SerializedName("css")
    public List<String> cssList;
    public List<Recommenders> recommenders;

    private class Section {
        public String thumbnail;
        public int id;
        public String name;
    }

    private class Recommenders {
        public String avatar;
    }
}
