package cn.htwlgs.bigdata.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ListenerConfig
 * @Description 初始数据加载
 * @Author lihouhai
 * @Date 2020/5/11 14:34
 * @Version 1.0
 */
@Slf4j
@Configuration
public class ListenerConfig {



    @Bean
    public void onApplicationEvent() {
        try {


            log.info("系统启动加载初始化数据完成");
        } catch (Exception exp) {
            exp.printStackTrace();
            log.error("系统启动加载初始数据失败,原因：{}",exp.getMessage());
        }
    }


}
