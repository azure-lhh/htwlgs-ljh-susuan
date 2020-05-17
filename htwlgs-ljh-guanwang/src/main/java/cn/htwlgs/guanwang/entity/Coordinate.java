package cn.htwlgs.guanwang.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 坐标实体
 */
@Entity
@Table(name = "gw_coordinate")
@Data
public class Coordinate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 地址
     */
    @Column(name = "addr", nullable = false)
    private String addr;

    /**
     * 经度
     */
    @Column(name = "lon", nullable = false)
    private BigDecimal lon;

    /**
     * 维度
     */
    @Column(name = "lat", nullable = false)
    private BigDecimal lat;

    /**
     * 下标
     */
    @Column(name = "index", nullable = false)
    private Integer index;

    /**
     * 管段序号
     */
    @Column(name = "serial_no", nullable = false)
    private String serialNo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 修改人
     */
    @Column(name = "update_user")
    private String updateUser;

    /**
     * 1删除 0 不删除
     */
    @Column(name = "deleted")
    private Integer deleted;

    
}