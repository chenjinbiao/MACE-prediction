package com.example.demo.hospital;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaRepository extends JpaRepository<Diagnosisln,String> {

//    List<Diagnosisln> saveAndFlashAll(Iterable<Diagnosisln> var1);

}
