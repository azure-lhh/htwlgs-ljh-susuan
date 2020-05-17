package cn.htwlgs.guanwang.entity;

import cn.htwlgs.guanwang.dtos.enums.EngTypeEnum;
import cn.htwlgs.guanwang.dtos.enums.EngineeringTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 缺陷检查总工程信息表
 */
@Table(name = "gw_eng_info_hz")
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class EngInfoHz implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 工程名称
     */
    @Column(name = "pro_name", nullable = false)
    private String proName;

    /**
     * 行政区域code
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 管段编号
     */
    @Column(name = "pro_part", nullable = false)
    private String proPart;

    /**
     * 工程地址
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * 委托单位
     */
    @Column(name = "entrust_unit")
    private String entrustUnit;

    /**
     * 检测单位
     */
    @Column(name = "detect_unit")
    private String detectUnit;

    /**
     * 检测时间
     */
    @Column(name = "detect_time", nullable = false)
    private LocalDateTime detectTime;

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

    /**
     * 1、删除 0 未删除
     */
    @Column(name = "deleted", nullable = false)
    private Long deleted;

    /**
     * 工程信息类型0、管段1、检查井2、排污口
     */
    @Column(name = "type", nullable = false)
    private EngTypeEnum type;

    @Column(name = "eng_type", nullable = false)
    private EngineeringTypeEnum engType;
}