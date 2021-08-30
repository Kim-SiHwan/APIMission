package kim.sihwan.mission.exception.customException;

public class UnknownServerException extends RuntimeException {
    public UnknownServerException(String desc) {
        super(desc);
    }
}