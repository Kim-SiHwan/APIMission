package kim.sihwan.mission.api;

import kim.sihwan.mission.dto.Info;

import java.util.List;

public interface RootApi {

    String requestProductToken();

    List<String> requestProductList();

    Info requestProductInfo(String name);


}
