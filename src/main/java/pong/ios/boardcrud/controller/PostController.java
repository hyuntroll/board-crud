package pong.ios.boardcrud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/post")
@RestController
public class PostController {

    @GetMapping
    public String index() {
        return "<h1>hello, world!!</h1>";
    }
}
