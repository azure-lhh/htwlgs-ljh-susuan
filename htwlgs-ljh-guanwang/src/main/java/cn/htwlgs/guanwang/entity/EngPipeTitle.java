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
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 管段检测数据表
 */
@Data
@Table(name = "gw_eng_pipe_title")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EngPipeTitle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 检测方法
     */
    @Column(name = "detect_meth")
    private Long detectMeth;

    /**
     * 起始井号
     */
    @Column(name = "begin_well")
    private String beginWell;

    /**
     * 终止井号
     */
    @Column(name = "end_well")
    private String endWell;

    /**
     * 建设年代
     */
    @Column(name = "steup_time", nullable = false)
    private LocalDateTime steupTime;

    /**
     * 起点埋深
     */
    @Column(name = "begin_depth")
    private BigDecimal beginDepth;

    /**
     * 终止埋深
     */
    @Column(name = "end_depth")
    private BigDecimal endDepth;

    /**
     * 管段材质
     */
    @Column(name = "part_material")
    private String partMaterial;

    /**
     * 管段直径
     */
    @Column(name = "part_radius")
    private BigDecimal partRadius;

    /**
     * 检测方向
     */
    @Column(name = "detect_dir")
    private String detectDir;

    /**
     * 管段长度
     */
    @Column(name = "part_len")
    private BigDecimal partLen;

    /**
     * 检测长度
     */
    @Column(name = "detect_len")
    private BigDecimal detectLen;

    /**
     * 修养指数
     */
    @Column(name = "cul_index")
    private BigDecimal culIndex;

    /**
     * 养护指数
     */
    @Column(name = "mata_index")
    private BigDecimal mataIndex;

    /**
     * 检测地址
     */
    @Column(name = "detect_addr", nullable = false)
    private String detectAddr;

    /**
     * 检测时间
     */
    @Column(name = "detect_time", nullable = false)
    private LocalDateTime detectTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 照片
     */
    @Column(name = "pic")
    private String pic;

    /**
     * 检测单位
     */
    @Column(name = "part_unti")
    private String partUnti;

    /**
     * 检测人员
     */
    @Column(name = "part_user", nullable = false)
    private String partUser;

    /**
     * 核校人员
     */
    @Column(name = "verify_user", nullable = false)
    private String verifyUser;

    /**
     * 督促人员
     */
    @Column(name = "spu_user", nullable = false)
    private String spuUser;
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

    /**
     * 工程id
     */
    @Column(name = "eng_id")
    private Integer engId;

    /**
     * 管段类型
     */
    @Column(name = "part_type")
    private String partType;


    
}