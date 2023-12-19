package com.example.ebookapp.config;


import com.example.ebookapp.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/*").permitAll()
                .requestMatchers("/user/**").hasAuthority("USER")
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                .anyRequest().authenticated() // Tat ca cac URL khac phai authentication
                .anyRequest().permitAll()
        ).formLogin(login -> login //Authentication by login by url: /logon
                .loginPage("/logon") //trang de login
                .loginProcessingUrl("/logon") // auto khop
                .usernameParameter("username") // Authenticate by username anh password
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
               // .defaultSuccessUrl("/admin/home", true) // redirect when login succsessfully, true de TH ta dang o /adnin roi
        ).logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/logon")); // sau khi logout "/logout" thi ban ve "/logon"

        return httpSecurity.build();
    }

//    Cho phep truy cap vao cac link trong /static (link css, html, image, ...) ma khong can logon
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/assets-user/**","/static/**", "/assets-admin/**", "/uploads/**");
    }

    // đưa customUserDetailService để cấu hình xác thực (authentication)
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }

    // Tạo 1 Bean trả về đối tượng CustomAuthenticationSuccessHandler sử dụng để xử lý sự kiện khi người dùng đăng nhập thành công.
    @Bean
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
