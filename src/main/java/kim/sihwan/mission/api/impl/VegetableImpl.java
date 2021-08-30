package kim.sihwan.mission.api.impl;

import kim.sihwan.mission.api.VegetableApi;
import kim.sihwan.mission.common.UrlType;
import kim.sihwan.mission.util.CustomDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class VegetableImpl implements VegetableApi {

    private final CustomDecoder decoder;
    private final RestTemplate restTemplate;

    @Override
    public String requestVegetableToken() {
        final String encodedApiUrl = UrlType.VEGETABLE_TOKEN.getEncodedUrl();
        return sendRequest(encodedApiUrl);
    }

    @Override
    public List<String> requestVegetableList() {
        String encodedApiUrl = UrlType.VEGETABLE_LIST.getEncodedUrl();
        return sendRequestVegetableList(encodedApiUrl);
    }

    private List<String> sendRequestVegetableList(final String encodedApiUrl){
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);
        log.info("채소 목록 요청 URL -> {}",decodedApiUrl);

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<List<String>>() {});
        log.info("채소 목록 응답 데이터 -> {}",responseEntity.getBody());

        checkErrorStatus(responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    private HttpEntity<String> makeHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization",requestVegetableToken());

        log.info("채소가게 요청 생성 헤더 {}",httpHeaders);
        return new HttpEntity<>(httpHeaders);
    }

    private String sendRequest(String encodedApiUrl){
        final String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(decodedApiUrl,String.class);

        checkErrorStatus(responseEntity.getStatusCode());
        log.info("채소가게 요청 URL -> {}",decodedApiUrl);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String cookieInfo = responseHeaders.getFirst(HttpHeaders.SET_COOKIE);
        log.info("채소가게 응답 데이터 -> {}",cookieInfo);

        Pattern pattern = Pattern.compile("(?<=Authorization=)(.*?)(?=;)");
        Matcher matcher = pattern.matcher(cookieInfo);
        String accessToken="";

        while(matcher.find()){
            accessToken=matcher.group();
        }

        return accessToken;
    }

    private void checkErrorStatus(HttpStatus status){
        log.info("채소가게 응답 상태 -> {}",status);
        if(status.isError()){
            throw new IllegalStateException("채소가게 토큰 요청 에러 발생");
        }
    }

}
