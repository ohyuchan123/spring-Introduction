package hello.demo.controller;

import hello.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired //연결
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
