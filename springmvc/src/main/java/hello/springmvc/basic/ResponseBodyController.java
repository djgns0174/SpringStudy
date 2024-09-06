package hello.springmvc.basic;

import hello.springmvc.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller

public class ResponseBodyController {

    @RequestMapping("/response-body-string-v1")
    public void responseBodyStringV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @RequestMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyStringV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping("/response-body-string-v3")
    public String responseBodyStringV3() {
        return "ok";
    }

    @RequestMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1()   {
        HelloData data = new HelloData("asdf", 123);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData data = new HelloData("asdf", 12345);
        return data;
    }
}
