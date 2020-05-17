package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.SewageSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface SewageSpotRepository extends JpaRepository<SewageSpot, Integer>, JpaSpecificationExecutor<SewageSpot> {
    @Query(value="select s.setupTime from SewageSpot s ")
    List<LocalDateTime> getSetupTimeList();

    Optional<SewageSpot> findByPipeNum(String name);


    Page<SewageSpot> findAllByPipeNameLike(String s, Pageable pageable);

    Page<SewageSpot> findAllByAreaCode(String areaCode, Pageable pageable);

    Page<SewageSpot> findAllByAreaCodeAndPipeNameLike(String areaCode, String s, Pageable pageable);

    int deleteByIdIn(List<Integer> idList);

    List<SewageSpot> findByIdIn(List<Integer> idList);

    @Query(value = "select s.id from SewageSpot s ")
    List<Integer> findAllId();
}