package cn.htwlgs.guanwang.dtos;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class CustomConverter  implements Converter {

    private   static  DateTimeFormatter ss_fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读的时候会调用
     */
    @Override
    public CellData convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if(cellData.getData() instanceof LocalDateTime){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return new CellData(((LocalDateTime) cellData.getData()).format(fmt));
        }
        if(cellData.getData() instanceof LocalDate){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new CellData(((LocalDateTime) cellData.getData()).format(fmt));
        }
        if(cellData.getData() instanceof LocalTime){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            return new CellData(((LocalDateTime) cellData.getData()).format(fmt));
        }
        return cellData;

//        String string = cellData.toString();
//        if (string.length() != 10){
//            date = ((LocalDateTime) cellData.getData()).format(ss_fmt);
//        }else {
//
//        }

        //log.info("date:{}", date);
//        return date;
    }

    /**
     * 这里是写的时候会调用 不用管
     */
    @Override
    public CellData convertToExcelData(Object o, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String date = null;
        //log.info("o:{}", o);
        if(o instanceof LocalDateTime){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            date = ((LocalDateTime) o).format(fmt);
        }
        if(o instanceof LocalDate){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = ((LocalDateTime) o).format(fmt);
        }
        if(o instanceof LocalTime){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            date = ((LocalDateTime) o).format(fmt);
        }
        //log.info("date:{}", date);
        return new CellData(date);
    }
}
