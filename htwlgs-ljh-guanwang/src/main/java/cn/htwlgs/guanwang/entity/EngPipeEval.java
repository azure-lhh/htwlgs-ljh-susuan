package cn.htwlgs.guanwang.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 管段缺陷评估
 */
@Entity
@Table(name = "gw_eng_pipe_eval")
@Data
@EntityListeners(AuditingEntityListener.class)
public class EngPipeEval implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 起始井
     */
    @Column(name = "begin_well")
    private String beginWell;

    /**
     * 终止井
     */
    @Column(name = "end_well", nullable = false)
    private String endWell;

    /**
     * 缺陷类型
     */
    @Column(name = "defect_type")
    private Long defectType;

    /**
     * 缺陷位置纵向
     */
    @Column(name = "defect_x")
    private String defectX;

    /**
     * 缺陷位置横向
     */
    @Column(name = "defect_y")
    private String defectY;

    /**
     * 缺陷等级
     */
    @Column(name = "defect_level")
    private Long defectLevel;

    /**
     * 修护建议
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 修护方式
     */
    @Column(name = "defect_meth")
    private String defectMeth;
    /**
     * 修改时间
     */
    @Column(name = "update_time", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateTime;


    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @CreatedDate
    private LocalDateTime createTime;


    /**
     * 创建人
     */
    @Column(name = "create_user")
    @CreatedBy
    private String createUser;

    /**
     * 修改人
     */
    @Column(name = "update_user")
    @LastModifiedBy
    private String updateUser;

    @Column(name = "deleted", nullable = false)
    private Long deleted;

    @Column(name = "eng_id")
    private Integer engId;

    
}