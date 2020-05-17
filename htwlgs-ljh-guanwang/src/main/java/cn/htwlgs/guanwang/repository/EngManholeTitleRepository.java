package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.EngManholeTitle;
import cn.htwlgs.guanwang.entity.EngPipeTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EngManholeTitleRepository extends JpaRepository<EngManholeTitle, Integer>, JpaSpecificationExecutor<EngManholeTitle> {
    EngManholeTitle findByEngId(Integer engId);

}