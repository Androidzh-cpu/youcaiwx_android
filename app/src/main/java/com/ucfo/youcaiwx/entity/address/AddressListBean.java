package com.ucfo.youcaiwx.entity.address;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-13.  下午 3:50
 * FileName: AddressListBean
 * Description:TODO 地址列表实体类
 */
public class AddressListBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : [{"address_id":2,"consignee":"小金龟","telephone":"15321876983","address":"琵琶山","is_default":2},{"address_id":6,"consignee":"宝石骑士周大福","telephone":"12345678901","address":"靠山屯7组","is_default":2},{"address_id":7,"consignee":"宝石骑士周大福","telephone":"12345678901","address":"靠山屯7组","is_default":1}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address_id : 2
         * consignee : 小金龟
         * telephone : 15321876983
         * address : 琵琶山
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
