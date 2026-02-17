package pong.ios.boardcrud.global.mapper;

public interface Mapper<D, E> {
    E toEntity(D domain);
    D toDomain(E entity);
}
