package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.SepticTank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public interface SepticTankRepository extends JpaRepository<SepticTank, Integer>{
    @Query(value="from SepticTank s where s.poolName like concat('%',?1,'%') ")
    Page<SepticTank> findAllByPoolNameLike(String s, Pageable pageable);

    void deleteByIdIn(List<Integer> idList);

    Optional<SepticTank> findByPoolCode(String poolCode);

    List<SepticTank> findByIdIn(List<Integer> idList);

    @Query(value = "select id from gw_septic_tank",nativeQuery = true)
    List<Integer> findAllId();

    @Query(value="select s.setupTime from SepticTank s")
    List<LocalDateTime>  getSetupTimeList();

    @Query(value="select sum(`state` =1) state_off, sum(`state` =2) state_on,max(handle) handle_max,min(handle) handle_min," +
            "max(mark) mark_max,min(mark) mark_min from gw_septic_tank ",nativeQuery = true)
    Map<String, Object> getStatisticsDto();

    @Query(value = "select `area_code`  `name`, COUNT(`id`) num from gw_septic_tank " +
            "GROUP BY `area_code`  ORDER BY  num desc",nativeQuery = true)
    List<Map<String, Object>> getGroupByState();
}