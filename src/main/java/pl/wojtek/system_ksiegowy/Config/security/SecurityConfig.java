package pl.wojtek.system_ksiegowy.Config.security;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/adduser").permitAll()
                .antMatchers("/admin/**").access("hasAnyAuthority('Administrator')")
                .antMatchers("/employees/**").access("hasAnyAuthority('Pracownicy')")
                .antMatchers("/services/**").access("hasAnyAuthority('Usługi')")
                .antMatchers("/templates/**").access("hasAnyAuthority('Szablony')")
                .antMatchers("/reporst/**").access("hasAnyAuthority('Raporty')")
                .antMatchers("/invoices/**").access("hasAnyAuthority('Fakturowanie')")
                .antMatchers("/contracts/**").access("hasAnyAuthority('Umowy')")
                .antMatchers("/contractors/list/contractor=1").access("hasAnyAuthority('Administrator')")
                .antMatchers("/contractors/**").access("hasAnyAuthority('Kontrahenci')")
                .antMatchers("/tasks/**").access("hasAnyAuthority('Zgłoszenia')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .loginProcessingUrl("/loginverify")
                .defaultSuccessUrl("/", true)
                .failureUrl("/loginerror").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();

        httpSecurity.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());

    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    public SessionRegistry sessionRegistry()
    {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher()
    {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
}
