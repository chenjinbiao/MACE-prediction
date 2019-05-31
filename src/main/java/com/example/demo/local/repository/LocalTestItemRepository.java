package com.example.demo.local.repository;

import com.example.demo.local.modal.Diagnosisln;
import com.example.demo.local.modal.LocalTestItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalTestItemRepository  extends JpaRepository<LocalTestItem,String> {
}
