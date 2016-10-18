package spfworld.spfworld.entity.Tribune;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class TribuneDatail {

    /**
     * status : 200
     * message : 数据成功
     * data : [{"c_id":"121","uid":"20","username":"舒通","title":"发帖子了","comment":"","zan":"0","replycount":"0","created_at":"2016-10-14 15:23:04","pic":["m.yundiaoke.cn/Uploads/Notepic/0"],"headpic":"m.yundiaoke.cn0","collection":"1"}]
     */

    private int status;
    private String message;
    /**
     * c_id : 121
     * uid : 20
     * username : 舒通
     * title : 发帖子了
     * comment :
     * zan : 0
     * replycount : 0
     * created_at : 2016-10-14 15:23:04
     * pic : ["m.yundiaoke.cn/Uploads/Notepic/0"]
     * headpic : m.yundiaoke.cn0
     * collection : 1
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String c_id;
        private String uid;
        private String username;
        private String title;
        private String comment;
        private String zan;
        private String replycount;
        private String created_at;
        private String headpic;
        private String collection;
        private List<String> pic;

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getReplycount() {
            return replycount;
        }

        public void setReplycount(String replycount) {
            this.replycount = replycount;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
