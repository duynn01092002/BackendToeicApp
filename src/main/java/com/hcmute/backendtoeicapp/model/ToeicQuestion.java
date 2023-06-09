package com.hcmute.backendtoeicapp.model;

import com.google.gson.annotations.SerializedName;
import com.hcmute.backendtoeicapp.entities.ToeicQuestionEntity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class ToeicQuestion implements Serializable {
    @SerializedName("question_id")
    private Integer questionId;
    private String question;
    private List<ToeicAnswerChoice> choices;
    @SerializedName("correct_answer")
    private String correctAnswer;
    private String explain;

    public ToeicQuestion() {

    }
    public ToeicQuestion(ToeicQuestionEntity toeicQuestionEntity) {
        this.questionId = toeicQuestionEntity.getQuestionId();
        this.question = toeicQuestionEntity.getQuestion();
        this.correctAnswer = toeicQuestionEntity.getCorrectAnswer();
        this.explain = toeicQuestionEntity.getExplain();
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ToeicAnswerChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<ToeicAnswerChoice> choices) {
        this.choices = choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
