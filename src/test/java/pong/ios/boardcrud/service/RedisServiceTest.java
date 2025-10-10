package pong.ios.boardcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {RedisService.class})
class RedisServiceTest {

    @Autowired
    private RedisService redisService;



}