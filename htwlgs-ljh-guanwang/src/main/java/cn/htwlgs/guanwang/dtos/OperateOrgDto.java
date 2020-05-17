package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @ClassName MangeOrgDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/10 15:46
 * @Version 1.0
 */
@Data
public class OperateOrgDto {
    @ApiModelProperty(value = "序号")
    @NotNull(message = "序号不能为空",groups = UpdateAttr.class)
    @Min(value = 0)
    private Integer id;

    @ApiModelProperty(value =  "巡检人")
    @NotBlank(message = "巡检人不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String detectUser;


    @ApiModelProperty(value =  "巡检备注")
    private String remark;

    @ApiModelProperty(value =  "create_time",example = "2019-02-23 02:23:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value =  "update_time",example = "2019-02-23 02:23:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value =  "创建数据账号")
    private String createUser;

    @ApiModelProperty(value =  "修改数据账号")
    private LocalDateTime updateUser;


    @ApiModelProperty(value =  "运营单位")
    @NotBlank(message = "运营单位不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String unit;

    @ApiModelProperty(value =  "设备巡检周期")
    private String patrolPeriod;
}
