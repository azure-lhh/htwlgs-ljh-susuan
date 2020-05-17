package cn.htwlgs.guanwang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@ComponentScan(basePackages = {"cn.htwlgs.common","cn.htwlgs.guanwang"})
public class HtwlgsLjhGuanwangApplication {



    public static void main(String[] args) {
        //spring boot 启动方式，调用核心类springapplication的run方法即可
        ConfigurableApplicationContext applicationContext = SpringApplication.run(HtwlgsLjhGuanwangApplication.class, args);
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        String port = applicationContext.getEnvironment().getProperty("server.port");
        String dataUrl = applicationContext.getEnvironment().getProperty("spring.datasource.url");

        log.info("当前启动的环境是： {}", Arrays.toString(profiles));
        log.info("数据库环境：{}", dataUrl);
        log.info("-----------------");
        log.info("系统启动成功，点击：http://127.0.0.1:{}访问", port);
        log.info("系统启动成功，访问点击：http://127.0.0.1:{}/doc.html 访问", port);
    }


    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

}
