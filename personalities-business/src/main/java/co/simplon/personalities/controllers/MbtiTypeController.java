package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.MbtiTypeView;
import co.simplon.personalities.services.MbtiTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mbti")
public class MbtiTypeController {

    private final MbtiTypeService mbtiTypeService;

    public MbtiTypeController(MbtiTypeService mbtiTypeService) {
        this.mbtiTypeService = mbtiTypeService;
    }

    @GetMapping("/{code}")
    public MbtiTypeView getMbtiType(@PathVariable("code") String code) {
        return mbtiTypeService.getMbtiType(code);
    }

}
