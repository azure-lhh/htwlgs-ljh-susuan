package cn.htwlgs.guanwang.entity;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.SewagePartDto;
import cn.htwlgs.guanwang.dtos.enums.GWLevelEnum;
import cn.htwlgs.guanwang.dtos.enums.ShapeTypeEnum;
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
 * 污水管段信息表
 */
@Table(name = "gw_sewage_part")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SewagePart implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 管网id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 管网名称
     */
    @Column(name = "pipe_name", nullable = false)
    private String pipeName;

    /**
     * 管道编号
     */
    @Column(name = "pipe_num", nullable = false)
    private String pipeNum;

    /**
     * 所属行政区划编码
     */
    @Column(name = "area_code", nullable = false)
    private String areaCode;

    /**
     * 管道级别
     */
    @Column(name = "level", nullable = false)
    private GWLevelEnum level ;

    /**
     * 管道长度-Km
     */
    @Column(name = "length", nullable = false)
    private BigDecimal length = BigDecimal.ZERO;

    /**
     * 管道地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 经度
     */
    @Column(name = "lon", nullable = false)
    private BigDecimal lon;

    /**
     * 纬度
     */
    @Column(name = "lat", nullable = false)
    private BigDecimal lat;

    /**
     * 管道空间拓扑关系
     */
    @Column(name = "relationship")
    private String relationship;

    /**
     * 管道采集设备基础信息
     */
    @Column(name = "equipment")
    private String equipment;

    /**
     * 管道形状（1圆形/2方形）
     */
    @Column(name = "shape", nullable = false)
    private ShapeTypeEnum shape;

    /**
     * 管道内径-M
     */
    @Column(name = "within", nullable = false)
    private BigDecimal within = BigDecimal.ZERO;

    /**
     * 管道外径-M
     */
    @Column(name = "outside", nullable = false)
    private BigDecimal outside = BigDecimal.ZERO;

    /**
     * 管道埋深-M
     */
    @Column(name = "depth", nullable = false)
    private BigDecimal depth = BigDecimal.ZERO;

    /**
     * 出水偏移
     */
    @Column(name = "effluent", nullable = false)
    private Integer effluent = 0;

    /**
     * 入水偏移
     */
    @Column(name = "enter", nullable = false)
    private Integer enter = 0;

    /**
     * 管道上游节点
     */
    @Column(name = "upper_node")
    private String upperNode;

    /**
     * 管道下游节点
     */
    @Column(name = "lower_node")
    private String lowerNode;

    /**
     * 上游底标高-M
     */
    @Column(name = "upper_elevation", nullable = false)
    private BigDecimal upperElevation = BigDecimal.ZERO;

    /**
     * 下游底标高-M
     */
    @Column(name = "lower_elevation", nullable = false)
    private BigDecimal lowerElevation = BigDecimal.ZERO;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    @Column(name = "create_time", nullable = false)
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
     * 运营单位id
     */
    @Column(name = "operate_id")
    private String operateId;

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
    public SewagePart() {
    }

    public SewagePart(SewagePartDto dto){
        CopyUtils.copyProperties(dto,this);
        this.level = GWLevelEnum.getEnumData(dto.getLevel());
        this.shape = ShapeTypeEnum.getEnumData(dto.getShape());
    }
}