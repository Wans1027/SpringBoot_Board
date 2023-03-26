package study.board.security;


import lombok.Getter;
import org.springframework.context.annotation.Bean;

@Getter
public class SecretKey {

    private final String key = "showMeTheMoney";


}
