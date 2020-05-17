package cn.htwlgs.bigdata.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 水监测结果保存
 */
@Builder(toBuilder=true)
@Data
@Entity
@Table(name = "bd_water_monitor")
@EntityListeners(AuditingEntityListener.class)
public class WaterMonitor implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    @Column(name = "type")
    private Integer  type;

    @Column(name = "serialNo")
    private  String serialNo;

    @Column(name = "basin")
    private String basin;

    @Column(name = "temp")
    private Double wTemp;

    @Column(name = "ph")
    private Double ph;

    @Column(name = "level")
    private  Integer level;

    @Column(name = "num")
    private Double  num;

    @Column(name = "key_pollutant")
    private  String keyPollutant;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updateTime;
}