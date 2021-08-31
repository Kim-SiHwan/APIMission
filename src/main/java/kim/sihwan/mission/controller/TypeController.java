package kim.sihwan.mission.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "2. Type")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/type")
public class TypeController {

    @ApiOperation(value = "조회 가능한 상품 유형 목록 조회", notes = "Enum으로 등록된 조회 가능한 상품의 목록을 조회한다 (현재 과일, 채소 )")
    @GetMapping(produces = {"application/json; charset=UTF-8"})
    public List<TypeResponseDto> requestAllProductType() {
        return Arrays.stream(ProductType.values())
                .map(p -> TypeResponseDto.toDto(p.toString().toLowerCase(), p.getType()))
                .collect(Collectors.toList());
    }
}
