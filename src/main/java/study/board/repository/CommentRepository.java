package study.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.board.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //본문id에 소속된 모든 댓글들을 조회
    @EntityGraph(attributePaths = {"posts","member"})
    @Query("select c from Comment c where c.posts.id = :id order by c.groups, c.orders")
    List<Comment> findAlignedCommentByPostId(@Param("id") Long postId);

    //가장 큰 order 가져오기
    @EntityGraph(attributePaths = {"posts","member"})
    @Query("select max(c.orders) from Comment c where c.posts.id = :id and c.groups = :groups")
    Long findMaxCommentOrder(@Param("id") Long postId, @Param("groups") Long groups);

}
