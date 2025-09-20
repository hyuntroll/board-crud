package pong.ios.boardcrud.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.refresh.RefreshEntity;


@Repository
public interface RefreshRepository extends CrudRepository<RefreshEntity, Long> {

    boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
