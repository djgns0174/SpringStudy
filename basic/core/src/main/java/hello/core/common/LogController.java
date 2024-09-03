package hello.core.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;
    private final MyLogger myLogger;

    @ResponseBody
    @RequestMapping("/")
    public String log(HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        myLogger.setRequestURL(requestURI);
        myLogger.log("controller test");
        logService.log("testId");

        return "ok";
    }
}
