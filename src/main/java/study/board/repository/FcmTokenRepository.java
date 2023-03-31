package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.FcmToken;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

}
