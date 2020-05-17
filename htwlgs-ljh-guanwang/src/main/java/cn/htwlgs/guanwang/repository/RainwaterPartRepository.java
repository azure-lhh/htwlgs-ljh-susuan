package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.RainwaterPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public interface RainwaterPartRepository extends JpaRepository<RainwaterPart, Integer>, JpaSpecificationExecutor<RainwaterPart> {
    @Query(value="select s.setupTime from RainwaterPart s")
    List<LocalDateTime> getSetupTimeList();

    Page<RainwaterPart> findAllByAreaCode(String areaCode, Pageable pageable);

    Page<RainwaterPart> findAllByPipeNameLike(String s, Pageable pageable);

    Page<RainwaterPart> findAllByAreaCodeAndPipeNameLike(String areaCode, String s, Pageable pageable);



    void deleteByIdIn(List<Integer> ids);

    Optional<RainwaterPart> findByPipeNum(String name);

    @Query(value="SELECT `name` from (SELECT  pipe_num `name` FROM gw_sewage_part  UNION  SELECT  pipe_num FROM gw_rainwater_part  )   s WHERE  s.name = :name",nativeQuery = true)
    List<Map<String,Object>>  findAllByPartNum(@Param("name")String name);

    @Query(value="SELECT `name` from (SELECT  pipe_num `name` FROM gw_sewage_part  UNION  SELECT  pipe_num FROM gw_rainwater_part  )   s WHERE  s.name  in (:names)",nativeQuery = true)
    List<Map<String,Object>>  findAllByPartNumIn(@Param("names")List<String> names);

    List<RainwaterPart> findByIdIn(List<Integer> idList);

    @Query(value = "select id from gw_rainwater_part",nativeQuery = true)
    List<Integer> findAllId();

    @Query(value = "select `area_code` `name`, COUNT(`id`) num from gw_rainwater_part GROUP BY `area_code` ORDER BY num desc",nativeQuery = true)
    List<Map<String, Object>> getGroupByState();

    @Query(value = "select sum(length) length_sum,sum(if(year(setup_time) > year(now()),0,length)) length_add," +
            "max(depth) depth_max,min(depth) depth_min from gw_rainwater_part ",nativeQuery = true)
    Map<String, Object> getStatisticsDto();

    @Query(value = "select `level` `name`,count(distinct id) num from gw_rainwater_part GROUP BY `level` ORDER BY num desc",nativeQuery = true)
    List<Map<String, Object>> getLevelList();

    @Query(value = " SELECT `id`,`setup_time`,`pipe_name`,`pipe_num`,`level`,`length`,`address`,`lon`,`lat`,`relationship`,`equipment`,`shape`,`within`,`outside`,`depth`,`effluent`,`enter`,`upper_node`," +
            "`lower_node`,`upper_elevation`,`lower_elevation`,`create_time`,`create_user`,`update_time`,`update_user`,`operate_id` FROM gw_rainwater_part ORDER BY pipe_num asc ",nativeQuery = true)
    List<Map<String, Object>> getPartRadiis();
}