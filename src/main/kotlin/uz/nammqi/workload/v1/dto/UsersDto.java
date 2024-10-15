package uz.nammqi.workload.v1.dto;

import java.util.Objects;

/**
 * DTO for {@link uz.nammqi.workload.v1.entity.Users}
 */
public class UsersDto {
    private final Long id;

    public UsersDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersDto entity = (UsersDto) o;
        return Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ")";
    }
}