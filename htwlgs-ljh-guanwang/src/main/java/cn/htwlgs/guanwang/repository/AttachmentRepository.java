package cn.htwlgs.guanwang.repository;

import cn.htwlgs.guanwang.dtos.enums.AttachmentTypeEnum;
import cn.htwlgs.guanwang.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer>, JpaSpecificationExecutor<Attachment> {
    @Modifying //说明该操作是修改类型操作，删除或者修改
    @Transactional //因为默认是readOnly=true的，这里必须自己进行声明
    @Query(value="delete from Attachment  s where s.id in (:ids)")
    int deleteAllByIdIn(@Param("ids") List<Integer> ids);


    List<Attachment>  findAllByEngTypeAndEngId(AttachmentTypeEnum enumOrdinal, Integer endId);
}