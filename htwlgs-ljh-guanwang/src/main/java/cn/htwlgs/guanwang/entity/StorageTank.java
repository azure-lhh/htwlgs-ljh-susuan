package cn.htwlgs.guanwang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 调蓄池信息表
 */
@Entity
@Data
@Table(name = "gw_storage_tank")
@EntityListeners(AuditingEntityListener.class)
public class StorageTank implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 调蓄池名称
     */
    @Column(name = "tank_name", nullable = false)
    private String tankName;

    /**
     * 所属行政区划编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 编号
     */
    @Column(name = "tank_code", nullable = false)
    private String tankCode;

    /**
     * 内底标高-M
     */
    @Column(name = "insole_elevation")
    private BigDecimal insoleElevation;

    /**
     * 进流量
     */
    @Column(name = "inflow")
    private BigDecimal inflow;

    /**
     * 积水面积
     */
    @Column(name = "catchment_area")
    private BigDecimal catchmentArea;

    /**
     * 初始深度-M
     */
    @Column(name = "initial_depth")
    private BigDecimal initialDepth;

    /**
     * 最大深度-M
     */
    @Column(name = "max_depth")
    private BigDecimal maxDepth;

    /**
     * 超载深度-M
     */
    @Column(name = "overload_depth")
    private BigDecimal overloadDepth;

    /**
     * 经度
     */
    @Column(name = "lon")
    private BigDecimal lon;

    /**
     * 纬度
     */
    @Column(name = "lat")
    private BigDecimal lat;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    @CreatedBy
    private String createUser;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @LastModifiedBy
    @Column(name = "update_user")
    private String updateUser;

    /**
     * 1删除 0 不删除
     */
    @Column(name = "deleted", nullable = false)
    private Long deleted;

    /**
     * 启用时间
     */
    @Column(name = "setup_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;


}