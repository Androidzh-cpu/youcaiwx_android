package com.ucfo.youcai.entity.address;

/**
 * Author: AND
 * Time: 2019-6-14.  下午 4:05
 * Package: com.ucfo.youcai.entity.address
 * FileName: AddressDetailBean
 * Description:TODO 地址详情
 */
public class AddressDetailBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"address_id":1,"consignee":"二狗子","telephone":"17710116887","address":"北京市海淀区五道口优盛大厦","is_default":2}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address_id : 1
         * consignee : 二狗子
         * telephone : 17710116887
         * address : 北京市海淀区五道口优盛大厦
         * is_default : 2
         */

        private int address_id;
        private String consignee;
        private String telephone;
        private String address;
        private int is_default;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getConsignee() {
            return consignee == null ? "" : consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getTelephone() {
            return telephone == null ? "" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
