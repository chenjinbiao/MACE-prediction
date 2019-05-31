package com.example.demo.hospital;

import com.example.demo.local.modal.LocalTestItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalTestItemRespository extends JpaRepository<HospitalTestItem,String> {
}
