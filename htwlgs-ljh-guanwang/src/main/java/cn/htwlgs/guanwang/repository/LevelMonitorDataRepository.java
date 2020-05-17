package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.LevelMonitorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
@Repository
public interface LevelMonitorDataRepository extends JpaRepository<LevelMonitorData, Integer>, JpaSpecificationExecutor<LevelMonitorData> {

    @Query(value = "select monitor_time,wl from gw_level_monitor_data where station_id = ?1 order by monitor_date desc,monitor_time desc",nativeQuery = true)
    List<Map<String, Object>> getWlList(Integer stationId);

    @Query(value = "select monitor_time,traffic from gw_level_monitor_data where station_id = ?1 order by monitor_date desc,monitor_time desc",nativeQuery = true)
    List<Map<String, Object>> getTrafficList(Integer stationId);

    void deleteByIdIn(List<Integer> idList);

    @Query(value = "select a.id,a.artificial,a.wl,a.station_id,a.monitor_date,a.monitor_time,\n" +
            "a.`voltage`,a.traffic,a.`signal`,a.`current`,a.energy,b.area_code,b.station_name,\n" +
            "b.station_code from gw_level_monitor_data a \n" +
            "left join gw_level_monitor b on a.station_id = b.id\n" +
            "where (case when ?1 is not null and ?1 <> '' then b.station_name like concat('%',?1,'%') else 1=1 end) and\n" +
            "(case when ?2 is not null and ?2 <> '' then b.area_code = ?2 else 1=1 end) and\n" +
            "(case when ?3 is not null and ?3 <> '' then a.monitor_date = ?3 else 1=1 end) and\n" +
            "(case when ?4 is not null and ?4 <> '' then a.monitor_time = ?4 else 1=1 end) \n" +
            "order by a.monitor_date desc,a.monitor_time desc limit ?5,?6",nativeQuery = true)
    List<Map<String, Object>> getMonitorList(String name, String area, LocalDate date, LocalTime time, Integer pageNum, Integer pageSize);
}