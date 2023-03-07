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
    @Query("select c from Comment c where c.posts.id = :id")
    List<Comment> findCommentByPostId(@Param("id") Long postId);
}
