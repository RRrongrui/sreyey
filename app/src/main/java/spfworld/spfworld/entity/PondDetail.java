package spfworld.spfworld.entity;

import java.util.List;

/**
 * Created by guozhengke on 2016/9/8.
 */
public class PondDetail {


    /**
     * status : 200
     * message : 获取数据成功
     * data : [{"pon_id":"41","theme":"小纪王农庄","price":"100元/场","prov":"上海","city":"闵行区","area":"","address":"纪友路西红卫村张家浜小区东北450米","lng":"121.278683","lat":"31.236107","phone":"13524058705","content":"","status":"3","big_img":["m.yundiaoke.cn/Uploads/Uploads/Pond/1476337145_7397.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337146_7161.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337148_2333.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337151_9964.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337155_1746.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337157_1370.PNG"],"special":["1","3","4","5"],"fish_type":"白条, 罗非鱼, 草鱼, 青鱼, 鲫鱼, 鲤鱼, 鲶鱼, 鳊鱼","basan":"1","collection":null}]
     */

    private int status;
    private String message;
    /**
     * pon_id : 41
     * theme : 小纪王农庄
     * price : 100元/场
     * prov : 上海
     * city : 闵行区
     * area :
     * address : 纪友路西红卫村张家浜小区东北450米
     * lng : 121.278683
     * lat : 31.236107
     * phone : 13524058705
     * content :
     * status : 3
     * big_img : ["m.yundiaoke.cn/Uploads/Uploads/Pond/1476337145_7397.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337146_7161.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337148_2333.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337151_9964.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337155_1746.PNG","m.yundiaoke.cn/Uploads/Uploads/Pond/1476337157_1370.PNG"]
     * special : ["1","3","4","5"]
     * fish_type : 白条, 罗非鱼, 草鱼, 青鱼, 鲫鱼, 鲤鱼, 鲶鱼, 鳊鱼
     * basan : 1
     * collection : null
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
        private String pon_id;
        private String theme;
        private String price;
        private String prov;
        private String city;
        private String area;
        private String address;
        private String lng;
        private String lat;
        private String phone;
        private String content;
        private String status;
        private String fish_type;
        private String basan;
        private String collection;
        private List<String> big_img;
        private List<String> special;

        public String getPon_id() {
            return pon_id;
        }

        public void setPon_id(String pon_id) {
            this.pon_id = pon_id;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFish_type() {
            return fish_type;
        }

        public void setFish_type(String fish_type) {
            this.fish_type = fish_type;
        }

        public String getBasan() {
            return basan;
        }

        public void setBasan(String basan) {
            this.basan = basan;
        }

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public List<String> getBig_img() {
            return big_img;
        }

        public void setBig_img(List<String> big_img) {
            this.big_img = big_img;
        }

        public List<String> getSpecial() {
            return special;
        }

        public void setSpecial(List<String> special) {
            this.special = special;
        }
    }
}
