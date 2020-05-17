package cn.htwlgs.guanwang.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AppConfig {

    @Value("${minio.endpoint:}")
    private String minioEndPoint;

    @Value("${minio.access-key:}")
    private String minioAccessKey;

    @Value("${minio.secret-key:}")
    private String minioSecretKey;





}
