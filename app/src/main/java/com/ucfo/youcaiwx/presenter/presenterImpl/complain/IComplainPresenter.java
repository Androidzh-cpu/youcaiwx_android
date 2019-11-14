package com.ucfo.youcaiwx.presenter.presenterImpl.complain;

/**
 * Author: AND
 * Time: 2019-11-13.  下午 4:19
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.complain
 * FileName: IComplainPresenter
 * Description:TODO 投诉业务
 */
public interface IComplainPresenter {

    void initComplainType();

    void initComplain(String type, String answer_id, String complainid, String useid, String content);

}
