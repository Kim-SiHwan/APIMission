package kim.sihwan.mission.controller;

import kim.sihwan.mission.dto.response.TypeResponseDto;
import kim.sihwan.mission.util.ProductType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/type")
public class TypeController {

    @GetMapping(produces = {"application/json; charset=UTF-8"})
    public List<TypeResponseDto> requestAllProductType(){
       return Arrays.stream(ProductType.values())
                .map(p-> TypeResponseDto.toDto(p.toString().toLowerCase(),p.getType()))
                .collect(Collectors.toList());
    }
}
