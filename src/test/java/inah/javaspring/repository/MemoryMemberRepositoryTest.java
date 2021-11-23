package inah.javaspring.repository;

import inah.javaspring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 하나의 테스트가 끝날 때 마다 저장소를 클리어 해주어야 한다.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        Member result = repository.findByName("spring1").get();

        //then
        assertThat(member1).isEqualTo(result);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("Spring");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void update() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Optional<Member> member = repository.findById(1L);

        member.ifPresent(m -> {
            m.setName("inah");
            repository.save(m);
        });
    }

    @Test
    public void deleteMember(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Optional<Member> member = repository.findById(1L);
        // 삭제를 원하는 회원 id를 조회하여 ifPresent값이 존재할 때 삭제를 진행한다.
        member.ifPresent(m -> {
            m.setId(1L);
            repository.deleteById(member1.getId());
        });
    }
}
