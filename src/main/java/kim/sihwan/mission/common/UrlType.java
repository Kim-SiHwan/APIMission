package kim.sihwan.mission.common;

import lombok.Getter;

@Getter
public enum UrlType {

    FRUIT_TOKEN("aHR0cDovL2ZydWl0LmFwaS5wb3N0eXBlLm5ldC90b2tlbg=="),
    FRUIT_LIST("aHR0cDovL2ZydWl0LmFwaS5wb3N0eXBlLm5ldC9wcm9kdWN0"),
    FRUIT_INFO("aHR0cDovL2ZydWl0LmFwaS5wb3N0eXBlLm5ldC9wcm9kdWN0P25hbWU9"),
    VEGETABLE_TOKEN("aHR0cDovL3ZlZ2V0YWJsZS5hcGkucG9zdHlwZS5uZXQvdG9rZW4="),
    VEGETABLE_LIST("aHR0cDovL3ZlZ2V0YWJsZS5hcGkucG9zdHlwZS5uZXQvaXRlbQ=="),
    VEGETABLE_INFO("aHR0cDovL3ZlZ2V0YWJsZS5hcGkucG9zdHlwZS5uZXQvaXRlbT9uYW1lPQ==");

    private final String encodedUrl;

    UrlType(String encodedUrl){
        this.encodedUrl = encodedUrl;
    }

}
