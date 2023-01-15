package hello.demo.service;

import hello.demo.domain.Member;
import hello.demo.repository.MemberRepository;
import hello.demo.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //회원 가입
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //같은 이름이 있는 중복 회원X
        //getName에서 null이 아니면 아래의 값이 동작
        memberRepository.findByName(member.getName()).ifPresent(m->{
            try {
                throw new IllegalAccessException("이미 존재하는 회원입니다.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });/*ifPresent : 만약 값이 있으면*/
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}