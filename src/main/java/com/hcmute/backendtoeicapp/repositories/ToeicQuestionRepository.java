package com.hcmute.backendtoeicapp.repositories;

import com.hcmute.backendtoeicapp.entities.ToeicQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToeicQuestionRepository extends JpaRepository<ToeicQuestionEntity, Integer> {
    @Query("SELECT u FROM ToeicQuestionEntity u WHERE u.toeicQuestionGroupEntity.id = :id")
    List<ToeicQuestionEntity> getListToeicQuestionByQuestionGroupId(@Param("id") Integer id);
}
