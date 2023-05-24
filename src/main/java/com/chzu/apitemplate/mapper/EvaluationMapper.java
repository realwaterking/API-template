package com.chzu.apitemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chzu.apitemplate.model.entity.Evaluation;

import java.util.List;

public interface EvaluationMapper extends BaseMapper<Evaluation> {

    Evaluation selectOneById(Long id);

    List<Evaluation> selectAll();
}
