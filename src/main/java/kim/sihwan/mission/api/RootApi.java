package kim.sihwan.mission.api;

import kim.sihwan.mission.dto.ProductInfo;

import java.util.List;

public interface RootApi {

    String requestProductToken();

    List<String> requestProductList();

    ProductInfo requestProductInfo(String name);


}
