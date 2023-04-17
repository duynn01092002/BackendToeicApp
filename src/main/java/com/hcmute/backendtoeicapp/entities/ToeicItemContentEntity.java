package com.hcmute.backendtoeicapp.entities;

import com.hcmute.backendtoeicapp.model.ToeicItemContent;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tbl_toeicItemContent")
public class ToeicItemContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="type")
    private String type;
    @Column(name="content", columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    @JoinColumn(name="toeic_question_group_question_content_id")
    private ToeicQuestionGroupEntity toeicQuestionGroupQuestionEntityContent;
    @ManyToOne
    @JoinColumn(name="toeic_question_group_transcript_id")
    private ToeicQuestionGroupEntity toeicQuestionGroupEntityTranscript;
    public ToeicItemContentEntity() {

    }
    public ToeicItemContentEntity(ToeicItemContent toeicItemContent) {
        this.type = toeicItemContent.getType();
        this.content = toeicItemContent.getContent();
    }
}
