package domain;

public interface ISerializer<ID, E extends IdentifiableEntity<ID>, F> {
    F serialize(E entity);
    E deserialize(F value);
}
