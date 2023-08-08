package shopping.auth;

import javax.servlet.http.HttpServletRequest;

public interface TokenExtractor {

    String extract(final HttpServletRequest request);
}
