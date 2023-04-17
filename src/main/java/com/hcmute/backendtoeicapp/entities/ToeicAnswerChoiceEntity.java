package com.hcmute.backendtoeicapp.entities;

import com.hcmute.backendtoeicapp.model.ToeicAnswerChoice;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tbl_toeicAnswerChoice")
public class ToeicAnswerChoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="c_label")
    private String label;
    @Column(name="content")
    private String content;
    @Column(name="c_explain", columnDefinition = "TEXT")
    private String explain;
    @ManyToOne
    @JoinColumn(name = "toeic_question_id")
    private ToeicQuestionEntity toeicQuestionEntity;

    public ToeicAnswerChoiceEntity() {

    }
    public ToeicAnswerChoiceEntity(ToeicAnswerChoice toeicAnswerChoice) {
        this.label = toeicAnswerChoice.getLabel();
        this.content = toeicAnswerChoice.getContent();
        this.explain = toeicAnswerChoice.getExplain();
    }
}
