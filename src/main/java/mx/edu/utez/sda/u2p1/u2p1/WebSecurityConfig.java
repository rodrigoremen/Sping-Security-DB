package mx.edu.utez.sda.u2p1.u2p1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //configura paths publicos
        http.authorizeHttpRequests(
                //request autorizados
                (requests) -> {
                    requests.requestMatchers("/", "/home").permitAll();
                    requests.anyRequest().authenticated();
                }
        );
        //configurar pagina de login

        http.formLogin((login) -> {
            login.loginPage("/login").permitAll().defaultSuccessUrl("/drive", true);
        });

        http.logout(
                (logout) -> {
                    logout.permitAll();
                }
        );
        return http.build();
    }
    //declarar metodo de config
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("Rodrigo")
                .password("root")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


}
