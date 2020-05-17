package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.LevelMonitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LevelMonitorRepository extends JpaRepository<LevelMonitor, Integer>, JpaSpecificationExecutor<LevelMonitor> {

    Page<LevelMonitor> findAllByAreaCode(String areaCode, Pageable pageable);

    Page<LevelMonitor> findAllByAreaCodeAndStationNameLike(String areaCode, String stationName, Pageable pageable);

    Page<LevelMonitor> findAllByStationNameLike(String stationName, Pageable pageable);

    void deleteByIdIn(List<Integer> ids);
}