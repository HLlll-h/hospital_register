import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public class Test {

    public static void main(String[] args) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = ""; //accessKeyId值是LTAI5tSDaQNefdLcYGL5c3hB
        String accessKeySecret = "eB2sSBGjZuna6BG2l1gkMi1kiUXJ5z";
        String bucketName = "hospital-test";

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
