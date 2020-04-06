package domain;

import java.util.Objects;

public abstract class IdentifiableEntity<ID> {
    private ID id;

    public IdentifiableEntity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        IdentifiableEntity<?> otherEntity = (IdentifiableEntity<?>) other;

        return getId() == otherEntity.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
