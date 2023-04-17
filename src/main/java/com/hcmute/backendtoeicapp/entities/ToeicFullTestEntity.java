package com.hcmute.backendtoeicapp.entities;

import com.hcmute.backendtoeicapp.model.ToeicFullTest;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="tbl_toeicFullTest")
public class ToeicFullTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "slug")
    private String slug;
    @Column(name="fullName")
    private String fullName;

    public ToeicFullTestEntity() {

    }
    public ToeicFullTestEntity(ToeicFullTest toeicFullTest) {
        this.slug = toeicFullTest.getSlug();
        this.fullName = toeicFullTest.getFullName();

    }
}
