package cn.htwlgs.guanwang.entity;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.PumpMonitorDto;
import cn.htwlgs.guanwang.utils.UserUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 管网泵站监测表
 */
@Entity
@Table(name = "gw_pump_monitor")
@Data
@EntityListeners(AuditingEntityListener.class)
public class PumpMonitor implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id", insertable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 站点名称
     */
    @Column(name = "station_name", nullable = false)
    private String stationName;

    /**
     * 站点编号
     */
    @Column(name = "station_code", nullable = false)
    private String stationCode;

    /**
     * 所属行政区划编码
     */
    @Column(name = "area_code", nullable = false)
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
     * 纬度
     */
    @Column(name = "lat")
    private BigDecimal lat;

    /**
     * 占地面积
     */
    @Column(name = "area_covered")
    private BigDecimal areaCovered = BigDecimal.ZERO;

    /**
     * 流量
     */
    @Column(name = "traffic")
    private BigDecimal traffic = BigDecimal.ZERO;

    /**
     * 排水体制
     */
    @Column(name = "drain")
    private String drain;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @Column(name = "create_user", nullable = false)
    @CreatedBy
    private String createUser;

    /**
     * 修改人
     */
    @Column(name = "update_user")
    @LastModifiedBy
    private String updateUser;

    /**
     * 运营单位id
     */
    @Column(name = "department_id")
    private String departmentId;

    /**
     * 运营单位名称
     */
    @Column(name = "department_name")
    private String departmentName;

    /**
     * 运营单位联系人
     */
    @Column(name = "department_user")
    private String departmentUser;

    /**
     * 运营单位联系方式
     */
    @Column(name = "department_phone")
    private String departmentPhone;

    /**
     * 通信传输设备
     */
    @Column(name = "communication")
    private String communication;

    /**
     * 监测因子code
     */
    @Column(name = "factor_code")
    private String factorCode;

    /**
     * 监测频次
     */
    @Column(name = "frequency")
    private String frequency;

    /**
     * 设备品牌
     */
    @Column(name = "brand")
    private String brand;

    /**
     * 设备型号
     */
    @Column(name = "model_num")
    private String modelNum;

    /**
     * 站房面积
     */
    @Column(name = "building_area", nullable = false)
    private BigDecimal buildingArea = BigDecimal.ZERO;

    /**
     * 站房至采样点距离
     */
    @Column(name = "distance", nullable = false)
    private BigDecimal distance = BigDecimal.ZERO;

    /**
     * 采样管线长度
     */
    @Column(name = "length", nullable = false)
    private BigDecimal length = BigDecimal.ZERO;

    /**
     * 数据采集仪 品牌
     */
    @Column(name = "collection_brand")
    private String collectionBrand;

    /**
     * 数据采集仪 传输协议
     */
    @Column(name = "transport")
    private String transport;

    /**
     * 数据采集仪 型号
     */
    @Column(name = "collection_model")
    private String collectionModel;

    /**
     * 输出型号类型 todo 不确定类型
     */
    @Column(name = "out_type")
    private Integer outType;

    public PumpMonitor() {}

    public PumpMonitor(PumpMonitorDto item) {
        CopyUtils.copyProperties(item,this);
        if(StringUtils.isEmpty(this.getCreateUser())){
            this.setCreateUser(UserUtils.getUserName());
        }
        if(StringUtils.isEmpty(this.getUpdateUser())){
            this.setUpdateUser(UserUtils.getUserName());
        }
    }
}