package hello.exception.api;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/members")
public class ApiExceptionController {

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        MemberDto memberDto = new MemberDto(id, "hello" + id);
        return memberDto;
    }

    @Data
    static class MemberDto {
        private String memberId;
        private String name;

        MemberDto(String memberId, String name) {
            this.memberId = memberId;
            this.name = name;
        }
    }
}
