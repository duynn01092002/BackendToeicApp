package com.hcmute.backendtoeicapp.model;

import com.hcmute.backendtoeicapp.entities.ToeicItemContentEntity;

import java.io.Serializable;

public class ToeicItemContent implements Serializable {
    private String type;
    private String content;

    public ToeicItemContent() {

    }
    public ToeicItemContent(ToeicItemContentEntity toeicItemContentEntity) {
        this.type = toeicItemContentEntity.getType();
        this.content = toeicItemContentEntity.getContent();
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
