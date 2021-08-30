package kim.sihwan.mission.service.fruit;

import kim.sihwan.mission.api.FruitApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FruitService {

    private final FruitApi fruitApi;

    public String requestFruitToken(){
        return fruitApi.requestToken();
    }

    public List<String> requestFruitList(){
        return fruitApi.requestFruitList();
    }
}
