package kim.sihwan.mission.api.impl;

import kim.sihwan.mission.api.FruitApi;
import kim.sihwan.mission.dto.response.FruitResponseDto;
import kim.sihwan.mission.exception.customException.FruitNotFoundException;
import kim.sihwan.mission.util.UrlType;
import kim.sihwan.mission.exception.customException.ApiServerException;
import kim.sihwan.mission.exception.customException.UnknownServerException;
import kim.sihwan.mission.util.CustomDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FruitImpl implements FruitApi {

    private final CustomDecoder decoder;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public List<String> requestFruitList() {
        String encodedApiUrl = UrlType.FRUIT_LIST.getEncodedUrl();
        return sendRequestFruitList(encodedApiUrl);
    }

    @Override
    public FruitResponseDto requestFruitInfo(final String name) {
        String encodedApiUrl = UrlType.FRUIT_INFO.getEncodedUrl();
        return sendRequestFruitInfo(encodedApiUrl, name);
    }

    public String bringFruitTokenFromRedis() {
        String accessToken = redisTemplate.opsForValue().get("fruitToken");
        log.info("Redis 과일 토큰 데이터 -> {}", accessToken);

        if (checkTokenIsEmptyOrNull(accessToken))
            return requestFruitToken();
        return accessToken;
    }

    public String requestFruitToken() {
        String encodedApiUrl = UrlType.FRUIT_TOKEN.getEncodedUrl();
        String accessToken = sendRequestFruitToken(encodedApiUrl);

        redisTemplate.opsForValue().set("fruitToken", accessToken);

        return accessToken;
    }

    private String sendRequestFruitToken(final String encodedApiUrl) {
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);

        ResponseEntity<Map<String, String>> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<>() {});
            log.info("과일가게 토큰 요청 URL -> {}", decodedApiUrl);
        } catch (Exception e) {
            throw new ApiServerException("API 서버에서 토큰을 정상적으로 받아올 수 없습니다.");
        }

        checkErrorStatus(responseEntity.getStatusCode());

        log.info("과일가게 토큰 응답 데이터 {}", responseEntity.getBody());
        Map<String, String> map = responseEntity.getBody();

        return map.get("accessToken");
    }

    private List<String> sendRequestFruitList(final String encodedApiUrl) {
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);
        log.info("과일 목록 요청 URL -> {}", decodedApiUrl);

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<>() {});
        log.info("과일 목록 응답 데이터 -> {}", responseEntity.getBody());

        checkErrorStatus(responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    private FruitResponseDto sendRequestFruitInfo(final String encodedApiUrl, final String name) {
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl) + name;
        log.info("과일 정보 요청 URL -> {}", decodedApiUrl);

        ResponseEntity<Map<String, String>> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<>() {});
            log.info("과일 정보 응답 데이터 -> {}", responseEntity.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            throw new FruitNotFoundException("목록에 존재하지 않는 과일을 요청했습니다.");
        }

        checkErrorStatus(responseEntity.getStatusCode());
        Map<String, String> map = responseEntity.getBody();
        return FruitResponseDto.toDto(map.get("name"), map.get("price"));
    }

    private HttpEntity<String> makeHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", bringFruitTokenFromRedis());

        log.info("과일가게 요청 생성 헤더 {}", httpHeaders);
        return new HttpEntity<>(httpHeaders);
    }

    private void checkErrorStatus(final HttpStatus status) {
        log.info("과일가게 응답 상태 -> {}", status);
        if (status.isError()) {
            throw new UnknownServerException("파악하지 못한 오류가 발생했습니다.");
        }
    }

    private boolean checkTokenIsEmptyOrNull(String accessToken) {
        return Strings.isBlank(accessToken);
    }

}
