package shopping.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import shopping.auth.domain.User;
import shopping.auth.domain.repository.UserRepository;

@Component
final class UserConfigurer {

    private static final User ADMIN = new User("admin@hello.world", "admin!123");

    private final UserRepository userRepository;

    public UserConfigurer(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationStartedEvent.class)
    void setDefaultUsers() {
        userRepository.saveUser(ADMIN);
    }

}
