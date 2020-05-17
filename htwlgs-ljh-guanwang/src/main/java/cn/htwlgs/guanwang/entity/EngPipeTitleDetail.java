package cn.htwlgs.guanwang.entity;

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

@Entity
@Table(name = "gw_eng_pipe_title_detail")
@Data
@EntityListeners(AuditingEntityListener.class)
public class EngPipeTitleDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 距离
     */
    @Column(name = "dist")
    private BigDecimal dist;

    /**
     * 缺陷名称代码
     */
    @Column(name = "nmcd")
    private String nmcd;

    /**
     * 分值
     */
    @Column(name = "score")
    private BigDecimal score;

    /**
     * 等级
     */
    @Column(name = "plevel", nullable = false)
    private Long plevel;

    /**
     * 管道内部描述
     */
    @Column(name = "pdesc")
    private String pdesc;

    /**
     * 照片序号或者说明
     */
    @Column(name = "pic_serial")
    private String picSerial;

    /**
     * 工程id
     */
    @Column(name = "eng_id")
    private Integer engId;


    
}