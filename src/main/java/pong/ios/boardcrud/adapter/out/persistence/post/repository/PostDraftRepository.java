package pong.ios.boardcrud.adapter.out.persistence.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostDraftEntity;

import java.util.Optional;

public interface PostDraftRepository extends JpaRepository<PostDraftEntity, Long> {
    Optional<PostDraftEntity> findById(Long id);

    Page<PostDraftEntity> findAllByUser_Id(Long userId, Pageable pageable);

    Page<PostDraftEntity> findAllByUser_IdAndBoard_Id(Long userId, Long boardId, Pageable pageable);
}
