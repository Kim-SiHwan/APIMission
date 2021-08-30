package kim.sihwan.mission.util;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CustomDecoder {

    public String decodeApiUrl(String encodedApiUrl){
        return new String(Base64.getDecoder().decode(encodedApiUrl));
    }

}
