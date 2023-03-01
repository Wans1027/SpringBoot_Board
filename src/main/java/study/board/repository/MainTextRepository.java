package study.board.repository;

import com.sun.tools.javac.Main;
import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.MainText;

public interface MainTextRepository extends JpaRepository<MainText, Long> {

}
