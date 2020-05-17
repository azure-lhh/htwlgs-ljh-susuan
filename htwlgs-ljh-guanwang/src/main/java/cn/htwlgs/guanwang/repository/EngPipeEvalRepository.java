package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.EngPipeEval;
import cn.htwlgs.guanwang.entity.EngPipeTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EngPipeEvalRepository extends JpaRepository<EngPipeEval, Integer>, JpaSpecificationExecutor<EngPipeEval> {
    EngPipeEval findByEngId(Integer engId);
}