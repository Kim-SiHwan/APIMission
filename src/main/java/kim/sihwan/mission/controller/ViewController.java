package kim.sihwan.mission.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "1. View")
@Controller
@RequestMapping
public class ViewController {

    @ApiOperation(value = "브라우저 매핑", notes = "Thymeleaf 브라우저 매핑")
    @GetMapping("/main")
    public String main() {
        return "main";
    }

}


