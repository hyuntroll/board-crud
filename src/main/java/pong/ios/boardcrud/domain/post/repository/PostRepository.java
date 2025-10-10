package pong.ios.boardcrud.domain.post.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

        @Transactional
        Post findPostById(Long id);

}
