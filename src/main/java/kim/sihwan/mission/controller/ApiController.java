package kim.sihwan.mission.controller;


import kim.sihwan.mission.dto.Info;
import kim.sihwan.mission.service.RootService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ApiController {

    private final RootService rootService;

    @GetMapping
    public ResponseEntity<List<String>> requestProductList(@RequestParam @NotBlank(message = "타입은 필수값입니다.") String type ){
        return new ResponseEntity<>(rootService.requestProductList(type), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Info> requestProductInfo(@RequestParam @NotBlank(message = "타입은 필수값입니다.") String type,
                                                   @RequestParam @NotBlank(message = "상품명은 필수값입니다.") String name){
        return new ResponseEntity<>(rootService.requestProductInfo(type,name), HttpStatus.OK);
    }
}
