package kim.sihwan.mission.api;

import kim.sihwan.mission.dto.response.VegetableResponseDto;

import java.util.List;

public interface VegetableApi extends RootApi{

    List<String> requestVegetableList();

    VegetableResponseDto requestVegetableInfo(String name);
}
