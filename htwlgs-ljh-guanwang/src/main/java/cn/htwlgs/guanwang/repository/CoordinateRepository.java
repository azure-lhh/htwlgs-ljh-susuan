package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Integer>, JpaSpecificationExecutor<Coordinate> {

}