package pong.ios.boardcrud.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.domain.entity.refresh.RefreshEntity;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
