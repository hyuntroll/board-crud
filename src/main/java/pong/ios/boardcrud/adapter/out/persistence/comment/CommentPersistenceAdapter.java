package pong.ios.boardcrud.adapter.out.persistence.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.comment.mapper.CommentMapper;
import pong.ios.boardcrud.adapter.out.persistence.comment.repository.CommentRepository;
import pong.ios.boardcrud.application.port.out.comment.LoadCommentPort;
import pong.ios.boardcrud.application.port.out.comment.SaveCommentPort;
import pong.ios.boardcrud.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements LoadCommentPort, SaveCommentPort {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper::toDomain);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPost_IdOrderByCreatedAtAsc(postId).stream()
                .map(commentMapper::toDomain)
                .toList();
    }

    @Override
    public List<Comment> findAllByParentId(Long parentId) {
        return commentRepository.findAllByParent_IdOrderByCreatedAtAsc(parentId).stream()
                .map(commentMapper::toDomain)
                .toList();
    }

    @Override
    public Comment save(Comment comment) {
        return commentMapper.toDomain(
                commentRepository.save(commentMapper.toEntity(comment))
        );
    }

    @Override
    public void saveAll(List<Comment> comments) {
        commentRepository.saveAll(
                comments.stream().map(commentMapper::toEntity).toList()
        );
    }
}
