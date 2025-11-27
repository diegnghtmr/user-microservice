package com.pragma.powerup.usermicroservice.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String password);
    boolean checkPassword(String rawPassword, String encodedPassword);
}
