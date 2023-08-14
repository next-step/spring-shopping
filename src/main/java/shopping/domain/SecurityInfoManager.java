package shopping.domain;

public interface SecurityInfoManager {

    String encode(SecurityInfo securityInfo);

    SecurityInfo decode(String token);
}
