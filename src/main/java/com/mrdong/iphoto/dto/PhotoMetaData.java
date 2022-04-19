package com.mrdong.iphoto.dto;

import java.util.Date;

public class PhotoMetaData {

    /**
     * 拍摄时间
     */
    private Date shootDate;

    /**
     * 标题
     */
    private String title;

    /**
     * 主题
     */
    private String subject;

    /**
     * 备注
     */
    private String comments;

    /**
     * 标记
     */
    private String keywords;

    private PhotoGPSInfo photoGPSInfo;

    public Date getShootDate() {
        return shootDate;
    }

    public void setShootDate(Date shootDate) {
        this.shootDate = shootDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public PhotoGPSInfo getPhotoGPSInfo() {
        return photoGPSInfo;
    }

    public void setPhotoGPSInfo(PhotoGPSInfo photoGPSInfo) {
        this.photoGPSInfo = photoGPSInfo;
    }
}
