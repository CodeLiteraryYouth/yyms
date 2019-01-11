package com.leanin.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OSS配置文件配置项
 * @author Administrator
 */
@Component
public class OssProperties implements InitializingBean {
    
    @Value("${oss.file.endpoint}")
    private String oss_file_endpoint;

    @Value("${oss.file.keyid}")
    private String oss_file_keyid;

    @Value("${oss.file.keysecret}")
    private String oss_file_keysecret;

    @Value("${oss.file.filehost}")
    private String oss_file_filehost;

    @Value("${oss.file.bucketname1}")
    private String oss_file_bucketname1;


    public static String OSS_END_POINT         ;
    public static String OSS_ACCESS_KEY_ID     ;
    public static String OSS_ACCESS_KEY_SECRET ;
    public static String OSS_BUCKET_NAME1      ;
    public static String OSS_FILE_HOST         ;

    @Override
    public void afterPropertiesSet() throws Exception {
        OSS_END_POINT = oss_file_endpoint;
        OSS_ACCESS_KEY_ID = oss_file_keyid;
        OSS_ACCESS_KEY_SECRET = oss_file_keysecret;
        OSS_FILE_HOST = oss_file_filehost;
        OSS_BUCKET_NAME1 = oss_file_bucketname1;
    }

}
