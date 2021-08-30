package kim.sihwan.mission.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Info {
    private final String name;
    private final String price;

    public static Info toDto(String name, String price){
        return Info.builder()
                .name(name)
                .price(price)
                .build();
    }

}
