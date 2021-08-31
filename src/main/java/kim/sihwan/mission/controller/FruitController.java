package kim.sihwan.mission.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kim.sihwan.mission.dto.response.FruitResponseDto;
import kim.sihwan.mission.exception.customException.BlankParameterException;
import kim.sihwan.mission.service.FruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "3. Fruit")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fruit")
public class FruitController {

    private final FruitService fruitService;

    @ApiOperation(value = "과일 목록 조회", notes = "과일의 목록을 조회한다")
    @GetMapping(produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<List<String>> requestFruitList() {
        return new ResponseEntity<>(fruitService.requestFruitList(), HttpStatus.OK);
    }

    @ApiOperation(value = "과일 상세 조회", notes = "입력한 과일의 상품명과 가격을 조회한다")
    @GetMapping(value = "/detail", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<FruitResponseDto> requestFruitInfo(@RequestParam String name) {
        if(Strings.isBlank(name))
            throw new BlankParameterException("상품명은 필수값입니다");
        return new ResponseEntity<>(fruitService.requestFruitInfo(name), HttpStatus.OK);
    }

}
