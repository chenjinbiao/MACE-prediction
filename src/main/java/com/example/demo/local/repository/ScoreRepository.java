package com.example.demo.local.repository;

import com.example.demo.local.modal.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository  extends JpaRepository<Score,String> {
    Score findFirstByPatientIDAndVisitID(String patintId,int visitId);
}
