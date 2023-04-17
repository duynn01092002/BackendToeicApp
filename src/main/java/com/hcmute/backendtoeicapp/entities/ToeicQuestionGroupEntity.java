package com.hcmute.backendtoeicapp.entities;

import com.hcmute.backendtoeicapp.model.ToeicQuestionGroup;
import jakarta.persistence.*;
import lombok.Data;

import java.io.File;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name="tbl_toeicQuestionGroup")
public class ToeicQuestionGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="type")
    private String type;
    @Column(name="audio")
    private String audio;
    @ManyToOne
    @JoinColumn(name="toeic_part_id")
    private ToeicPartEntity toeicPartEntity;
    public ToeicQuestionGroupEntity() {

    }
    public ToeicQuestionGroupEntity(ToeicQuestionGroup toeicQuestionGroup) {
        this.type = toeicQuestionGroup.getType();
        this.audio = toeicQuestionGroup.getAudio();
    }
}
