package kim.sihwan.mission.service;


import kim.sihwan.mission.api.ApiConstructor;
import kim.sihwan.mission.api.RootApi;
import kim.sihwan.mission.dto.ProductInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RootService {

    private final ApiConstructor apiConstructor;

    public List<String> requestProductList(final String type){
        RootApi api = apiConstructor.selectApi(type);
        return api.requestProductList();
    }

    public ProductInfo requestProductInfo(final String type,final String name){
        RootApi api = apiConstructor.selectApi(type);
        return api.requestProductInfo(name);
    }

}
