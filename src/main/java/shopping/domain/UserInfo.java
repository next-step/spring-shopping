package shopping.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class UserInfo implements SecurityInfo {

    private Long principal;

    @JsonCreator
    public UserInfo(@JsonProperty final Long principal) {
        this.principal = principal;
    }

    private UserInfo() {
    }

    @Override
    public Long getPrincipal() {
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
        final UserInfo userInfo = (UserInfo) o;
        return Objects.equals(principal, userInfo.principal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(principal);
    }
}
