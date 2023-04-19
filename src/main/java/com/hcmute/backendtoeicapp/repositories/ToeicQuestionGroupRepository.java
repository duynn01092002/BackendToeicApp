package com.hcmute.backendtoeicapp.repositories;

import com.hcmute.backendtoeicapp.entities.ToeicQuestionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToeicQuestionGroupRepository extends JpaRepository<ToeicQuestionGroupEntity, Integer> {
    @Query("SELECT u FROM ToeicQuestionGroupEntity u WHERE u.toeicPartEntity.id=:id")
    List<ToeicQuestionGroupEntity> getListToeicQuestionGroupByPartId(@Param("id") Integer id);
}
