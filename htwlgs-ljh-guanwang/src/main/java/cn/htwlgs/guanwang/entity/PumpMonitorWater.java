package cn.htwlgs.guanwang.entity;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.PumpMonitorWaterDto;
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
 * 管网泵站监测--水泵表
 */
@Entity
@Table(name = "gw_pump_monitor_water")
@Data
@EntityListeners(AuditingEntityListener.class)
public class PumpMonitorWater implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id", insertable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 水泵名称
     */
    @Column(name = "pump_name", nullable = false)
    private String pumpName;

    /**
     * 水泵编号
     */
    @Column(name = "pump_code", nullable = false)
    private String pumpCode;

    /**
     * 水泵类型 TODO 不确定类型
     */
    @Column(name = "pump_type", nullable = false)
    private Integer pumpType = 0;

    /**
     * 流量
     */
    @Column(name = "traffic")
    private BigDecimal traffic = BigDecimal.ZERO;

    /**
     * 扬程
     */
    @Column(name = "water_head")
    private BigDecimal waterHead;

    /**
     * 功率
     */
    @Column(name = "power")
    private BigDecimal power = BigDecimal.ZERO;

    /**
     * 近期设置流量--旱季
     */
    @Column(name = "tra_drought")
    private BigDecimal traDrought;

    /**
     * 近期设置流量--雨季
     */
    @Column(name = "tra_rain")
    private BigDecimal traRain;

    /**
     * 开关信号  0 开 1关
     */
    @Column(name = "signal")
    private Integer signal = 0;

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
     * 开关量
     */
    @Column(name = "switch_amount")
    private BigDecimal switchAmount;

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
     * 泵站id
     */
    @Column(name = "station_id", nullable = false)
    private Integer stationId;

    public PumpMonitorWater() {}

    public PumpMonitorWater(PumpMonitorWaterDto item) {
        CopyUtils.copyProperties(item,this);
        if(Constants.PUMPING_STATION_SIGNAL_ON.equals(item.getSignal())){
            this.setSignal(1);
        }
        if(StringUtils.isEmpty(this.getCreateUser())){
            this.setCreateUser(UserUtils.getUserName());
        }
        if(StringUtils.isEmpty(this.getUpdateUser())){
            this.setUpdateUser(UserUtils.getUserName());
        }
    }
}