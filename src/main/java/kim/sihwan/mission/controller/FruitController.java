package kim.sihwan.mission.controller;


import kim.sihwan.mission.dto.request.ProductInfoRequestDto;
import kim.sihwan.mission.dto.response.FruitResponseDto;
import kim.sihwan.mission.service.FruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fruit")
public class FruitController {

    private final FruitService fruitService;

    @GetMapping
    public ResponseEntity<List<String>> requestFruitList() {
        return new ResponseEntity<>(fruitService.requestFruitList(), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<FruitResponseDto> requestFruitInfo(@Valid ProductInfoRequestDto requestDto) {
        return new ResponseEntity<>(fruitService.requestFruitInfo(requestDto.getName()), HttpStatus.OK);
    }

}
