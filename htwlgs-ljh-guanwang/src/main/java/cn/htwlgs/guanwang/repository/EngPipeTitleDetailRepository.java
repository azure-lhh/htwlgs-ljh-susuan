package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.EngPipeTitle;
import cn.htwlgs.guanwang.entity.EngPipeTitleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EngPipeTitleDetailRepository extends JpaRepository<EngPipeTitleDetail, Integer>, JpaSpecificationExecutor<EngPipeTitleDetail> {

   List<EngPipeTitleDetail>  findByEngId(Integer engId);

   void deleteByEngId(Integer engId);
}