package com.ucfo.youcai.entity.download;

/**
 * Author: AND
 * Time: 2019-7-2.  下午 4:51
 * Package: com.ucfo.youcai.entity
 * FileName: GetVideoStsBean
 * Description:TODO 获取视频STS
 */
public class GetVideoStsBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"SecurityToken":"CAIS6QF1q6Ft5B2yfSjIr4mNMf7gvOdOwKPZRB/0kXQzffUeqLKcpjz2IH1JdXhvA+kWt/wxlW9Z5/wflqx2Vo5MQxQ3kwqWPdEFnzm6aq/t5uaXj9Vd+rDHdEGXDxnkprywB8zyUNLafNq0dlnAjVUd6LDmdDKkLTfHWN/z/vwBVNkMWRSiZjdrHcpfIhAYyPUXLnzML/2gQHWI6yjydBMx41Mg2DgvuPvlm5XGukHk4QekmrNPlePYOYO5asRgBpB7Xuqu0fZ+Hqi7i3MNukkVrv4m1fQaqGiW74vAGTRU/gSPNfoXCRKwmbvtDxqAAZ5GGba4FVOLfKQn88n51/r35R7R9sdG55ISNzhYfee08RPlKS8qwZCHFlZBniVPgqBoecREPQPTbWK4eCzPJHZxdh9kdgBWCNJGhbZck1LZBD0fVuet5VEngTzJisbxsqAmtes3LCLUNjJACYFiZsH0YmmzifGlAbK/x7hc48Am","accessKeyId":"STS.NJ8zDTQ8owa2F9Eutfqz2Gw7D","KeySecret":"6Hraq5BJHxmCHDbnmco5vxBpHrTyaFjhYQ3HBZsdA4Kh"}
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
         * SecurityToken : CAIS6QF1q6Ft5B2yfSjIr4mNMf7gvOdOwKPZRB/0kXQzffUeqLKcpjz2IH1JdXhvA+kWt/wxlW9Z5/wflqx2Vo5MQxQ3kwqWPdEFnzm6aq/t5uaXj9Vd+rDHdEGXDxnkprywB8zyUNLafNq0dlnAjVUd6LDmdDKkLTfHWN/z/vwBVNkMWRSiZjdrHcpfIhAYyPUXLnzML/2gQHWI6yjydBMx41Mg2DgvuPvlm5XGukHk4QekmrNPlePYOYO5asRgBpB7Xuqu0fZ+Hqi7i3MNukkVrv4m1fQaqGiW74vAGTRU/gSPNfoXCRKwmbvtDxqAAZ5GGba4FVOLfKQn88n51/r35R7R9sdG55ISNzhYfee08RPlKS8qwZCHFlZBniVPgqBoecREPQPTbWK4eCzPJHZxdh9kdgBWCNJGhbZck1LZBD0fVuet5VEngTzJisbxsqAmtes3LCLUNjJACYFiZsH0YmmzifGlAbK/x7hc48Am
         * accessKeyId : STS.NJ8zDTQ8owa2F9Eutfqz2Gw7D
         * KeySecret : 6Hraq5BJHxmCHDbnmco5vxBpHrTyaFjhYQ3HBZsdA4Kh
         */

        private String SecurityToken;
        private String accessKeyId;
        private String KeySecret;

        public String getSecurityToken() {
            return SecurityToken == null ? "" : SecurityToken;
        }

        public void setSecurityToken(String securityToken) {
            SecurityToken = securityToken;
        }

        public String getAccessKeyId() {
            return accessKeyId == null ? "" : accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getKeySecret() {
            return KeySecret == null ? "" : KeySecret;
        }

        public void setKeySecret(String keySecret) {
            KeySecret = keySecret;
        }
    }
}
