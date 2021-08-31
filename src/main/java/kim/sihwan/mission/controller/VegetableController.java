package kim.sihwan.mission.controller;

import kim.sihwan.mission.dto.request.ProductInfoRequestDto;
import kim.sihwan.mission.dto.response.VegetableResponseDto;
import kim.sihwan.mission.service.VegetableService;
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
@RequestMapping("/api/v1/vegetable")
public class VegetableController {

    private final VegetableService vegetableService;

    @GetMapping(produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<List<String>> requestVegetableList() {
        return new ResponseEntity<>(vegetableService.requestVegetableList(), HttpStatus.OK);
    }

    @GetMapping(value = "/detail", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<VegetableResponseDto> requestVegetableInfo(@Valid ProductInfoRequestDto requestDto) {
        return new ResponseEntity<>(vegetableService.requestVegetableInfo(requestDto.getName()), HttpStatus.OK);
    }
}
