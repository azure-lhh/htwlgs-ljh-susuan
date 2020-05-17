package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.PumpingStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Repository
public interface PumpingStationRepository extends JpaRepository<PumpingStation, Integer>, JpaSpecificationExecutor<PumpingStation> {
    @Query(value="select s.setupTime from PumpingStation s")
    List<LocalDateTime> getSetupTimeList();

    @Query(value="from PumpingStation s where s.stationName like concat('%',?1,'%') ")
    Page<PumpingStation> findAllByStationNameLike(String s, Pageable pageable);

//    Optional<PumpingStation> findByPoolCode(String poolCode);

    void deleteByIdIn(List<Integer> ids);

    List<PumpingStation> findByIdIn(List<Integer> idList);

    @Query(value = "select sum(`signal` =0) signal_off, sum(`signal` =1) signal_on,max(wl) wl_max,min(wl) wl_min," +
            "max(traffic) traffic_max,min(traffic) traffic_min from gw_pumping_station ",nativeQuery = true)
    Map<String, Object> getpumpingStatisticsDto();

    @Query(value = "select id from gw_pumping_station",nativeQuery = true)
    List<Integer> findAllId();

    @Query(value = "select `area_code` `name`, COUNT(`id`) num from gw_pumping_station GROUP BY `area_code` ORDER BY num desc",nativeQuery = true)
    List<Map<String, Object>> getGroupByState();
}