package kim.sihwan.mission.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfo {
    private final String name;
    private final String price;

    public static ProductInfo toDto(String name, String price){
        return ProductInfo.builder()
                .name(name)
                .price(price)
                .build();
    }

}
