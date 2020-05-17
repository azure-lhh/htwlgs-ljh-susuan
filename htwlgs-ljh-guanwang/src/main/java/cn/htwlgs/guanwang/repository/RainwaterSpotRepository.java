package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.RainwaterSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface RainwaterSpotRepository extends JpaRepository<RainwaterSpot, Integer>{
    @Query(value="select s.setupTime from RainwaterSpot s")
    List<LocalDateTime> getSetupTimeList();

    Page<RainwaterSpot> findAllByPipeNameLike(String s, Pageable pageable);

    Page<RainwaterSpot> findAllByAreaCode(String areaCode, Pageable pageable);

    Page<RainwaterSpot> findAllByAreaCodeAndPipeNameLike(String areaCode,String s, Pageable pageable);

    Optional<RainwaterSpot> findByPipeNum(String name);

    void deleteByIdIn(List<Integer> idList);

    List<RainwaterSpot> findByIdIn(List<Integer> idList);

    @Query(value = "select id from gw_rainwater_spot",nativeQuery = true)
    List<Integer> findAllId();
}