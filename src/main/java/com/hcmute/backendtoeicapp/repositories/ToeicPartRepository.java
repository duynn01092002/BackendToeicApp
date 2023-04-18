package com.hcmute.backendtoeicapp.repositories;

import com.hcmute.backendtoeicapp.entities.ToeicPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToeicPartRepository extends JpaRepository<ToeicPartEntity, Integer> {
    @Query("SELECT u FROM ToeicPartEntity u WHERE u.partId=:id")
    List<ToeicPartEntity> getToeicPartByPartNumber(@Param("id") Integer id);

    @Query("SELECT COUNT(u) FROM ToeicQuestionEntity u WHERE u.toeicQuestionGroupEntity.toeicPartEntity.id = :id")
    Integer getNumOfQuestions(@Param("id") Integer id);
}
