package kim.sihwan.mission.api;

import kim.sihwan.mission.dto.response.FruitResponseDto;

import java.util.List;

public interface FruitApi extends RootApi{

    List<String> requestFruitList();

    FruitResponseDto requestFruitInfo(String name);
}
