package akademia.databaseauth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustumUserService implements UserDetailsService {
    private UserRepository userRepository;

    public CustumUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserApp> userApp = userRepository.findUserAppByLogin(login);
        System.out.println(userApp.get().getLogin());
        userApp.orElseThrow(()->
            new UsernameNotFoundException("User by login: " + login + " not found")
        );
        return userApp.map(CustomUserDetails::new).get();
    }
}
