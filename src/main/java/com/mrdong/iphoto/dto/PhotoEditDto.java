package com.mrdong.iphoto.dto;

import java.util.Date;
import java.util.List;

public class PhotoEditDto {

    /**
     * 拍摄日期
     */
    private Date shootDate;

    /**
     * gps信息
     */
    private PhotoGPSInfo gpsInfo;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 图片地址
     */
    private List<String> photoFileDirs;

    public Date getShootDate() {
        return shootDate;
    }

    public void setShootDate(Date shootDate) {
        this.shootDate = shootDate;
    }

    public PhotoGPSInfo getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(PhotoGPSInfo gpsInfo) {
        this.gpsInfo = gpsInfo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getPhotoFileDirs() {
        return photoFileDirs;
    }

    public void setPhotoFileDirs(List<String> photoFileDirs) {
        this.photoFileDirs = photoFileDirs;
    }
}
