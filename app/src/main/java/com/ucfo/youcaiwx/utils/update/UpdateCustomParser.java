package com.ucfo.youcaiwx.utils.update;

import com.google.gson.Gson;
import com.ucfo.youcaiwx.entity.home.UpdateBean;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.IUpdateParser;

/**
 * Author: AND
 * Time: 2019-8-21.  上午 10:21
 * Package: com.ucfo.youcaiwx.utils.netutils
 * FileName: UpdateCustomParser
 * Description:TODO 版本更新自定义解析器
 */
public class UpdateCustomParser implements IUpdateParser {
    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        Gson gson = new Gson();
        UpdateBean data = gson.fromJson(json, UpdateBean.class);
        UpdateBean.DataBean updateBean = data.getData();
        if (updateBean != null) {
            boolean flag = false;
            int updatestatus = updateBean.getUpdatestatus();
            if (updatestatus == 2) {
                //2强制更新,1普通更新
                flag = true;
            }
            return new UpdateEntity()
                    .setHasUpdate(updateBean.isIs_update())
                    .setIsIgnorable(true)
                    .setIsAutoInstall(true)
                    .setForce(flag)
                    .setVersionCode(updateBean.getVersioncode())
                    .setVersionName(updateBean.getVersionname())
                    .setUpdateContent(updateBean.getModifycontent())
                    .setDownloadUrl(updateBean.getDownloadurl())
                    .setSize(updateBean.getApksize() * 1024);
        }
        return null;
    }
}
