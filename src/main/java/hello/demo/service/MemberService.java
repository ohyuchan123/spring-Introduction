package hello.demo.service;

import hello.demo.domain.Member;
import hello.demo.repository.MemberRepository;
import hello.demo.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
@Transactional //jpa를 사용하면 필수로 있어야 한다.
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //회원 가입
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //같은 이름이 있는 중복 회원X
        //getName에서 null이 아니면 아래의 값이 동작
        memberRepository.findByName(member.getName())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
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
