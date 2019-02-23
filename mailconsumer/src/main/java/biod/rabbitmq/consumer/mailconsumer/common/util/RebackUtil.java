package biod.rabbitmq.consumer.mailconsumer.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RebackUtil {
    // 回调链接
    private String url = null;
    // 参数 其他类型的Map可能报错
    private MultiValueMap<String, String> value = null;

    public RebackUtil(String url, MultiValueMap<String, String> value){
        this.url = url;
        this.value = value;
    }

    public void confirmRequest(){
        if (url != null && value != null){
            RestTemplate restTemplate = new RestTemplate();

            // 设置Header信息
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 封装http请求
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(value, headers);
            // 发送请求
            ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MultiValueMap<String, String> getValue() {
        return value;
    }

    public void setValue(MultiValueMap<String, String> value) {
        this.value = value;
    }
}
