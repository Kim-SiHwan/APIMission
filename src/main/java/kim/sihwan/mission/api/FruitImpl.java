package kim.sihwan.mission.api;

import kim.sihwan.mission.common.UrlType;
import kim.sihwan.mission.dto.ProductInfo;
import kim.sihwan.mission.util.CustomDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FruitImpl implements RootApi {

    private final CustomDecoder decoder;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String,String> redisTemplate;

    @Override
    public List<String> requestProductList() {
        String encodedApiUrl = UrlType.FRUIT_LIST.getEncodedUrl();
        return sendRequestFruitList(encodedApiUrl);
    }

    @Override
    public ProductInfo requestProductInfo(final String name){
        String encodedApiUrl = UrlType.FRUIT_INFO.getEncodedUrl();
        return sendRequestFruitInfo(encodedApiUrl, name);
    }

    public String bringFruitTokenFromRedis(){
        String accessToken = redisTemplate.opsForValue().get("fruitToken");
        log.info("Redis 과일 토큰 데이터 -> {}",accessToken);

        if(checkTokenIsEmptyOrNull(accessToken))
            return requestFruitToken();
        return accessToken;
    }

    public String requestFruitToken() {
        String encodedApiUrl = UrlType.FRUIT_TOKEN.getEncodedUrl();
        String accessToken = sendRequestFruitToken(encodedApiUrl);

        redisTemplate.opsForValue().set("fruitToken",accessToken);

        return accessToken;
    }

    private ProductInfo sendRequestFruitInfo(final String encodedApiUrl, final String name){
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl) + name;
        log.info("과일 정보 요청 URL -> {}",decodedApiUrl);

        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<>() {});
        log.info("과일 정보 응답 데이터 -> {}",responseEntity.getBody());

        checkErrorStatus(responseEntity.getStatusCode());
        Map<String,String> map = responseEntity.getBody();
        return ProductInfo.toDto(map.get("name"), map.get("price"));
    }

    private List<String> sendRequestFruitList(final String encodedApiUrl){
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);
        log.info("과일 목록 요청 URL -> {}",decodedApiUrl);

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<>() {});
        log.info("과일 목록 응답 데이터 -> {}",responseEntity.getBody());

        checkErrorStatus(responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    private HttpEntity<String> makeHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization",bringFruitTokenFromRedis());

        log.info("과일가게 요청 생성 헤더 {}",httpHeaders);
        return new HttpEntity<>(httpHeaders);
    }

    private String sendRequestFruitToken(final String encodedApiUrl){
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);

        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<>() {});
        log.info("과일가게 토큰 요청 URL -> {}",decodedApiUrl);

        checkErrorStatus(responseEntity.getStatusCode());

        log.info("과일가게 토큰 응답 데이터 {}",responseEntity.getBody());
        Map<String,String> map = responseEntity.getBody();

        return map.get("accessToken");
    }

    private void checkErrorStatus(HttpStatus status){
        log.info("과일가게 응답 상태 -> {}",status);
        if(status.isError()){
            throw new IllegalStateException("과일가게 토큰 요청 에러 발생");
        }
    }

    private boolean checkTokenIsEmptyOrNull(String accessToken){
        return Strings.isBlank(accessToken);
    }

}
