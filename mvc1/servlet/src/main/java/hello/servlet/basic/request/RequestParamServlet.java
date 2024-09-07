package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("전체 파라미터 조회 start");
        req.getParameterNames().asIterator().forEachRemaining(name -> {
            log.info(name + "=" + req.getParameter(name));
        });
        log.info("전체 파라미터 조회 end");

        log.info("단일 파라미터 조회");
        log.info("username = "+ req.getParameter("username"));
        log.info("age = "+ req.getParameter("age"));

        resp.getWriter().write("ok");
    }
}
