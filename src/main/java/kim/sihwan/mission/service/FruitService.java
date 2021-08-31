package kim.sihwan.mission.service;

import kim.sihwan.mission.api.FruitApi;
import kim.sihwan.mission.dto.response.FruitResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FruitService {

    private final FruitApi fruitApi;

    public List<String> requestFruitList() {
        return fruitApi.requestFruitList();
    }

    public FruitResponseDto requestFruitInfo(String name) {
        return fruitApi.requestFruitInfo(name);
    }

}
