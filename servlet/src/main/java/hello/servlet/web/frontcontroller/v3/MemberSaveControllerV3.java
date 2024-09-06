package hello.servlet.web.frontcontroller.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> model) {

        String username = model.get("username");
        int age = Integer.parseInt(model.get("age"));

        Member member = new Member(username, age);

        memberRepository.save(member);

        ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member", member);

        return modelView;
    }
}
