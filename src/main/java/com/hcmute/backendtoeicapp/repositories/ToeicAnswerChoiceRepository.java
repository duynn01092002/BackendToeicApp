package com.hcmute.backendtoeicapp.repositories;

import com.hcmute.backendtoeicapp.entities.ToeicAnswerChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToeicAnswerChoiceRepository extends JpaRepository<ToeicAnswerChoiceEntity, Integer> {
    @Query("SELECT u FROM ToeicAnswerChoiceEntity u WHERE u.toeicQuestionEntity.id=:id")
    List<ToeicAnswerChoiceEntity> getToeicAnswerChoiceByToeicQuestionId(@Param("id") Integer id);
}
