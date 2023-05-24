package com.chzu.apitemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chzu.apitemplate.model.entity.Evaluation;

import java.util.List;

public interface EvaluationService extends IService<Evaluation> {

    Evaluation update(Evaluation evaluation);

    void delete(Long id);

    List<Evaluation> getEvaluationAll();

    Evaluation getEvaluationById(Long id);

    boolean save(Evaluation evaluation);
}
