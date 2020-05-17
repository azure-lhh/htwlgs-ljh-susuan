package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.PumpMonitorWater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PumpMonitorWaterRepository extends JpaRepository<PumpMonitorWater, Integer>, JpaSpecificationExecutor<PumpMonitorWater> {

    void deleteByIdIn(List<Integer> idList);

    List<PumpMonitorWater> findAllByStationId(Integer stationId);

    //Page<PumpMonitorWater> findAllByStationId(Integer stationId, Pageable pageable);
}