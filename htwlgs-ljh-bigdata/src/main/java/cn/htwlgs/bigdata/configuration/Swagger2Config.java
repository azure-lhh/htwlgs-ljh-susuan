package cn.htwlgs.bigdata.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Swagger2Config
 * @Description Swagger配置
 * @Author lihouhai
 * @Date 2020/5/11 14:34
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${server.port:}")
    private String SWAGGER_PORT;

    @Value("${swagger.enabled:}")
    private boolean SWAGGER_ENABLE ;



    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(SWAGGER_ENABLE)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.htwlgs.bigdata.controller"))
                // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("流域大数据分析与辅助决策系统接口文档")
                .description("本文档描述了接口定义规范")
                .version("1.0")
                .contact(new Contact("环投网络公司", "http://htwlgs.cn", "待补充"))
                .build();
    }
}
