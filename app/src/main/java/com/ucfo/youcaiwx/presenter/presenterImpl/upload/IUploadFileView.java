package com.ucfo.youcaiwx.presenter.presenterImpl.upload;

import com.ucfo.youcaiwx.entity.UploadFileBean;

/**
 * Author:29117
 * Time: 2019-4-18.  下午 4:09
 * Email:2911743255@qq.com
 * ClassName: IUploadFileView
 * Description:TODO 上传图片结果
 */
public interface IUploadFileView {

    void startUploadFile();

    void errorUploadFile();

    void resultUploadFile(UploadFileBean data);

}
