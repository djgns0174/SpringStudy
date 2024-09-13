package hello.login.web.member;

import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addMember(Model model) {
        model.addAttribute("member", new Member());
        return "/members/addMemberForm";
    }

    @PostMapping("/add")
    public String saveMember(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "/members/addMemberForm";
        }

        memberRepository.save(member);
        return "redirect:/login";
    }
}
