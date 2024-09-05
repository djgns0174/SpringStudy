package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
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
@WebServlet(name="frontControllerV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerV2(){
        controllerMap.put("/front-controller/v2/members/new-form", new
                MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new
                MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new
                MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();
        ControllerV2 controller = controllerMap.get(requestURI);

        log.info("requestURI: {}", requestURI);
        log.info("controller: {}", controller);

        if (controller == null) {
            log.info("controller == null");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(req, resp);
        log.info("view: {}", view);
        view.render(req, resp);
    }
}
