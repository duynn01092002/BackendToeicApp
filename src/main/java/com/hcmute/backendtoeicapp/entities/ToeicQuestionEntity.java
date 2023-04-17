package com.hcmute.backendtoeicapp.entities;

import com.hcmute.backendtoeicapp.model.ToeicQuestion;
import jakarta.persistence.*;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
@Entity
@Table(name="tbl_toeicQuestion")
public class ToeicQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="questionId")
    private Integer questionId;
    @Column(name="question")
    private String question;
    @Column(name="correctAnswer")
    private String correctAnswer;
    @Column(name="c_explain", columnDefinition = "TEXT")
    private String explain;
    @ManyToOne
    @JoinColumn(name="toeic_question_group_id")
    private ToeicQuestionGroupEntity toeicQuestionGroupEntity;
    public ToeicQuestionEntity() {

    }
    public ToeicQuestionEntity(ToeicQuestion toeicQuestion) {
        this.questionId = toeicQuestion.getQuestionId();
        this.question = toeicQuestion.getQuestion();
        this.correctAnswer = toeicQuestion.getCorrectAnswer();
        this.explain = toeicQuestion.getExplain();
    }
}
