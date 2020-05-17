package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.dtos.enums.EngTypeEnum;
import cn.htwlgs.guanwang.dtos.enums.EngineeringTypeEnum;
import cn.htwlgs.guanwang.entity.EngInfo;
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
public interface EngInfoRepository extends JpaRepository<EngInfo, Integer>, JpaSpecificationExecutor<EngInfo> {
    Page<EngInfo> findAllByEngType(EngineeringTypeEnum engType , Pageable pageable);

    Page<EngInfo> findAllByEngTypeAndAreaCode(EngineeringTypeEnum engType , String area, Pageable pageable);

    @Modifying //说明该操作是修改类型操作，删除或者修改
    @Transactional //因为默认是readOnly=true的，这里必须自己进行声明
    @Query(value="delete from EngInfo  s where s.id in (:ids)")
    int deleteAllByIdIn(@Param("ids") List<Integer> ids);

    Page<EngInfo> findAllByEngTypeAndType(EngineeringTypeEnum engType, EngTypeEnum type , Pageable pageable);

    Page<EngInfo> findAllByEngTypeAndTypeAndProPart(EngineeringTypeEnum engType, EngTypeEnum type , String area, Pageable pageable);
}