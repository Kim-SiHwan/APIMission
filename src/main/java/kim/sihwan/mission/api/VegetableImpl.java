package kim.sihwan.mission.api;

import kim.sihwan.mission.common.UrlType;
import kim.sihwan.mission.dto.Info;
import kim.sihwan.mission.util.CustomDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class VegetableImpl implements RootApi {
    private final CustomDecoder decoder;
    private final RestTemplate restTemplate;

    @Override
    public String requestProductToken() {
        final String encodedApiUrl = UrlType.VEGETABLE_TOKEN.getEncodedUrl();
        return sendRequest(encodedApiUrl);
    }

    @Override
    public List<String> requestProductList() {
        final String encodedApiUrl = UrlType.VEGETABLE_LIST.getEncodedUrl();
        return sendRequestVegetableList(encodedApiUrl);
    }

    @Override
    public Info requestProductInfo(String name) {
        String encodedApiUrl = UrlType.VEGETABLE_INFO.getEncodedUrl();
        return sendRequestVegetableInfo(encodedApiUrl, name);
    }

    private Info sendRequestVegetableInfo(final String encodedApiUrl, final String name){
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl) + name;
        log.info("채소 정보 요청 URL -> {}",decodedApiUrl);

        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<>() {});
        log.info("채소 정보 응답 데이터 -> {}",responseEntity.getBody());

        checkErrorStatus(responseEntity.getStatusCode());
        Map<String,String> map = responseEntity.getBody();
        return Info.toDto(map.get("name"), map.get("price"));
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
        httpHeaders.set("Authorization",requestProductToken());

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
