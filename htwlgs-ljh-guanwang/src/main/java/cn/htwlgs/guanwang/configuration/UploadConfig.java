//package cn.htwlgs.guanwang.configuration;
//
//import io.minio.MinioClient;
//import io.minio.errors.MinioException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//@Configuration
//public class UploadConfig {
//
//    @Autowired
//    private AppConfig appConfig;
//
//    @Bean
//    public MinioClient minioClient() {
//        try {
////            MinioClient minioClient = new MinioClient(appConfig.getMinioEndPoint(), appConfig.getMinioAccessKey(), appConfig.getMinioSecretKey());
////            boolean isExist = minioClient.bucketExists("files");
////            if (!isExist) {
////                minioClient.makeBucket("files");
////            }
////            return minioClient;
//        } catch (MinioException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
