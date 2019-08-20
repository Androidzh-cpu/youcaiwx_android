package com.ucfo.youcaiwx.entity.home;

/**
 * Author: AND
 * Time: 2019-8-20.  下午 4:53
 * Package: com.ucfo.youcaiwx.entity.home
 * FileName: UpdateBean
 * Description:TODO 版本更新
 */
public class UpdateBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":1,"apk_name":"安卓系统","versionname":"1.1.81","versioncode":16,"apksize":50,"downloadurl":"http://ycapi.youcaiwx.com/apk/20190605/0a1da36b8eba0dd2b486d2ff91e55dee.apk","modifycontent":"-新增\u201c冲刺训练营\u201d\r\n-新增学习记录\r\n-优化课程播放\r\n-优化试题交卷\r\n-优化H5页面响应速度\r\n-程序GG还修改了一些看得见看不见的bug\r\n-感谢大家一直以来的支持\r\n如遇下载缓慢请稍后在试(づ。◕\u203f\u203f◕｡)づ\r\n","updatestatus":1,"apkmd5":"","update":"Yes","is_update":true}
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
        return msg == null ? "" : msg;
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
         * id : 1
         * apk_name : 安卓系统
         * versionname : 1.1.81
         * versioncode : 16
         * apksize : 50
         * downloadurl : http://ycapi.youcaiwx.com/apk/20190605/0a1da36b8eba0dd2b486d2ff91e55dee.apk
         * modifycontent : -新增“冲刺训练营”
         -新增学习记录
         -优化课程播放
         -优化试题交卷
         -优化H5页面响应速度
         -程序GG还修改了一些看得见看不见的bug
         -感谢大家一直以来的支持
         如遇下载缓慢请稍后在试(づ。◕‿‿◕｡)づ

         * updatestatus : 1
         * apkmd5 :
         * update : Yes
         * is_update : true
         */

        private int id;
        private String apk_name;
        private String versionname;
        private int versioncode;
        private int apksize;
        private String downloadurl;
        private String modifycontent;
        private int updatestatus;
        private String apkmd5;
        private String update;
        private boolean is_update;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getApk_name() {
            return apk_name == null ? "" : apk_name;
        }

        public void setApk_name(String apk_name) {
            this.apk_name = apk_name;
        }

        public String getVersionname() {
            return versionname == null ? "" : versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public int getVersioncode() {
            return versioncode;
        }

        public void setVersioncode(int versioncode) {
            this.versioncode = versioncode;
        }

        public int getApksize() {
            return apksize;
        }

        public void setApksize(int apksize) {
            this.apksize = apksize;
        }

        public String getDownloadurl() {
            return downloadurl == null ? "" : downloadurl;
        }

        public void setDownloadurl(String downloadurl) {
            this.downloadurl = downloadurl;
        }

        public String getModifycontent() {
            return modifycontent == null ? "" : modifycontent;
        }

        public void setModifycontent(String modifycontent) {
            this.modifycontent = modifycontent;
        }

        public int getUpdatestatus() {
            return updatestatus;
        }

        public void setUpdatestatus(int updatestatus) {
            this.updatestatus = updatestatus;
        }

        public String getApkmd5() {
            return apkmd5 == null ? "" : apkmd5;
        }

        public void setApkmd5(String apkmd5) {
            this.apkmd5 = apkmd5;
        }

        public String getUpdate() {
            return update == null ? "" : update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public boolean isIs_update() {
            return is_update;
        }

        public void setIs_update(boolean is_update) {
            this.is_update = is_update;
        }
    }
}
