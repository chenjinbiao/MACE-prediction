package com.example.demo.service;

import com.example.demo.local.modal.Score;

public interface ScoreService {

    double getGrace(String patientId,int visitId);
    Score getScore(String patientId, int visitId);//预测并赋值
    double getCrusade(String patientId,int visitId);
}
