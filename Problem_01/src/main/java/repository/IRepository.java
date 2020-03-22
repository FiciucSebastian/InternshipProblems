package repository;

import domain.IdentifiableEntity;

public interface IRepository<ID, E extends IdentifiableEntity<ID>> {
    E find(ID id);
    Iterable<E> find();
    E add(E entity);
    E update(E entity);
    E remove(ID id);
    void empty();
}
