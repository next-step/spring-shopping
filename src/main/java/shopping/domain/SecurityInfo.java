package shopping.domain;

import java.util.Objects;

public class SecurityInfo {

    private Object principal;

    public SecurityInfo(final Object principal) {
        this.principal = principal;
    }

    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SecurityInfo that = (SecurityInfo) o;
        return Objects.equals(principal, that.principal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(principal);
    }
}
