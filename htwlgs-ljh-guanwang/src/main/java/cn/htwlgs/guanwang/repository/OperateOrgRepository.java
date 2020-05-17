package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.entity.OperateOrg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateOrgRepository extends JpaRepository<OperateOrg, Integer>, JpaSpecificationExecutor<OperateOrg> {


}