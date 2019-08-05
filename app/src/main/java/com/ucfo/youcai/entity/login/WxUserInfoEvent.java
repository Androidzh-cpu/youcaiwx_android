package com.ucfo.youcai.entity.login;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-3-28.  下午 6:51
 * Email:2911743255@qq.com
 * ClassName: WxUserInfoEvent
 * Package: com.ucfo.youcai.entity.login
 * Description:
 * Detail:
 */
public class WxUserInfoEvent {


    /**
     * openid : o4zDq0jH6KG4vQF3P2jJfZkiHXE4
     * nickname : 时光 锁定在过往的曾经¹¹²⁵
     * sex : 1
     * language : zh_CN
     * city :
     * province : Beijing
     * country : CN
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/B9Z2TQxeF0cibQD2MJTMnA6M0Zpjs2M0G62f6U4eqsBCxt7tXfYLzjMI8JriaHIP6MVvlt5OPcOT7QH7jZ41sp1Q/132
     * privilege : []
     * unionid : oTkO5w0wYhtry5btIe_NmBH3JwnY
     */

    private String openid;//普通用户的标识，对当前开发者帐号唯一
    private String nickname;//普通用户昵称
    private int sex;//普通用户性别，1为男性，2为女性
    private String language;
    private String city;//普通用户个人资料填写的城市
    private String province;//普通用户个人资料填写的省份
    private String country;//国家，如中国为CN
    private String headimgurl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
    private String unionid;//用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
    private List<String> privilege;//用户特权信息，json数组，如微信沃卡用户为（chinaunicom）

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }
}
