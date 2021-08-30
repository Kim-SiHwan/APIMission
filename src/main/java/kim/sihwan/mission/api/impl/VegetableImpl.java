package kim.sihwan.mission.api.impl;

import kim.sihwan.mission.api.VegetableApi;
import kim.sihwan.mission.common.UrlType;
import kim.sihwan.mission.util.CustomDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class VegetableImpl implements VegetableApi {

    private final CustomDecoder decoder;
    private final RestTemplate restTemplate;

    @Override
    public String requestToken() {
        final String encodedApiUrl = UrlType.VEGETABLE_TOKEN.getEncodedUrl();
        return sendRequest(encodedApiUrl);
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
