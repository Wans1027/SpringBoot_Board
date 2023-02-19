package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.board.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //유저 이름으로 멤버 리스트 조회
    List<Member> findByUsername(String username);

    //멤버리스트에 유저이름 리스트만 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();

}
