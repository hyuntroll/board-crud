package pong.ios.boardcrud.global.auth.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain_1.entity.like.Like;
import pong.ios.boardcrud.domain_1.entity.like.LikeId;
import pong.ios.boardcrud.domain.post.domain.Post;
import pong.ios.boardcrud.domain.user.domain.UserEntity;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    boolean existsByLikerAndPost(UserEntity liker, Post post);

    @Transactional
    void deleteByLikerAndPost(UserEntity liker, Post post);

    int countByPost(Post post);


}
