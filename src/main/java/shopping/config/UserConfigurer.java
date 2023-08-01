package shopping.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import shopping.dto.UserJoinRequest;
import shopping.service.AuthService;

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
