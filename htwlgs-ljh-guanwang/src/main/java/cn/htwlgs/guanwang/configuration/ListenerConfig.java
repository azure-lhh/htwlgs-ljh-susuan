package cn.htwlgs.guanwang.configuration;


import cn.htwlgs.guanwang.dtos.AdminRegionDto;
import cn.htwlgs.guanwang.dtos.TypeNameDto;
import cn.htwlgs.guanwang.repository.SewagePartRepository;
import cn.htwlgs.guanwang.service.SewagePartService;
import cn.htwlgs.guanwang.utils.GetTree;
import cn.htwlgs.guanwang.utils.TreeNode;
import com.alibaba.fastjson.JSON;
import com.google.api.client.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ListenerConfig
 * @Description 初始数据加载
 * @Author lihouhai
 * @Date 2020/4/22 9:55
 * @Version 1.0
 */
@Slf4j
@Configuration
public class ListenerConfig {

    public static List<TypeNameDto<String>>   typeNameList = new ArrayList<>();

    public static List<? extends TreeNode>   adminRegionTree = new ArrayList<>();
    public static List<AdminRegionDto>   adminDtos = new ArrayList<>();
    @Value("${area.url:}")
    private   String area_url ;

    @Autowired
    SewagePartService sewagePartService;

    @Autowired
    RestTemplate  restTemplate;
    @Autowired
    SewagePartRepository sewagePartRepository;


    @Bean
    public void onApplicationEvent() {
        try {

            //获取区域数据
            typeNameList =   sewagePartService.loadDataTypeEnum();

            String response  = restTemplate.getForObject(area_url,String.class);

             adminDtos = JSON.parseArray(response, AdminRegionDto.class);

            adminRegionTree = GetTree.getTree(adminDtos);


            log.info("行政区域树解析完成");
            //500000

            log.info("系统启动加载初始化数据完成");
        } catch (Exception exp) {
            exp.printStackTrace();
            log.error("系统启动加载初始数据失败,原因：{}",exp.getMessage());
        }
    }


}
