package wanted.n.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_USER("ROLE_USER", "USER");

    private final String roleName;
    private final String authority;
}
