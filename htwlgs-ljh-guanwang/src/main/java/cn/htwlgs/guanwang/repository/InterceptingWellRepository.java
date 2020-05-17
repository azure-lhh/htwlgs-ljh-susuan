package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.InterceptingWell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public interface InterceptingWellRepository extends JpaRepository<InterceptingWell, Integer>, JpaSpecificationExecutor<InterceptingWell> {
    @Query(value="select s.setupTime from InterceptingWell s")
    List<LocalDateTime> getSetupTimeList();

    Optional<InterceptingWell> findByWellCode(String wellCode);

    @Query(value="from InterceptingWell s where s.wellName like concat('%',?1,'%') ")
    Page<InterceptingWell> findAllByWellNameLike(String name, Pageable pageable);

   @Query(value = "select i.id from InterceptingWell i")
   List<Integer> findAllId();

    @Modifying //说明该操作是修改类型操作，删除或者修改
    @Transactional //因为默认是readOnly=true的，这里必须自己进行声明
    @Query(value="delete from InterceptingWell  s where s.id in (:ids)")
    int deleteAllByIdIn(@Param("ids")List<Integer> ids);

    //统计 区域数量
    @Query(value = "select `area_code`  `name`, COUNT(`id`) num from gw_intercepting_well GROUP BY `area_code`  ORDER BY  name desc",nativeQuery = true)
    List<Map<String, Object>>  findGroupByAreaCode();


    @Query(value = "select  max(insole_elevation) insole_max, min(insole_elevation) insole_min,max(inflow) flow_max,min(inflow) flow_min,max(catchment_area) arae_max,min(catchment_area) arae_min from gw_intercepting_well",nativeQuery = true)
    Map<String, Object> getStatisticsDto();










}