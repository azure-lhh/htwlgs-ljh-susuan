package cn.htwlgs.guanwang.entity;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.PumpMonitorDataDto;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 管网泵站监测--监测数据表
 */
@Data
@Table(name = "gw_pump_monitor_data")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PumpMonitorData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id", insertable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 泵站id
     */
    @Column(name = "station_id", nullable = false)
    private Integer stationId;

    /**
     * 是否手动填报 0不是 1是
     */
    @Column(name = "artificial", nullable = false)
    private Integer artificial = 0;

    /**
     * 监测日期
     */
    @Column(name = "monitor_date", nullable = false)
    private LocalDate monitorDate;

    /**
     * 监测时间
     */
    @Column(name = "monitor_time", nullable = false)
    private LocalTime monitorTime;

    /**
     * 实时液位
     */
    @Column(name = "level")
    private BigDecimal level = BigDecimal.ZERO;

    /**
     * 流量
     */
    @Column(name = "traffic")
    private BigDecimal traffic = BigDecimal.ZERO;

    /**
     * 开关信号  0 开 1关
     */
    @Column(name = "`signal`")
    private Integer signal = 0;

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
     * 蓄水量
     */
    @Column(name = "storage")
    private BigDecimal storage;

    /**
     * 开度大小
     */
    @Column(name = "opening")
    private String opening;

    public PumpMonitorData() {}

    public PumpMonitorData(PumpMonitorDataDto item) {
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