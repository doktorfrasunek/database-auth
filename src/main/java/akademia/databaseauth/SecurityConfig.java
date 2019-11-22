package akademia.databaseauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// można użyć adnotacji preauthorize
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private CustumUserService custumUserService;


    public SecurityConfig(PasswordEncoder passwordEncoder, CustumUserService custumUserService) {
        this.passwordEncoder = passwordEncoder;
        this.custumUserService = custumUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//wlaczamy filtr autoryzacji
                .antMatchers("/login**", "/register**","/").permitAll()// wylaczamy z auth
                .and()
                .formLogin()//wlaczamy obsluge logowania form
                .loginPage("/login")// url do logowania
                //.loginProcessingUrl("/sign")// url dla formularza wewnatze strony html
                .usernameParameter("username")// nazwa inputa w html
                .passwordParameter("password")
                .successForwardUrl("/")// nie brany pod uwage gdy jest successHandler
                //.successHandler((req, res, auth) -> {
                //    for (GrantedAuthority g : auth.getAuthorities()) {
               //         System.out.println(g.getAuthority());
                //    }

                //    res.sendRedirect("/");
               // })
                .failureForwardUrl("/login?error=zaloguj sie").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    req.getSession().setAttribute("message", "wylogowałeś");
                    res.sendRedirect("/login");
                }).permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(custumUserService)
                .passwordEncoder(passwordEncoder);
    }
}
