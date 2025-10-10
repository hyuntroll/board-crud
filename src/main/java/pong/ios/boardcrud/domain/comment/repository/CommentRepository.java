package pong.ios.boardcrud.global.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain_1.entity.comment.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
