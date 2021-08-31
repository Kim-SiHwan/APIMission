package kim.sihwan.mission.exception.customException;

public class BlankParameterException extends RuntimeException{
    public BlankParameterException(String desc){
        super(desc);
    }
}
