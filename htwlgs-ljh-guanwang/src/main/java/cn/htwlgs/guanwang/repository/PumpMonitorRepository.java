package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.PumpMonitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PumpMonitorRepository extends JpaRepository<PumpMonitor, Integer>, JpaSpecificationExecutor<PumpMonitor> {

    void deleteByIdIn(List<Integer> idList);

    Page<PumpMonitor> findAllByAreaCodeAndStationNameLike(String areaCode, String stationName, Pageable pageable);

    Page<PumpMonitor> findAllByAreaCode(String areaCode, Pageable pageable);

    Page<PumpMonitor> findAllByStationNameLike(String stationName, Pageable pageable);
}