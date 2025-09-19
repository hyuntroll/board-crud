package pong.ios.boardcrud.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.post.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

        @Transactional
        Post findPostById(Long id);

}
