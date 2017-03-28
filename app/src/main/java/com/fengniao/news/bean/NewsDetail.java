package com.fengniao.news.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsDetail {
    public String body;
    public String title;
    public String image;
    public int id;
    public Section section;
    @SerializedName("css")
    public List<String> cssList;
    public List<Recommenders> recommenders;


    public class Section {
        public String thumbnail;
        public int id;
        public String name;
    }

    public class Recommenders {
        public String avatar;
    }
}
