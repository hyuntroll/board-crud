package pong.ios.boardcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pong.ios.boardcrud.global.redis.RedisService;


@SpringBootTest(classes = {RedisService.class})
class RedisServiceTest {

    @Autowired
    private RedisService redisService;



}