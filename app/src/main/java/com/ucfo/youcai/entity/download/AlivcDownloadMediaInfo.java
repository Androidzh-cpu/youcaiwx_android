package com.ucfo.youcai.entity.download;

import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;

public class AlivcDownloadMediaInfo {


    private boolean isEditState;
    private boolean isCheckedState;
    private AliyunDownloadMediaInfo aliyunDownloadMediaInfo;

    public boolean isEditState() {
        return isEditState;
    }

    public void setEditState(boolean editState) {
        isEditState = editState;
    }

    public boolean isCheckedState() {
        return isCheckedState;
    }

    public void setCheckedState(boolean checkedState) {
        isCheckedState = checkedState;
    }

    public AliyunDownloadMediaInfo getAliyunDownloadMediaInfo() {
        return aliyunDownloadMediaInfo;
    }

    public void setAliyunDownloadMediaInfo(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        this.aliyunDownloadMediaInfo = aliyunDownloadMediaInfo;
    }


}
