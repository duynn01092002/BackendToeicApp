package com.hcmute.backendtoeicapp.repositories;

import com.hcmute.backendtoeicapp.entities.ToeicItemContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToeicItemContentRepository extends JpaRepository<ToeicItemContentEntity, Integer> {
    @Query("SELECT u FROM ToeicItemContentEntity u WHERE u.toeicQuestionGroupQuestionEntityContent.id = :id")
    List<ToeicItemContentEntity> getListQuestionContentByQuestionGroupId(@Param("id") Integer id);

    @Query("SELECT u FROM ToeicItemContentEntity u WHERE u.toeicQuestionGroupEntityTranscript.id = :id")
    List<ToeicItemContentEntity> getListTranscriptByQuestionGroupId(@Param("id") Integer id);
}
