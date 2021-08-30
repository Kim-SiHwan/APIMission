package kim.sihwan.mission.controller.vegetable;

import kim.sihwan.mission.service.vegetable.VegetableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vegetable")
public class VegetableController {

    private final VegetableService vegetableService;

    @GetMapping
    public ResponseEntity<String> requestVegetableToken() {
        return new ResponseEntity<>(vegetableService.requestVegetableToken(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> requestVegetableList() {
        return new ResponseEntity<>(vegetableService.requestVegetableList(), HttpStatus.OK);
    }

}
