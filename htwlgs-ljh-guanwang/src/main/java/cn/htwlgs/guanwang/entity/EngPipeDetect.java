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
 * 管段缺陷详情表
 */
@Data
@Table(name = "gw_eng_pipe_detect")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EngPipeDetect implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 管段编号
     */
    @Column(name = "part_code", nullable = false)
    private String partCode;

    /**
     * 管径
     */
    @Column(name = "part_radius")
    private BigDecimal partRadius;

    /**
     * 长度
     */
    @Column(name = "part_len")
    private BigDecimal partLen;

    /**
     * 材质
     */
    @Column(name = "part_material")
    private String partMaterial;

    /**
     * 埋深起点
     */
    @Column(name = "begin_depth")
    private String beginDepth;

    /**
     * 埋深终点
     */
    @Column(name = "end_depth")
    private String endDepth;

    /**
     * 结构性缺陷平均值S
     */
    @Column(name = "s_avg")
    private BigDecimal sAvg;

    /**
     * 最大值Smax
     */
    @Column(name = "s_max")
    private BigDecimal sMax;

    /**
     * 缺陷等级
     */
    @Column(name = "s_level")
    private Long sLevel;

    /**
     * 缺陷密度
     */
    @Column(name = "s_density")
    private BigDecimal sDensity;

    /**
     * 修护指数RI
     */
    @Column(name = "cul_index")
    private BigDecimal culIndex;

    /**
     * 综合状况评价
     */
    @Column(name = "s_eval")
    private String sEval;

    /**
     * 功能性缺陷平均值Y
     */
    @Column(name = "g_avg")
    private BigDecimal gAvg;

    /**
     * 最大值Ymax
     */
    @Column(name = "g_max")
    private BigDecimal gMax;

    /**
     * 缺陷等级
     */
    @Column(name = "g_level")
    private Long gLevel;

    /**
     * 缺陷密度
     */
    @Column(name = "g_density")
    private BigDecimal gDensity;

    /**
     * 养护指数
     */
    @Column(name = "mata_index")
    private BigDecimal mataIndex;

    /**
     * 综合状况评价
     */
    @Column(name = "g_eval")
    private String gEval;

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
     * 工程信息id
     */
    @Column(name = "eng_id")
    private Integer engId;

    
}