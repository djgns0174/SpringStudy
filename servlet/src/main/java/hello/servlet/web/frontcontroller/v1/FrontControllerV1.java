package hello.servlet.web.frontcontroller.v1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet(name="frontControllerV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerV1(){
        controllerMap.put("/front-controller/v1/members/new-form", new
                MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new
                MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new
                MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();
        ControllerV1 controllerV1 = controllerMap.get(requestURI);

        log.info("requestURI: {}", requestURI);
        log.info("controllerV1: {}", controllerV1);

        if (controllerV1 == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controllerV1.process(req, resp);
    }
}