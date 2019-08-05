package com.ucfo.youcai.presenter.view.user;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.address.AddressDetailBean;
import com.ucfo.youcai.entity.address.AddressListBean;
import com.ucfo.youcai.entity.address.StateStatusBean;

/**
 * Author: AND
 * Time: 2019-6-13.  下午 4:11
 * Package: com.ucfo.youcai.presenter.view.user
 * FileName: IUserAddressView
 * Description:TODO 地址VIew层
 */
public interface IUserAddressView extends BaseView {

    //TODO 地址列表
    void getUserAddressList(AddressListBean databean);

    //TODO 地址添加状态
    void addAddressStatus(StateStatusBean data);

    //TODO 地址详细信息
    void getAddressDetail(AddressDetailBean detailBean);

    //TODO 地址删除状态
    void deleteAddressStatus(StateStatusBean data);

}
