package kim.sihwan.mission.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kim.sihwan.mission.dto.response.VegetableResponseDto;
import kim.sihwan.mission.exception.customException.BlankParameterException;
import kim.sihwan.mission.service.VegetableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "4. Vegetable")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vegetable")
public class VegetableController {

    private final VegetableService vegetableService;

    @ApiOperation(value = "채소 목록 조회", notes = "채소의 목록을 조회한다")
    @GetMapping(produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<List<String>> requestVegetableList() {
        return new ResponseEntity<>(vegetableService.requestVegetableList(), HttpStatus.OK);
    }

    @ApiOperation(value = "채소 상세 조회", notes = "입력한 채소의 상품명과 가격을 조회한다")
    @GetMapping(value = "/detail", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<VegetableResponseDto> requestVegetableInfo(@RequestParam String name) {
        System.out.println("name : " + name);
        if (Strings.isBlank(name))
            throw new BlankParameterException("상품명은 필수값입니다");
        return new ResponseEntity<>(vegetableService.requestVegetableInfo(name), HttpStatus.OK);
    }
}
