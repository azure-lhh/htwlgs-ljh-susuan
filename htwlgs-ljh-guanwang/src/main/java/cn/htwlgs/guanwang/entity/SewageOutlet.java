package cn.htwlgs.guanwang.entity;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.SewageOutletDto;
import cn.htwlgs.guanwang.dtos.enums.PFTypeEnum;
import cn.htwlgs.guanwang.dtos.enums.PFWayEnum;
import cn.htwlgs.guanwang.dtos.enums.SewageTypeEnum;
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
 * 污水口信息表
 */
@Data
@Entity
@Table(name = "gw_sewage_outlet")
@EntityListeners(AuditingEntityListener.class)
public class SewageOutlet implements Serializable {
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
     * 区域编号
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

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
     * 检查井编号
     */
    @Column(name = "serial_no")
    private String serialNo;

    /**
     * 井深
     */
    @Column(name = "depth")
    private BigDecimal depth;

    /**
     * 排污类型 0、类型 1、污水
     */
    @Column(name = "type", nullable = false)
    private PFTypeEnum type;

    /**
     * 规模
     */
    @Column(name = "scale")
    private String scale;

    /**
     * 排放方式
     */
    @Column(name = "pfway")
    private PFWayEnum pfway;

    /**
     * 类型
     */
    @Column(name = "sewage_type")
    private SewageTypeEnum sewageType;

    /**
     * 是否处于保护区
     */
    @Column(name = "reserves")
    private boolean reserves;

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
    @Column(name = "update_user")
    @LastModifiedBy
    private String updateUser;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    @CreatedBy
    private String createUser;

    /**
     * 创建时间 
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createTime;

    /**
     * 1删除 0 不删除
     */
    @Column(name = "deleted", nullable = false)
    private Long deleted;

    /**
     * 水功能区编码
     */
    @Column(name = "water_func")
    private String waterFunc;

    /**
     * 水资源分区
     */
    @Column(name = "water_region")
    private String waterRegion;

    /**
     * 河湖编号
     */
    @Column(name = "river")
    private String river;

    /**
     * 存在问题
     */
    @Column(name = "remark")
    private String remark;


    /**
     * 启用时间
     */
    @Column(name = "setup_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;

    public SewageOutlet(){

    }

    public SewageOutlet(SewageOutletDto dto) {
        CopyUtils.copyProperties(dto,this);
        this.type =  PFTypeEnum.getEnumData(dto.getType());
        this.pfway = PFWayEnum.getEnumData(dto.getPfway());
        this.sewageType = SewageTypeEnum.getEnumData(dto.getSewageType());
    }



}