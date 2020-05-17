package cn.htwlgs.bigdata.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName SerialNoDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/14 14:29
 * @Version 1.0
 */
@Data
public class SerialNoMonitorDto extends MonitorDto{

    private String serialNo;


    public SerialNoMonitorDto() {
    }

    public SerialNoMonitorDto( ResultDto dto, String serialNo) {
        super(dto.getKeyPollutant(), dto.getLevel(), dto.getNum());
        this.serialNo = serialNo;
    }

    public SerialNoMonitorDto( MonitorDto dto, String serialNo) {
        super(dto.getName(), dto.getLevel(), dto.getNum());
        this.serialNo = serialNo;
    }


    public SerialNoMonitorDto(int index, String name, Integer level, Double num, Double sprstd, boolean flag, Integer priority, String serialNo) {
        super(index, name, level, num, sprstd, flag, priority);
        this.serialNo = serialNo;
    }
}
