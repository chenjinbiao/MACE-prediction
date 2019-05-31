package com.example.demo.local.repository;

import com.example.demo.local.modal.DiagOut;
import com.example.demo.local.modal.Diagnosisln;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagLocalRepository extends JpaRepository<Diagnosisln,String> {
}
