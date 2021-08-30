package kim.sihwan.mission.exception.customException;

public class ApiServerException extends RuntimeException {
    public ApiServerException(String desc) {
        super(desc);
    }
}
