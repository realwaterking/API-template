package com.chzu.apitemplate.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chzu.apitemplate.mapper.EvaluationMapper;
import com.chzu.apitemplate.model.entity.Evaluation;
import com.chzu.apitemplate.service.EvaluationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {

    @Resource
    private EvaluationMapper evaluationMapper;

    @Override
    public Evaluation update(Evaluation evaluation) {
        Long id = Long.valueOf(evaluationMapper.insert(evaluation));
        return evaluationMapper.selectOneById(id);
    }

    @Override
    public void delete(Long id) {
        evaluationMapper.deleteById(id);
    }

    @Override
    public List<Evaluation> getEvaluationAll() {
        return evaluationMapper.selectAll();
    }

    @Override
    public Evaluation getEvaluationById(Long id) {
        return evaluationMapper.selectOneById(id);
    }

    @Override
    public boolean save(Evaluation evaluation) {
        if (evaluationMapper.insert(evaluation) >= 0) {
            return true;
        } else return false;
//        return evaluationMapper.selectOneById(id);
    }
}
