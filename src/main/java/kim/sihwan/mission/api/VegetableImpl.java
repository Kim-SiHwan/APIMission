package kim.sihwan.mission.api;

import kim.sihwan.mission.common.UrlType;
import kim.sihwan.mission.dto.ProductInfo;
import kim.sihwan.mission.exception.customException.ApiCookieException;
import kim.sihwan.mission.exception.customException.ApiServerException;
import kim.sihwan.mission.exception.customException.ProductNotFoundException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class VegetableImpl implements RootApi {

    private final CustomDecoder decoder;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public List<String> requestProductList() {
        final String encodedApiUrl = UrlType.VEGETABLE_LIST.getEncodedUrl();
        return sendRequestVegetableList(encodedApiUrl);
    }

    @Override
    public ProductInfo requestProductInfo(final String name) {
        String encodedApiUrl = UrlType.VEGETABLE_INFO.getEncodedUrl();
        return sendRequestVegetableInfo(encodedApiUrl, name);
    }

    public String bringVegetableTokenFromRedis() {
        String accessToken = redisTemplate.opsForValue().get("vegetableToken");
        log.info("Redis 채소 토큰 데이터 -> {}", accessToken);

        if (checkTokenIsEmptyOrNull(accessToken))
            return requestVegetableToken();
        return accessToken;
    }

    public String requestVegetableToken() {
        final String encodedApiUrl = UrlType.VEGETABLE_TOKEN.getEncodedUrl();
        String accessToken = sendRequestVegetableToken(encodedApiUrl);

        redisTemplate.opsForValue().set("vegetableToken", accessToken);

        return accessToken;
    }

    private String sendRequestVegetableToken(final String encodedApiUrl) {
        final String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);

        ResponseEntity<String> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<>() {});
            log.info("채소가게 토큰 요청 URL -> {}", decodedApiUrl);
        } catch (Exception e) {
            throw new ApiServerException("API 서버에서 토큰을 정상적으로 받아올 수 없습니다.");
        }
        checkErrorStatus(responseEntity.getStatusCode());

        HttpHeaders responseHeaders = responseEntity.getHeaders();

        String cookieInfo = responseHeaders.getFirst(HttpHeaders.SET_COOKIE);
        log.info("채소가게 토큰 응답 데이터 -> {}", cookieInfo);

        return findTokenFromCookie(cookieInfo);
    }

    private String findTokenFromCookie(final String cookieInfo) {
        Pattern pattern = Pattern.compile("(?<=Authorization=)(.*?)(?=;)");
        Matcher matcher = pattern.matcher(cookieInfo);
        String accessToken = "";

        while (matcher.find()) {
            accessToken = matcher.group();
            log.info("채소 토큰 매치 데이터 -> {}", accessToken);
        }

        if (checkTokenIsEmptyOrNull(accessToken))
            throw new ApiCookieException("쿠키에 존재하는 인증 토큰이 존재하지 않습니다.");

        return accessToken;
    }

    private List<String> sendRequestVegetableList(final String encodedApiUrl) {
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl);
        log.info("채소 목록 요청 URL -> {}", decodedApiUrl);

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<>() {});
        log.info("채소 목록 응답 데이터 -> {}", responseEntity.getBody());

        checkErrorStatus(responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    private ProductInfo sendRequestVegetableInfo(final String encodedApiUrl, final String name) {
        String decodedApiUrl = decoder.decodeApiUrl(encodedApiUrl) + name;
        log.info("채소 정보 요청 URL -> {}", decodedApiUrl);
        ResponseEntity<Map<String, String>> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(decodedApiUrl, HttpMethod.GET, makeHeader(), new ParameterizedTypeReference<>() {});
            log.info("채소 정보 응답 데이터 -> {}", responseEntity.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductNotFoundException("존재하지 않는 상품을 요청했습니다.");
        }

        checkErrorStatus(responseEntity.getStatusCode());
        Map<String, String> map = responseEntity.getBody();
        return ProductInfo.toDto(map.get("name"), map.get("price"));
    }

    private HttpEntity<String> makeHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", bringVegetableTokenFromRedis());

        log.info("채소가게 요청 생성 헤더 {}", httpHeaders);
        return new HttpEntity<>(httpHeaders);
    }

    private void checkErrorStatus(final HttpStatus status) {
        log.info("채소가게 응답 상태 -> {}", status);
        if (status.isError()) {
            throw new UnknownServerException("파악하지 못한 오류가 발생했습니다.");
        }
    }

    private boolean checkTokenIsEmptyOrNull(String accessToken) {
        return Strings.isBlank(accessToken);
    }
}
