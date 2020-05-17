package cn.htwlgs.bigdata.repository;

import cn.htwlgs.bigdata.entity.WaterMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaterMonitorRepository extends JpaRepository<WaterMonitor, Long>, JpaSpecificationExecutor<WaterMonitor> {

}