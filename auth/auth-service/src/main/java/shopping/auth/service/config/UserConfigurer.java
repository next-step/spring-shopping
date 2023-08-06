package shopping.auth.service.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;;
import shopping.auth.service.AuthService;
import shopping.auth.service.dto.UserJoinRequest;

@Component
final class UserConfigurer {

    private static final UserJoinRequest ADMIN = new UserJoinRequest("admin@hello.world", "admin!123");

    private final AuthService authService;

    public UserConfigurer(final AuthService authService) {
        this.authService = authService;
    }

    @EventListener(ApplicationStartedEvent.class)
    void setDefaultUsers() {
        authService.joinUser(ADMIN);
    }

}
