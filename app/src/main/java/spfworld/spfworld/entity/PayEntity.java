package spfworld.spfworld.entity;

/**
 * Created by guozhengke on 2016/10/12.
 * 支付宝  参数
 */
public class PayEntity {
    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String partner;
        private String seller_id;
        private String private_key_path;

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getPrivate_key_path() {
            return private_key_path;
        }

        public void setPrivate_key_path(String private_key_path) {
            this.private_key_path = private_key_path;
        }
    }
}
