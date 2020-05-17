package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @ClassName EngManholeTitleDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/6 11:40
 * @Version 1.0
 */
@Data
public class EngManholeTitleDto  extends  IdDto {


    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "年代")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date setupTime;
    
    @ApiModelProperty(value = "材质")
    private String material;

    @ApiModelProperty(value = "性质")
    private String nature;
    
    @ApiModelProperty(value = "监测单位")
    private String detectUnit;

    @ApiModelProperty(value = "检查井编号")
    private String code;
    
    @ApiModelProperty(value = "井盖埋没")
    private String bury;

    @ApiModelProperty(value = "井盖丢失")
    private String lost;
    
    @ApiModelProperty("井盖破损")
    private String wWorn;
    
    @ApiModelProperty("井框破损")
    private String fWorn;
    
    @ApiModelProperty(value = "盖框缝隙")
    private String wfCrack;
    
    @ApiModelProperty(value = "盖框高差")
    private String wfFf;

    @ApiModelProperty(value = "盖框突出或凹陷")
    private String wfSunken;

    @ApiModelProperty(value = "跳动或声响")
    private String noise;
    
    @ApiModelProperty(value = "周边路面破损或沉降")
    private String highway;

    @ApiModelProperty(value = "井盖标识错误")
    private String flagErr;

    @ApiModelProperty(value = "是否为重型井盖")
    private String heavy;

    @ApiModelProperty(value = "其他")
    private String oth;

    @ApiModelProperty(value = "链条或锁具")
    private String insdChain;

    @ApiModelProperty(value = "爬梯松动锈蚀或缺损")
    private String insdLadder;

    @ApiModelProperty(value = "井壁泥垢")
    private String insdDirt;

    @ApiModelProperty(value = "井壁裂缝")
    private String insdCrack;

    @ApiModelProperty(value = "井壁渗漏")
    private String insdLeaks;

    @ApiModelProperty(value = "抹面脱落")
    private String insdOff;

    @ApiModelProperty(value = "管口孔洞")
    private String insdHole;
    
    @ApiModelProperty(value = "流槽破损")
    private String insdLaunder;

    @ApiModelProperty(value = "井底积泥、杂物")
    private String insdMud;

    @ApiModelProperty(value = "水流不畅")
    private String insdPlugup;
    
    @ApiModelProperty(value = "浮渣")
    private String insdScum;

    @ApiModelProperty(value = "其他")
    private String insdOth;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "检查日期",example = "2019-02-23 02:23:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date detectTime;
    /**
     * 监测人员
     */
    @ApiModelProperty(value = "监测人员")
    private String detectUser;

    @ApiModelProperty(value = "记录人")
    private String recordUser;

    @ApiModelProperty(value = "核较人员")
    private String verifyUser;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "工程信息id")
    @NotNull(message = "工程信息id不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private Integer engId;

    @ApiModelProperty(value = "井盖形状",example = "圆形")
    @ExcelProperty(value = "井盖形状")
    private String mheShape;
}
