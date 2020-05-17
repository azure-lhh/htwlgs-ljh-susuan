package cn.htwlgs.guanwang.entity;

import cn.htwlgs.guanwang.dtos.enums.ShapeTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检查井信息表信息表
 */
@Entity
@Table(name = "gw_manhole")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Manhole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 行政区编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 检查井地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 检查井编号
     */
    @Column(name = "serial_no")
    private String serialNo;

    /**
     * 井深
     */
    @Column(name = "depth")
    private Double depth;

    /**
     * 管理单位code
     */
    @Column(name = "man_unit")
    private String manUnit;

    /**
     * 井材质
     */
    @Column(name = "material")
    private String material;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    @LastModifiedBy
    private String createUser;

    /**
     * 埋深时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedBy
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updateTime;

    @Column(name = "update_user")
    @LastModifiedBy
    private String updateUser;

    /**
     * 1删除 0 不删除
     */
    @Column(name = "deleted")
    private Long deleted;

    /**
     * 经度
     */
    @Column(name = "lon")
    private BigDecimal lon;

    /**
     * 维度
     */
    @Column(name = "lat")
    private BigDecimal lat;

    /**
     * 井盖形状
     */
    @Column(name = "mhe_shape")
    private ShapeTypeEnum mheShape;

    /**
     * 启用时间
     */
    @Column(name = "setup_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;

    
}