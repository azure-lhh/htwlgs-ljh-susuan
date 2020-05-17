package cn.htwlgs.bigdata.dtos;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @ClassName DataDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/11 14:37
 * @Version 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@Builder(toBuilder=true)
public class DataDto {
    @ApiModelProperty(value = "采集时间",example = "2019-02-23 02:23:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordTime;

    @ApiModelProperty(value = "0湖库1其他")
    @NotNull(message ="字段总磷(以P计)用户区分湖库与其他，字段不能为空")
    @Min(value = 0,message = "默认值未0")
    private Integer  type;

    @ApiModelProperty(value = "编号")
    @NotEmpty(message ="编号字段不能为空")
    private  String serialNo;

    @ApiModelProperty(value = "水系")
    private String basin;

    @ApiModelProperty(value = "水温")
    @JsonProperty(value = "W_temp")
    @NotNull(message ="水温值不能为空")
    private Double wTemp;

    @ApiModelProperty(value = "PH值")
    @JsonProperty(value = "ph")
    @NotNull(message ="PH值不能为空")
    private Double ph;

    @ApiModelProperty(value = "高锰酸钾指数")
    @JsonProperty(value = "CODMn")
    private Double cODMn;

    @ApiModelProperty(value = "溶解氧")
    @JsonProperty(value = "DO")
    private Double dO;

    @ApiModelProperty(value = "化学需氧量(CODCr)")
    @JsonProperty(value = "CODCr")
    private Double cODCr;

    @ApiModelProperty(value = "五日生化需氧量(BOD5)")
    @JsonProperty(value ="BOD5")
    private Double bOD5;

    @ApiModelProperty(value = "氨氮NH3N")
    @JsonProperty(value = "NH4N")
    private Double nH4N;

    @ApiModelProperty(value = "总磷(以P计)")
    @JsonProperty(value = "P_total")
    private Double pTotal;

    @ApiModelProperty(value = "总氮(湖库以N计)")
    @JsonProperty(value = "N_total")
    private Double nTotal;

    @ApiModelProperty(value = "铜")
    @JsonProperty(value = "W_Cu")
    private Double wCu;

    @ApiModelProperty(value = "锌")
    @JsonProperty(value = "W_Zn")
    private Double wZn;

    @ApiModelProperty(value = "氟化物(以F-计)")
    private Double f;

    @ApiModelProperty(value = "硒")
    private Double se;

    @ApiModelProperty(value = "砷")
    private Double as;

    @ApiModelProperty(value = "汞")
    @JsonProperty(value = "W_Hg")
    private Double wHg;

    @ApiModelProperty(value = "镉")
    private Double cd;

    @ApiModelProperty(value = "六价铬")
    private Double cr6;


    @ApiModelProperty(value = "铅")
    @JsonProperty(value = "W_Pb")
    private Double wPb;

    @ApiModelProperty(value = "氰化物")
    @JsonProperty(value = "Cn_total")
    private Double cnTotal;

    @ApiModelProperty(value = "挥发酚")
    @JsonProperty(value = "V_phen")
    private Double wPhen;

    @ApiModelProperty(value = "石油类")
    private Double oils;

    @ApiModelProperty(value = "阴离子表面活性剂")
    @JsonProperty(value = "An_SAA")
    private Double anSAA;

    @ApiModelProperty(value = "硫化物")
    private Double s;

    @ApiModelProperty(value = "粪大肠菌群(个/L)")
    @JsonProperty(value = "Colo_org")
    private Double coloOrg;


    public DataDto() {
    }

    public Double getCODMn() {
        return this.cODMn =  cODMn == null ? 0.0 : cODMn;
    }

    public Double getDO() {
        return this.dO =  dO == null ? 0.0 : dO;
    }

    public Double getCODCr() {
        return this.cODCr =  cODCr == null ? 0.0 : cODCr;
    }

    public Double getBOD5() {
        return this.bOD5 =  bOD5 == null ? 0.0 : bOD5;
    }

    public Double getNH4N() {
        return this.nH4N =  nH4N == null ? 0.0 : nH4N;
    }


    public Double getPTotal() {
        return this.pTotal =  pTotal == null ? 0.0 : pTotal;
    }

    public Double getNTotal() {
        return this.nTotal =  nTotal == null ? 0.0 : nTotal;
    }

    public Double getWCu() {
        return this.wCu =  wCu == null ? 0.0 : wCu;
    }

    public Double getWZn() {
        return this.wZn =  wZn == null ? 0.0 : wZn;
    }

    public Double getF() {
        return this.f =  f == null ? 0.0 : f;
    }

    public Double getSe() {
        return this.se =  se == null ? 0.0 : se;
    }

    public Double getAs() { return this.as =  as == null ? 0.0 : as; }

    public Double getWHg() {
        return this.wHg =  wHg == null ? 0.0 : wHg;
    }

    public Double getCd() {
        return this.cd =  cd == null ? 0.0 : cd;
    }

    public Double getCr6() {
        return this.cr6 =  cr6 == null ? 0.0 : cr6;
    }

    public Double getWPb() {
        return this.wPb =  wPb == null ? 0.0 : wPb;
    }

    public Double getCnTotal() {
        return this.cnTotal =  cnTotal == null ? 0.0 : cnTotal;
    }

    public Double getWPhen() {
        return this.wPhen =  wPhen == null ? 0.0 : wPhen;
    }

    public Double getOils() {
        return this.oils =  oils == null ? 0.0 : oils;
    }

    public Double getAnSAA() {
        return this.anSAA =  anSAA == null ? 0.0 : anSAA;
    }

    public Double getS() {
        return this.s =  s == null ? 0.0 : s;
    }

    public Double getColoOrg() {
        return this.coloOrg =  coloOrg == null ? 0.0 : coloOrg;
    }

    public DataDto(Integer  type, String serialNo, Double bOD5, Double nH4N, Double cODMn, Double dO, Double cODCr) {
        this.type = type;
        this.serialNo =serialNo;
        this.bOD5 = bOD5;
        this.nH4N = nH4N;
        this.cODMn = cODMn;
        this.dO = dO;
        this.cODCr = cODCr;
    }

    public DataDto(Integer type, String serialNo, Double cODMn, Double dO, Double cODCr, Double bOD5, Double nH4N, Double pTotal, Double nTotal, Double wCu, Double wZn, Double f, Double se, Double as, Double wHg, Double cd, Double cr6, Double wPb, Double cnTotal, Double wPhen, Double oils, Double anSAA, Double s, Double coloOrg) {
        this.type = type;
        this.serialNo = serialNo;
        this.cODMn = cODMn;
        this.dO = dO;
        this.cODCr = cODCr;
        this.bOD5 = bOD5;
        this.nH4N = nH4N;
        this.pTotal = pTotal;
        this.nTotal = nTotal;
        this.wCu = wCu;
        this.wZn = wZn;
        this.f = f;
        this.se = se;
        this.as = as;
        this.wHg = wHg;
        this.cd = cd;
        this.cr6 = cr6;
        this.wPb = wPb;
        this.cnTotal = cnTotal;
        this.wPhen = wPhen;
        this.oils = oils;
        this.anSAA = anSAA;
        this.s = s;
        this.coloOrg = coloOrg;
    }
}
