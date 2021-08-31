package kim.sihwan.mission.util;

import lombok.Getter;

@Getter
public enum ProductType {
    FRUIT("과일"),
    VEGETABLE("채소");

    private final String type;

    ProductType(String type){
        this.type = type;
    }

}
