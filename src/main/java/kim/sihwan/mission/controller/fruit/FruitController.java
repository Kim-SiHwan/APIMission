package kim.sihwan.mission.controller.fruit;

import kim.sihwan.mission.service.fruit.FruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fruit")
public class FruitController {

    private final FruitService fruitService;

    @GetMapping
    public ResponseEntity<String> requestFruitToken(){
        return new ResponseEntity<>(fruitService.requestFruitToken(), HttpStatus.OK);
    }

}
