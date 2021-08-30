package kim.sihwan.mission.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiConstructor {

    private static final List<String> TYPES = Arrays.asList("과일","채소");
    private final FruitImpl fruitImpl;
    private final VegetableImpl vegetableImpl;

    public RootApi selectApi(String type){
        if(!TYPES.contains(type))
            throw new IllegalStateException("존재하지 않는 유형입니다.");
        if(type.equals("과일"))
            return fruitImpl;
        return vegetableImpl;
    }


}
