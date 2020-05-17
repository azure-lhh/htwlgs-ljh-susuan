package cn.htwlgs.guanwang.entity;

import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.SepticTankDto;
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
 * 化粪池信息表
 */
@Data
@Entity
@Table(name = "gw_septic_tank")
@EntityListeners(AuditingEntityListener.class)
public class SepticTank implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 化粪池id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 化粪池名称
     */
    @Column(name = "pool_name", nullable = false)
    private String poolName;

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
     * 化粪池编号
     */
    @Column(name = "pool_code", nullable = false)
    private String poolCode;

    /**
     * 标高-M
     */
    @Column(name = "mark", nullable = false)
    private BigDecimal mark = BigDecimal.ZERO;

    /**
     * 规模
     */
    @Column(name = "scale")
    private String scale;

    /**
     * 处理量
     */
    @Column(name = "handle", nullable = false)
    private BigDecimal handle = BigDecimal.ZERO;

    /**
     * 运行状态 1运行 2未运行
     */
    @Column(name = "state", nullable = false)
    private Integer state = 1;

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

    public SepticTank() {
    }

    public SepticTank(SepticTankDto item) {
        BeanUtils.copyProperties(item,this);
        if(Constants.SEPTIC_TANK_STATE_ON.equals(item.getState())){
            this.setState(2);
        }
        if(StringUtils.isEmpty(item.getCreateUser())){
            this.setCreateUser(UserUtils.getUserName());
        }
    }
}