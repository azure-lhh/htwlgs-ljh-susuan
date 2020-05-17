package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.SewageOutlet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface SewageOutletRepository extends JpaRepository<SewageOutlet, Integer> {
    @Query(value="select s.setupTime from SewageOutlet s ")
    List<LocalDateTime> getSetupTimeList();

    Optional<SewageOutlet> findBySerialNo(String serialNo);

    Page<SewageOutlet> findAllByAreaCode(String area, Pageable pageable);

    @Query(value="from SewageOutlet s where s.name like concat('%',?1,'%') ")
    Page<SewageOutlet>  findAllByNameLike(String area, Pageable pageable);

    @Modifying //说明该操作是修改类型操作，删除或者修改
    @Transactional //因为默认是readOnly=true的，这里必须自己进行声明
    @Query(value="delete from SewageOutlet  s where s.id in (:ids)")
    int deleteAllByIdIn(@Param("ids")List<Integer> ids);


    List<SewageOutlet> findAllByIdIn(List<Integer> idList);

    @Query(value="select s.id from SewageOutlet s")
    List<Integer> findAllId();

    @Query(value="select `area_code`  `name`, COUNT(`id`) num from `gw_sewage_outlet`  GROUP BY `area_code`  ORDER BY  num desc;",nativeQuery=true)
    List<Map<String,Object>> getGroupByAreaCode();


    @Query(value="select `type`  `name`, COUNT(`id`) num from `gw_sewage_outlet`  GROUP BY `type`  ORDER BY  num desc;",nativeQuery=true)
    List<Map<String,Object>>  getGroupByType();

    @Query(value="select `pfway`  `name`, COUNT(`id`) num from `gw_sewage_outlet`  GROUP BY `pfway`  ORDER BY  num desc;",nativeQuery=true)
    List<Map<String,Object>> getGroupByPFWay();
}