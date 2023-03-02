
package study.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.board.entity.Comment;
import study.board.entity.Member;
import study.board.entity.Posts;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Override
    @EntityGraph(attributePaths = {"member"})
    List<Posts> findAll();

    //@Query("select m from Member m where m.username = :username and m.age = :age")
    //@Query("select p from Posts p where p.member = :member")
    List<Posts> findPostByMember(Member member);
    //List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @EntityGraph(attributePaths = {"member","mainText"})
    @Query("select p from Posts p where p.id = :id")
    Optional<Posts> findMainTextById(@Param("id") Long id);

}