package study.board.repository;

import com.sun.tools.javac.Main;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.MainText;

import java.util.Optional;

public interface MainTextRepository extends JpaRepository<MainText, Long> {
    @Override
    @EntityGraph(attributePaths = {"posts"})
    Optional<MainText> findById(Long aLong);
}
