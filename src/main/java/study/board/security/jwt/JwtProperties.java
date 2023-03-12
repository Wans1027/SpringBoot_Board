package study.board.security.jwt;


import study.board.security.SecretKey;

public class JwtProperties {


    static int  EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    static String TOKEN_PREFIX = "Bearer ";
    static String HEADER_STRING = "Authorization";
}
