package inah.javaspring.service;

import inah.javaspring.domain.Member;
import inah.javaspring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional
                /**
                  * 테스트 케이스에 Transactional이 있을 경우 테스트 시작 전에 트랜잭션을 시작하고
                  * 테스트 완료 후에 항상 롤백한다. 이 이노테이션 사용 시 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
                */

public class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given 뭔가 주어졌을 때
        Member member = new Member();
        member.setName("Hello");

        //when 검증
        Long saveId = memberService.join(member);

        //then 검증 되었을 때 실행 될 로직
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복회원_예외처리() {
        // 같은 name 값의 객체를 생성하여 비교 후 중복을 예외 처리

        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
