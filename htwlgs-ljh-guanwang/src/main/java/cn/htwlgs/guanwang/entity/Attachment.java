package cn.htwlgs.guanwang.entity;

import cn.htwlgs.guanwang.dtos.enums.AttachmentTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 附件表
 */
@Data
@Entity
@Table(name = "gw_attachment")
@EntityListeners(AuditingEntityListener.class)
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 上传名称
     */
    @Column(name = "fname", nullable = false)
    private String fname;

    /**
     * 上传描述
     */
    @Column(name = "fdescribe")
    private String fdescribe;

    /**
     * 上传文件格式类型
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 上传时间
     */
    @Column(name = "upload_time", nullable = false)
    private String uploadTime;

    /**
     * 存储文件地址
     */
    @Column(name = "upload_url", nullable = false)
    private String uploadUrl;

    /**
     * 是否删除
     */
    @Column(name = "deleted", nullable = false)
    private Long deleted;

    /**
     * 工程id
     */
    @Column(name = "eng_id")
    private Integer engId;

    /**
     * 工程类型 0子工程 1总工程
     */
    @Column(name = "eng_type", nullable = false)
    private AttachmentTypeEnum engType;


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
     * 多文件名称
     */
    @Column(name = "fname_list")
    private String fnameList;

    /**
     * 多文件大小
     */
    @Column(name = "fsize_list")
    private String fsizeList;

    
}