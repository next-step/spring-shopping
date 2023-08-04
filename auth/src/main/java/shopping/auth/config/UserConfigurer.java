package shopping.auth.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import shopping.auth.dto.UserJoinRequest;
import shopping.auth.service.AuthService;

@Component
final class UserConfigurer {

    private static final UserJoinRequest ADMIN = new UserJoinRequest("admin@hello.world", "admin!123");

    private final AuthService authService;

    UserConfigurer(final AuthService authService) {
        this.authService = authService;
    }

    @EventListener(ApplicationStartedEvent.class)
    void setDefaultUsers() {
        authService.joinUser(ADMIN);
    }

}
