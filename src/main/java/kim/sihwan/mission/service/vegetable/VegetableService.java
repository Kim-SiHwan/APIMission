package kim.sihwan.mission.service.vegetable;

import kim.sihwan.mission.api.VegetableApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VegetableService {

    private final VegetableApi vegetableApi;

    public String requestVegetableToken() {
        return vegetableApi.requestVegetableToken();
    }

    public List<String> requestVegetableList() {
        return vegetableApi.requestVegetableList();
    }
}
