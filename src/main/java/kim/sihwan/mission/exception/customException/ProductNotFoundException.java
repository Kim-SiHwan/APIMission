package kim.sihwan.mission.exception.customException;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String desc) {
        super(desc);
    }
}
