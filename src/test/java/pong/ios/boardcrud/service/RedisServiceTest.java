package pong.ios.boardcrud.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pong.ios.boardcrud.domain.entity.refresh.RefreshToken;


@SpringBootTest(classes = {RedisService.class})
class RedisServiceTest {

    @Autowired
    private RedisService redisService;



}