package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;

@SpringBootTest
class SessionManagerTest {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    SessionManager sessionManager = new SessionManager();

    @Test
    void test(){
        Member member = new Member();
        sessionManager.createSession(member, response);

        request.setCookies(response.getCookies());

        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member);

        sessionManager.expire(request);
        Object session = sessionManager.getSession(request);
        Assertions.assertThat(session).isNull();
    }
}