package study.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //아이디로 검색
    @Transactional(readOnly = true)
    @Override
    Optional<Member> findById(Long id);
    //이름으로 검색
    @Transactional(readOnly = true)
    Optional<Member> findByUsername(String username);
    //모든 유저이름 리스트만 조회
    @Transactional(readOnly = true)
    @Query("select m.username from Member m")
    List<String> findUsernameList();


}
