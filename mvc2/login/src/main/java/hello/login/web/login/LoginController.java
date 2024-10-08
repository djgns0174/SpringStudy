package hello.login.web.login;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "/login/loginForm";
    }

//    @PostMapping("/login")
    public String loginSubmitV1(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "/login/loginForm";
        }

        String loginId = loginForm.getLoginId();
        String password = loginForm.getPassword();
        Member loginMember = loginService.login(loginId, password);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/login/loginForm";
        }

        Cookie cookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(cookie);
        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginSubmitV2(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "/login/loginForm";
        }

        String loginId = loginForm.getLoginId();
        String password = loginForm.getPassword();
        Member loginMember = loginService.login(loginId, password);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/login/loginForm";
        }

        sessionManager.createSession(loginMember, response);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginSubmitV3(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                                BindingResult bindingResult,
                                HttpServletRequest request,
                                @RequestParam String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "/login/loginForm";
        }

        String loginId = loginForm.getLoginId();
        String password = loginForm.getPassword();
        Member loginMember = loginService.login(loginId, password);
        log.info("loginMember: {}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:"+redirectURL;
    }

//    @GetMapping("/logout")
    public String logoutV1(HttpServletResponse response, String cookieName) {
        expireCookie(response, cookieName);
        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
