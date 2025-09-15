package pong.ios.boardcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.comment.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
