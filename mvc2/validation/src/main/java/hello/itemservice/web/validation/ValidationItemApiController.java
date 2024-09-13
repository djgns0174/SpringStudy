package hello.itemservice.web.validation;

import hello.itemservice.domain.item.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/validation/api/items")
@RestController
public class ValidationItemApiController {

    @PostMapping("add")
    public Object addItem(@Validated @RequestBody ItemSaveForm form, BindingResult bindingResult){
        log.info("API 컨트롤러 호출");
        if(bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors : {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공로직 호출");
        return form;
    }
}
