package cn.htwlgs.guanwang.repository;


import cn.htwlgs.guanwang.entity.SewagePart;
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
public interface SewagePartRepository extends JpaRepository<SewagePart, Integer>, JpaSpecificationExecutor<SewagePart> {

        Optional<SewagePart> findByPipeNum(String name);

        @Query(value="select s.setupTime from SewagePart s ")
        List<LocalDateTime>  getSetupTimeList();

        @Query(value="select s.id from SewagePart s ")
        List<Integer>  getIdList();

        @Query(value="SELECT `name` from (SELECT  pipe_num `name` FROM gw_sewage_part UNION  SELECT  pipe_num FROM gw_rainwater_part )   s ",nativeQuery = true)
        List<String> findAllPipeNum();

        Page<SewagePart>  findAllByAreaCode(String area, Pageable pageable);

        Page<SewagePart>  findAllByPipeNameLike(String area, Pageable pageable);

        Page<SewagePart> findAllByAreaCodeAndPipeNameLike(String areaCode, String s, Pageable pageable);

        //本地查询 nativeQuery=true 表示使用原生sql，表名、字段名跟数据库一致
        @Query(value="SELECT `name` from (SELECT  pipe_num `name` FROM gw_sewage_part  UNION  SELECT  pipe_num FROM gw_rainwater_part  )   s WHERE  s.name = :name",nativeQuery = true)
        List<Map<String,Object>>  findAllByPartNum(@Param("name")String name);

        @Query(value="SELECT `name` from (SELECT  pipe_num `name` FROM gw_sewage_part  UNION  SELECT  pipe_num FROM gw_rainwater_part  )   s WHERE  s.name  in (:names)",nativeQuery = true)
        List<Map<String,Object>>  findAllByPartNumIn(@Param("names")List<String> names);

        List<SewagePart>  findByIdIn(List<Integer> ids);

        @Modifying //说明该操作是修改类型操作，删除或者修改
        @Transactional //因为默认是readOnly=true的，这里必须自己进行声明
        @Query(value="delete from SewagePart  s where s.id in (:ids)")
        int deleteAllByIdIn(@Param("ids")List<Integer> ids);

        @Query(value="SELECT sum(length) length_sum,sum(if(year(setup_time) > year(now()),0,length)) length_add,max(depth) depth_max,min(depth) depth_min from gw_sewage_part  ",nativeQuery=true)
        Map<String,Object>  getStatisticsDto();


        @Query(value="select `level` `name`,count(distinct id) num from gw_sewage_part  GROUP BY `level`  ORDER BY  num desc",nativeQuery=true)
        List<Map<String,Object>>  getLevelList();


        @Query(value="select `area_code`  `name`, COUNT(`id`) num from gw_sewage_part GROUP BY `area_code`  ORDER BY  num desc",nativeQuery=true)
        List<Map<String,Object>>  getGroupByState();

}