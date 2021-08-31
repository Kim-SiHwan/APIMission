package kim.sihwan.mission.service;

import kim.sihwan.mission.api.VegetableApi;
import kim.sihwan.mission.dto.response.VegetableResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VegetableService {

    private final VegetableApi vegetableApi;

    public List<String> requestVegetableList() {
        return vegetableApi.requestVegetableList();
    }

    public VegetableResponseDto requestVegetableInfo(String name) {
        return vegetableApi.requestVegetableInfo(name);
    }
}
