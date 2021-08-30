package kim.sihwan.mission.service.fruit;

import kim.sihwan.mission.api.FruitApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FruitService {

    private final FruitApi fruitApi;

    public String requestFruitToken(){
        return fruitApi.requestToken();
    }
}
