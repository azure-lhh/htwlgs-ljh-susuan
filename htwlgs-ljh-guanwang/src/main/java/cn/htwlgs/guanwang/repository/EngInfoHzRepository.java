package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.dtos.enums.EngineeringTypeEnum;
import cn.htwlgs.guanwang.entity.EngInfoHz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface EngInfoHzRepository extends JpaRepository<EngInfoHz, Integer>, JpaSpecificationExecutor<EngInfoHz> {
    @Modifying //说明该操作是修改类型操作，删除或者修改
    @Transactional //因为默认是readOnly=true的，这里必须自己进行声明
    @Query(value="delete from EngInfoHz  s where s.id in (:ids)")
    int deleteAllByIdIn(@Param("ids") List<Integer> ids);

    List<EngInfoHz> findByIdIn(List<Integer> idList);

    Page<EngInfoHz> findAllByEngType(EngineeringTypeEnum engType , Pageable pageable);

    Page<EngInfoHz> findAllByEngTypeAndAreaCode(EngineeringTypeEnum engType ,String area, Pageable pageable);

    @Query(value="from EngInfoHz e where e.engType = ?1 and concat(e.proName,e.proPart) like ?2 ")
    Page<EngInfoHz>  findAllByEngTypePipeNameLike(EngineeringTypeEnum engType , String area, Pageable pageable);
}