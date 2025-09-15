package pong.ios.boardcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
