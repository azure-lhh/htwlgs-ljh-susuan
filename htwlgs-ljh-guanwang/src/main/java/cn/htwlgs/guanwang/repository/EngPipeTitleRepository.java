package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.EngPipeTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EngPipeTitleRepository extends JpaRepository<EngPipeTitle, Integer> {
        EngPipeTitle findByEngId(Integer engId);


}