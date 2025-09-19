package pong.ios.boardcrud.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.like.Like;
import pong.ios.boardcrud.domain.entity.like.LikeId;
import pong.ios.boardcrud.domain.entity.post.Post;
import pong.ios.boardcrud.domain.entity.user.UserEntity;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    boolean existsByLikerAndPost(UserEntity liker, Post post);

    @Transactional
    void deleteByLikerAndPost(UserEntity liker, Post post);

    int countByPost(Post post);


}
