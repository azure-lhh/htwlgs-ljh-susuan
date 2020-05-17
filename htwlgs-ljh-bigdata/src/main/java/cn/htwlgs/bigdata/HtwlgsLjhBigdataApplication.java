package cn.htwlgs.bigdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class HtwlgsLjhBigdataApplication {

    public static void main(String[] args) {
        //spring boot 启动方式，调用核心类springapplication的run方法即可
        ConfigurableApplicationContext applicationContext = SpringApplication.run(HtwlgsLjhBigdataApplication.class, args);
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        String port = applicationContext.getEnvironment().getProperty("server.port");
        String dataUrl = applicationContext.getEnvironment().getProperty("spring.datasource.url");

        log.info("当前启动的环境是： {}", Arrays.toString(profiles));
        log.info("数据库环境：{}", dataUrl);
        log.info("-----------------");
        log.info("系统启动成功，点击：http://127.0.0.1:{}访问", port);
        log.info("系统启动成功，访问点击：http://127.0.0.1:{}/doc.html 访问", port);
    }

}
