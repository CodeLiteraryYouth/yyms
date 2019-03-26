package com.leanin.testmodel.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.junit.Test;

import java.io.*;

public class OSSAudioUpLoad {

    private String accessKey = "LTAI9hPxQGlo79NT";
    private String accessSecret = "y10wZmbmV0U1RzWjcpPpMQWGcM5dgq";
    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";

    @Test
    public void upload() {
         // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//        String accessKeyId = "<yourAccessKeyId>";
//        String accessKeySecret = "<yourAccessKeySecret>";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKey, accessSecret);

        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        ossClient.putObject("audio-upload", "audio", new File("C:\\Users\\Administrator\\Desktop\\ly\\162951_呼出_018556531536.wav"));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void downLoad() throws IOException {

        /*// 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKey, accessSecret);

        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject("audio-upload", "image1");

        // 读取文件内容。
        System.out.println("Object content:");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        FileInputStream fis =new FileInputStream(new InputStreamReader(ossObject.getObjectContent()))
        FileOutputStream fos =new FileOutputStream("C:\\Users\\Administrator\\Desktop\\ly\\image.img");
        byte[] bytes=new byte[1024];
        int len;
        while((len=reader.read(bytes))!=-1){
            fos.write(bytes,0,len);
        }
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\n" + line);
        }
        // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        reader.close();

        // 关闭OSSClient。
        ossClient.shutdown();*/
    }

}
