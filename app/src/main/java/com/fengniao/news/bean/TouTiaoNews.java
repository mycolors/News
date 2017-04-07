package com.fengniao.news.bean;

import com.google.gson.annotations.SerializedName;

public class TouTiaoNews {
    public String title;
    public String date;
    @SerializedName("author_name")
    public String author;
    public String url;
    @SerializedName("thumbnail_pic_s")
    public String picOne;
    @SerializedName("thumbnail_pic_s02")
    public String picTwo;
    @SerializedName("thumbnail_pic_s03")
    public String picThree;
}
