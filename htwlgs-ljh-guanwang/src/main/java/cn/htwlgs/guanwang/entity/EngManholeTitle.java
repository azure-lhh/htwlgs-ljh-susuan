package cn.htwlgs.guanwang.entity;

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
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 检查井工程检查表
 */
@Table(name = "gw_eng_manhole_title")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EngManholeTitle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 任务名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 年代
     */
    @Column(name = "setup_time")
    private Date setupTime;

    /**
     * 材质
     */
    @Column(name = "material", nullable = false)
    private String material;

    /**
     * 性质
     */
    @Column(name = "nature", nullable = false)
    private String nature;

    /**
     * 监测单位
     */
    @Column(name = "detect_unit", nullable = false)
    private String detectUnit;

    /**
     * 检查井编号
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 井盖埋没
     */
    @Column(name = "bury")
    private String bury;

    /**
     * 井盖丢失
     */
    @Column(name = "lost")
    private String lost;

    /**
     * 井盖破损
     */
    @Column(name = "w_worn")
    private String wWorn;

    /**
     * 井框破损
     */
    @Column(name = "f_worn")
    private String fWorn;

    /**
     * 盖框缝隙
     */
    @Column(name = "wf_crack")
    private String wfCrack;

    /**
     * 盖框高差
     */
    @Column(name = "wf_ff")
    private String wfFf;

    /**
     * 盖框突出或凹陷
     */
    @Column(name = "wf_sunken")
    private String wfSunken;

    /**
     * 跳动或声响
     */
    @Column(name = "noise")
    private String noise;

    /**
     * 周边路面破损或沉降
     */
    @Column(name = "highway")
    private String highway;

    /**
     * 井盖标识错误
     */
    @Column(name = "flag_err")
    private String flagErr;

    /**
     * 是否为重型井盖
     */
    @Column(name = "heavy")
    private String heavy;

    /**
     * 其他
     */
    @Column(name = "oth")
    private String oth;

    /**
     * 链条或锁具
     */
    @Column(name = "insd_chain")
    private String insdChain;

    /**
     * 爬梯松动锈蚀或缺损
     */
    @Column(name = "insd_ladder")
    private String insdLadder;

    /**
     * 井壁泥垢
     */
    @Column(name = "insd_dirt")
    private String insdDirt;

    /**
     * 井壁裂缝
     */
    @Column(name = "insd_crack")
    private String insdCrack;

    /**
     * 井壁渗漏
     */
    @Column(name = "insd_leaks")
    private String insdLeaks;

    /**
     * 抹面脱落
     */
    @Column(name = "insd_off")
    private String insdOff;

    /**
     * 管口孔洞
     */
    @Column(name = "insd_hole")
    private String insdHole;

    /**
     * 流槽破损
     */
    @Column(name = "insd_launder")
    private String insdLaunder;

    /**
     * 井底积泥、杂物
     */
    @Column(name = "insd_mud")
    private String insdMud;

    /**
     * 水流不畅
     */
    @Column(name = "insd_plugup")
    private String insdPlugup;

    /**
     * 浮渣
     */
    @Column(name = "insd_scum")
    private String insdScum;

    /**
     * 其他
     */
    @Column(name = "insd_oth")
    private String insdOth;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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

    /**
     * 检查日期
     */
    @Column(name = "detect_time", nullable = false)
    private Date detectTime;

    /**
     * 监测人员
     */
    @Column(name = "detect_user", nullable = false)
    private String detectUser;

    /**
     * 记录人
     */
    @Column(name = "record_user", nullable = false)
    private String recordUser;

    /**
     * 核较人员
     */
    @Column(name = "verify_user", nullable = false)
    private String verifyUser;


    /**
     * 是否删除
     */
    @Column(name = "deleted", nullable = false)
    private Long deleted;

    /**
     * 工程信息id
     */
    @Column(name = "eng_id")
    private Integer engId;




    /**
     * 井盖形状
     */
    @Column(name = "mhe_shape")
    private ShapeTypeEnum mheShape;
    
}