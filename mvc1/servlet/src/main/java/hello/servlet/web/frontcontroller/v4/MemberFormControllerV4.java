package hello.servlet.web.frontcontroller.v4;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4{
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
