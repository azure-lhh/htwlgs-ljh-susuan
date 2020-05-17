package cn.htwlgs.guanwang.entity;

import cn.htwlgs.guanwang.dtos.enums.PFTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 排污口工程检查表
 */
@Entity
@Table(name = "gw_eng_sewage_title")
@Data
@EntityListeners(AuditingEntityListener.class)
public class EngSewageTitle implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;



    /**
     * 水体名称
     */
    @Column(name = "wtbd")
    private String wtbd;

    /**
     * 河段
     */
    @Column(name = "river")
    private String river;

    /**
     * 调查地址
     */
    @Column(name = "detect_addr")
    private String detectAddr;

    /**
     * 调查时间
     */
    @Column(name = "detect_time")
    private LocalDateTime detecttime;

    /**
     * 天气情况
     */
    @Column(name = "weather")
    private String weather;

    /**
     * 调查人签字
     */
    @Column(name = "detect_user")
    private String detectUser;

    /**
     * 调查单位
     */
    @Column(name = "detect_unit")
    private String detectUnit;

    /**
     * 排水口类型
     */
    @Column(name = "outfall_type")
    private PFTypeEnum outfallType;

    /**
     * 排水口编号
     */
    @Column(name = "outfall_code")
    private String outfallCode;

    /**
     * 排水口经度x
     */
    @Column(name = "lon")
    private BigDecimal lon;

    /**
     * 排水口维度y
     */
    @Column(name = "lat")
    private BigDecimal lat;

    /**
     * 排水口断面形式
     */
    @Column(name = "section_form")
    private String sectionForm;

    /**
     * 排水口尺寸大小
     */
    @Column(name = "section_size")
    private Double sectionSize;

    /**
     * 排水口材质
     */
    @Column(name = "material")
    private String material;

    /**
     * 出流形式
     */
    @Column(name = "outfall_way")
    private String outfallWay;

    /**
     * 管低高程
     */
    @Column(name = "high_low")
    private String highLow;

    /**
     * 末端控制
     */
    @Column(name = "end_control")
    private String endControl;

    /**
     * 水体水位
     */
    @Column(name = "water_level")
    private String waterLevel;

    /**
     * 编号
     */
    @Column(name = "voucher_code")
    private String voucherCode;

    /**
     * 旱天排水量
     */
    @Column(name = "dro_drainage")
    private BigDecimal droDrainage;

    /**
     * 旱天CODcr
     */
    @Column(name = "dro_codcr")
    private BigDecimal droCodcr;

    /**
     * 旱天氨氮
     */
    @Column(name = "dro_nh3n")
    private BigDecimal droNh3n;

    /**
     * 旱天总磷
     */
    @Column(name = "dro_p")
    private BigDecimal droP;

    /**
     * 雨天排水量
     */
    @Column(name = "rainy_drainage")
    private BigDecimal rainyDrainage;

    /**
     * 雨天CODcr
     */
    @Column(name = "rainy_codcr")
    private BigDecimal rainyCodcr;

    /**
     * 雨天氨氮
     */
    @Column(name = "rainy_nh3n")
    private BigDecimal rainyNh3n;

    /**
     * 雨天总磷
     */
    @Column(name = "rainy_p")
    private BigDecimal rainyP;

    /**
     * 图片编号
     */
    @Column(name = "pic_code")
    private String picCode;

    /**
     * 工程信息id
     */
    @Column(name = "eng_id")
    private Integer engId;

    /**
     * 是否删除
     */
    @Column(name = "deleted")
    private Long deleted;

    /**
     * 修改时间
     */
    @Column(name = "update_time", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateTime;


    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @CreatedDate
    private LocalDateTime createTime;


    /**
     * 创建人
     */
    @Column(name = "create_user")
    @CreatedBy
    private String createUser;

    /**
     * 修改人
     */
    @Column(name = "update_user")
    @LastModifiedBy
    private String updateUser;
}