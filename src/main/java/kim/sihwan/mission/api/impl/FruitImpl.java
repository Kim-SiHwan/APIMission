package kim.sihwan.mission.api.impl;

import kim.sihwan.mission.api.FruitApi;
import kim.sihwan.mission.common.UrlType;
import kim.sihwan.mission.util.CustomDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FruitImpl implements FruitApi {

    private final CustomDecoder decoder;
    private final RestTemplate restTemplate;

    @Override
    public String requestToken() {
        String encodedApiUrl = UrlType.FRUIT_TOKEN.getEncodedUrl();
        return sendRequest(encodedApiUrl);
    }

    @Override
    public List<String> requestFruitList() {
        String encodedApiUrl = UrlType.FRUIT_LIST.getEncodedUrl();
        return sendRequestFruitList(encodedApiUrl);
    }

    private List<String> sendRequestFruitList(final String encodedApiUrl){
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);
        log.info("과일 목록 요청 URL -> {}",decodedApiUrl);

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<List<String>>() {});
        log.info("과일 목록 응답 데이터 -> {}",responseEntity.getBody());

        checkErrorStatus(responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    private HttpEntity<String> makeHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization",requestToken());

        log.info("과일가게 요청 생성 헤더 {}",httpHeaders);
        return new HttpEntity<>(httpHeaders);
    }

    private String sendRequest(final String encodedApiUrl){
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);

        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<Map<String,String>>() {});
        log.info("과일가게 요청 URL -> {}",decodedApiUrl);

        checkErrorStatus(responseEntity.getStatusCode());

        log.info("과일가게 응답 데이터 {}",responseEntity.getBody());
        Map<String,String> map = responseEntity.getBody();

        return map.get("accessToken");
    }

    private void checkErrorStatus(HttpStatus status){
        log.info("과일가게 응답 상태 -> {}",status);
        if(status.isError()){
            throw new IllegalStateException("과일가게 토큰 요청 에러 발생");
        }
    }
}
