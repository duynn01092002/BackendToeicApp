package com.hcmute.backendtoeicapp.repositories;

import com.hcmute.backendtoeicapp.entities.ToeicQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToeicQuestionRepository extends JpaRepository<ToeicQuestionEntity, Integer> {
}
