package com.fengniao.news.bean;

import java.util.List;


public class YueArticle {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1097
         * uuid : 6bf7ea53e372498889e20515c258a03b
         * author : {"id":217,"pen_name":"亚米契斯","avatar":"http://cdn.yue.fm/a0ae651f-65ff-41a4-b8d8-31e8c6015475.jpg-icon128","tags":[],"intro":"1846年出生于意大利。那时的意大利受法国大革命的影响，国内正酝酿着爱国主义，这股风潮自然也在他幼小的心灵上留下不可磨灭的印记。 "}
         * title : 卡罗纳
         * excerpt : 卡罗纳的母亲病得很厉害，卡罗纳很多天没来上学了。昨天上午，我们刚走进教室，老师就对大家说：“卡罗纳的……
         * content : <p>卡罗纳的母亲病得很厉害，卡罗纳很多天没来上学了。昨天上午，我们刚走进教室，老师就对大家说：“卡罗纳的母亲去世了，这个可怜的孩子遭到了巨大的不幸。他明天要来上课，孩子们，你们要庄重严肃，热情地对待他。任何人都不许跟他开玩笑，不许在他面前放声大笑！”</p><p>今天上午，可怜的卡罗纳来到了学校。他面容灰白，眼睛哭红了，两腿站不稳，好像他自己也大病了一场似的。我心里不由得泛起一阵同情和怜悯，大家都屏息凝神地望着他。</p><p>卡罗纳走进教室，突然放声大哭起来。他一定是想起了往日的情景。那时候，母亲差不多每天都来接他；要考试了，母亲总是俯下身来向他千叮咛万嘱咐。老师把卡罗纳拉到自己胸前，对他说：“哭吧，痛痛快快地哭吧，可怜的孩子！但你要坚强！你母亲已不在这个世界上了，但她能看见你，她依然爱着你，她还生活在你身边。孩子，你要坚强哟！”</p><p>老师说完，卡罗纳回到座位上，挨着我坐下。卡罗纳翻开书，当他看到一幅母亲拉着儿子的手的插图时，突然双手抱住脑袋，趴在桌子上号啕大哭。老师暗示大家暂时别管他，开始上课。我本想跟他说几句话，但不知说什么才好，就把一只手放在他的肩膀上，脸贴在他的耳朵上，对他说：“卡罗纳，别哭了。”</p><p>他什么也没说，也没有抬起头来，只是把他的手放在我的手上。</p><p>放学的时候，大家围在他身边，谁都没有说话，只用关切的目光默默地看着他。</p><p>我看见母亲在等我，跑过去扑进她的怀抱。母亲把我推开了，她目不转睛地望着卡罗纳。当时我并不明白母亲的用意。过了一会儿，我发现卡罗纳独自站在一边端详着我，他的目光里充满着无法形容的悲哀，那神情仿佛在说：“你可以拥抱妈妈，我却再也不能了。”</p><p>我恍然大悟，没去拉母亲的手，却拉起卡罗纳的手，和他一块儿回家去。</p>
         * read_count : 206
         * liked_count : 10
         * comments_count : 0
         * is_liked : false
         * tags : [{"id":24,"content":"微型小说"}]
         * url : http://www.yue.fm/article/6bf7ea53e372498889e20515c258a03b
         * scrolled : false
         * progress : 0
         * created_at : 2016-07-27 10:01:25
         * updated_at : 2017-03-04 21:13:38
         * published_at :
         */

        private int id;
        private String uuid;
        private AuthorBean author;
        private String title;
        private String excerpt;
        private String content;
        private String read_count;
        private String liked_count;
        private int comments_count;
        private boolean is_liked;
        private String url;
        private boolean scrolled;
        private int progress;
        private String created_at;
        private String updated_at;
        private String published_at;
        private List<TagsBean> tags;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRead_count() {
            return read_count;
        }

        public void setRead_count(String read_count) {
            this.read_count = read_count;
        }

        public String getLiked_count() {
            return liked_count;
        }

        public void setLiked_count(String liked_count) {
            this.liked_count = liked_count;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public boolean isIs_liked() {
            return is_liked;
        }

        public void setIs_liked(boolean is_liked) {
            this.is_liked = is_liked;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isScrolled() {
            return scrolled;
        }

        public void setScrolled(boolean scrolled) {
            this.scrolled = scrolled;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getPublished_at() {
            return published_at;
        }

        public void setPublished_at(String published_at) {
            this.published_at = published_at;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class AuthorBean {
            /**
             * id : 217
             * pen_name : 亚米契斯
             * avatar : http://cdn.yue.fm/a0ae651f-65ff-41a4-b8d8-31e8c6015475.jpg-icon128
             * tags : []
             * intro : 1846年出生于意大利。那时的意大利受法国大革命的影响，国内正酝酿着爱国主义，这股风潮自然也在他幼小的心灵上留下不可磨灭的印记。
             */

            private int id;
            private String pen_name;
            private String avatar;
            private String intro;
            private List<?> tags;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPen_name() {
                return pen_name;
            }

            public void setPen_name(String pen_name) {
                this.pen_name = pen_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public List<?> getTags() {
                return tags;
            }

            public void setTags(List<?> tags) {
                this.tags = tags;
            }
        }

        public static class TagsBean {
            /**
             * id : 24
             * content : 微型小说
             */

            private int id;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
