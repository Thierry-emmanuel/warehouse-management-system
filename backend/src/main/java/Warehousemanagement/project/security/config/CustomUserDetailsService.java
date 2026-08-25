package Warehousemanagement.project.security.config;

import Warehousemanagement.project.security.model.User;
import Warehousemanagement.project.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findWithRolesAndPermissionsByUsername(usernameOrEmail)
            .orElseGet(() -> userRepository.findByEmail(usernameOrEmail)
                .flatMap(u -> userRepository.findWithRolesAndPermissionsById(u.getId()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail)));

        return CustomUserDetails.build(user);
    }
}
