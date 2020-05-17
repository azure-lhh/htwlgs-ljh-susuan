package cn.htwlgs.guanwang.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "gw_operate_org")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class OperateOrg implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @Column(name = "detect_user")
    private String detectUser;

    /**
     * 巡检备注
     */
    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_user")
    private LocalDateTime updateUser;

    /**
     * 运营单位
     */
    @Column(name = "unit")
    private String unit;

    @Column(name = "patrol_period")
    private String patrolPeriod;

    
}