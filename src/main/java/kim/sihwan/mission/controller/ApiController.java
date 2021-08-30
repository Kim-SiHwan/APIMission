package kim.sihwan.mission.controller;


import kim.sihwan.mission.dto.ProductInfo;
import kim.sihwan.mission.dto.ProductInfoRequestDto;
import kim.sihwan.mission.dto.ProductListRequestDto;
import kim.sihwan.mission.service.RootService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ApiController {

    private final RootService rootService;

    @GetMapping
    public ResponseEntity<List<String>> requestProductList(@Valid ProductListRequestDto requestDto){
        return new ResponseEntity<>(rootService.requestProductList(requestDto.getType()), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ProductInfo> requestProductInfo(@Valid ProductInfoRequestDto requestDto){
        return new ResponseEntity<>(rootService.requestProductInfo(requestDto.getType(),requestDto.getName()), HttpStatus.OK);
    }

}
