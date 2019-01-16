package biod.rabbitmq.consumer.mailconsumer.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ConfirmUtil {
    private String url = null;
    private MultiValueMap<String, String> value = null;

    public ConfirmUtil(String url, MultiValueMap<String, String> value){
        this.url = url;
        this.value = value;
    }

    public void confirmRequest(){
        if (url != null && value != null){
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(value, headers);
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
