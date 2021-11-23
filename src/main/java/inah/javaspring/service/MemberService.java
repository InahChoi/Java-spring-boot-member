package inah.javaspring.service;

import inah.javaspring.domain.Member;
import inah.javaspring.repository.MemberRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * C) 회원가입
     */
    public Long join(Member member) {
        // 아이디 중복 확인
        memberRepository.findByName(member.getName())
                // ifPresent() => 값이 있으면 로직이 동작한다.
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * R) 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * R) 회원 아이디로 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * U) 회원 수정
     */
    public Optional<Member> changeInfo(@RequestParam Long id, @RequestBody Member member) {
        Optional<Member> updateMember = memberRepository.findById(id);

        updateMember.ifPresent(m -> {
            m.setName(member.getName());

            memberRepository.save(m);
        });
        return updateMember;
    }

    /**
     * D) 회원 삭제
     */
    public Member deleteById(Long memberId){
        return memberRepository.deleteById(memberId);
    }

}
