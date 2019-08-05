package com.ucfo.youcai.presenter.presenterImpl.upload;

import com.ucfo.youcai.entity.UploadFileBean;

/**
 * Author:29117
 * Time: 2019-4-18.  下午 4:09
 * Email:2911743255@qq.com
 * ClassName: IUploadFileView
 * Package: com.ucfo.youcai.presenter.presenterImpl.upload
 * Description:TODO 上传图片结果
 * Detail:TODO
 */
public interface IUploadFileView {

    void startUploadFile();

    void errorUploadFile();

    void resultUploadFile(UploadFileBean data);

}
