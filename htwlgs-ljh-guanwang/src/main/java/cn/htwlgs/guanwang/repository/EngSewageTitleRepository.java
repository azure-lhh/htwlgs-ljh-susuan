package cn.htwlgs.guanwang.repository;


import cn.htwlgs.guanwang.entity.EngPipeTitle;
import cn.htwlgs.guanwang.entity.EngSewageTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EngSewageTitleRepository extends JpaRepository<EngSewageTitle, Integer>, JpaSpecificationExecutor<EngSewageTitle> {

    EngSewageTitle findByEngId(Integer engId);

}