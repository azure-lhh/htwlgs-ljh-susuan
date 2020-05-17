package cn.htwlgs.guanwang.entity;

import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.PumpingStationDto;
import cn.htwlgs.guanwang.utils.UserUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
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
 * 管网泵站信息表
 */
@Table(name = "gw_pumping_station")
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class PumpingStation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 泵站id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 泵站名称
     */
    @Column(name = "station_name", nullable = false)
    private String stationName;

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
     * 水位-M
     */
    @Column(name = "wl")
    private BigDecimal wl = BigDecimal.ZERO;

    /**
     * 流量
     */
    @Column(name = "traffic")
    private BigDecimal traffic = BigDecimal.ZERO;

    /**
     * 电压
     */
    @Column(name = "voltage")
    private BigDecimal voltage = BigDecimal.ZERO;

    /**
     * 电流
     */
    @Column(name = "current")
    private BigDecimal current = BigDecimal.ZERO;

    /**
     * 功率
     */
    @Column(name = "power")
    private BigDecimal power = BigDecimal.ZERO;

    /**
     * 开关信号  0 开 1关
     */
    @Column(name = "`signal`")
    private Integer signal = 0;

    /**
     * 故障情况
     */
    @Column(name = "situation")
    private String situation;

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
    @Column(name = "create_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "create_user", nullable = false)
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
    @Column(name = "update_user")
    @LastModifiedBy
    private String updateUser;

    /**
     * 1删除 0 不删除
     */
    @Column(name = "deleted", nullable = false)
    private Long deleted = 0L;

    /**
     * 启用时间
     */
    @Column(name = "setup_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;


    public PumpingStation() {
    }

    public PumpingStation(PumpingStationDto item) {
        BeanUtils.copyProperties(item,this);
        if(Constants.PUMPING_STATION_SIGNAL_ON.equals(item.getSignal())){
            this.setSignal(1);
        }
        if(StringUtils.isEmpty(item.getCreateUser())){
            this.setCreateUser(UserUtils.getUserName());
        }
    }
}