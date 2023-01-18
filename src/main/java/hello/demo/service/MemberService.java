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
        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }
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
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }

    /*(문제
    * 회원가입, 회원 조회에 시간을 측정하는 기능은 핵심 관심 사항이 아니다.
      시간을 측정하는 로직은 공통 관심 사항이다.
      시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다.
      시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다.
      시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.*/

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
