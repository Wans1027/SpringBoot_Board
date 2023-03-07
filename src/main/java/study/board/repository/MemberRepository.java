package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.board.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //아이디로 검색
    @Override
    Optional<Member> findById(Long id);
    //이름으로 검색
    Optional<Member> findByUsername(String username);
    //모든 유저이름 리스트만 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();

}
