package kim.sihwan.mission.exception.customException;

public class InvalidProductTypeException extends RuntimeException {
    public InvalidProductTypeException(String desc) {
        super(desc);
    }
}
