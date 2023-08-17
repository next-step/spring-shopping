package shopping.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class MemberInfo implements SecurityInfo {

    private Long principal;

    @JsonCreator
    public MemberInfo(@JsonProperty final Long principal) {
        this.principal = principal;
    }

    private MemberInfo() {
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
        final MemberInfo memberInfo = (MemberInfo) o;
        return Objects.equals(principal, memberInfo.principal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(principal);
    }
}
