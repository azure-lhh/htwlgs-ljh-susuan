package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.EngPipeDetect;
import cn.htwlgs.guanwang.entity.EngPipeTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EngPipeDetectRepository extends JpaRepository<EngPipeDetect, Integer>, JpaSpecificationExecutor<EngPipeDetect> {
    EngPipeDetect findByEngId(Integer engId);
}