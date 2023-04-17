package com.hcmute.backendtoeicapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.backendtoeicapp.model.ToeicPart;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_toeicPart")
public class ToeicPartEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "partId")
    private Integer partId;
    @Column(name = "slug")
    private String slug;
    @ManyToOne
    @JoinColumn(name = "toeic_full_test_id")
    private ToeicFullTestEntity toeicFullTestEntity;
    public ToeicPartEntity() {

    }
    public ToeicPartEntity(ToeicPart toeicPart) {
        this.partId = toeicPart.getPartId();
        this.slug = toeicPart.getSlug();
    }

}
