package com.hcmute.backendtoeicapp.model;

import com.hcmute.backendtoeicapp.entities.ToeicAnswerChoiceEntity;

import java.io.Serializable;

public class ToeicAnswerChoice implements Serializable {
    private String label;
    private String content;
    private String explain;

    public ToeicAnswerChoice() {

    }
    public ToeicAnswerChoice(ToeicAnswerChoiceEntity toeicAnswerChoiceEntity) {
        this.label = toeicAnswerChoiceEntity.getLabel();
        this.content = toeicAnswerChoiceEntity.getContent();
        this.explain = toeicAnswerChoiceEntity.getExplain();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
