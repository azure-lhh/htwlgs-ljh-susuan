package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.StorageTank;
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
public interface StorageTankRepository extends JpaRepository<StorageTank, Integer>, JpaSpecificationExecutor<StorageTank> {

    Optional<StorageTank>  findByTankCode(String name);

    @Query(value="select s.setupTime from StorageTank s ")
    List<LocalDateTime> getSetupTimeList();

    @Query(value="from StorageTank s where s.tankName like concat('%',?1,'%') ")
    Page<StorageTank> findAllByTankNameLike(String s, Pageable pageable);


    @Modifying //说明该操作是修改类型操作，删除或者修改
    @Transactional //因为默认是readOnly=true的，这里必须自己进行声明
    @Query(value="delete from StorageTank  s where s.id in (:ids)")
    int deleteAllByIdIn(@Param("ids")List<Integer> ids);

    List<StorageTank> findByIdIn(List<Integer> idList);

    @Query(value = "select s.id from StorageTank s")
    List<Integer> findAllId();



    @Query(value="select  max(insole_elevation) insole_max, min(insole_elevation) insole_min,max(inflow) " +
            "flow_max,min(inflow) flow_min,max(catchment_area) arae_max,min(catchment_area) arae_min from gw_storage_tank",nativeQuery = true)
    Map<String, Object> getStatisticsDto();

    @Query(value = "select `area_code`  `name`, COUNT(`id`) num from gw_storage_tank " +
            "GROUP BY `area_code`  ORDER BY  num desc",nativeQuery = true)
    List<Map<String, Object>> getGroupByState();
}