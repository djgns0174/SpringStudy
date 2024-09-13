package hello.login.web.login;

import hello.login.domain.member.Member;
import hello.login.web.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Optional<Member> byLoginId = memberRepository.findByLoginId(loginId);
        return byLoginId.filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
