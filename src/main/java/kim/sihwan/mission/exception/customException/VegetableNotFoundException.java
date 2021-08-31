package kim.sihwan.mission.exception.customException;

public class VegetableNotFoundException extends RuntimeException {
    public VegetableNotFoundException(String desc) {
        super(desc);
    }
}
