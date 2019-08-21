package com.ucfo.youcaiwx.presenter.presenterImpl.user;

/**
 * Author: AND
 * Time: 2019-6-13.  下午 4:09
 * FileName: IUserAddressPresenter
 * Description:TODO 地址接口
 */
public interface IUserAddressPresenter {

    //TODO 获取用户地址列表
    void getUserAddressList(int user_id);

    //TODO 添加地址
    void addAddress(int user_id, String userName, String userPhone, String userAddress, int type);

    //TODO 获取地址详细信息
    void getAddressDetail(int user_id, int address_id);

    //TODO 删除地址
    void deleteAddress(int user_id, int address_id);

    //TODO 修改地址
    void updateAddress(int user_id, int address_id, String userName, String userPhone, String userAddress, int type);

}
