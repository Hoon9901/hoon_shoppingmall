package com.example.hoon_shop.config;

import com.example.hoon_shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 로그인 설정
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

        http.authorizeRequests() // httpServletRequest 사용
                .mvcMatchers("/", "/members/**",
                        "/item/**", "/images/**").permitAll() // 해당 경로들은 비인증으로 접근 허용
                .mvcMatchers("/admin/**").hasRole("ADMIN") // 어드민 접근권한
                .anyRequest().authenticated();  // 나머지 경로들은 모두 인증 요구
        // 비인증 사용자가 리소스에 접근할 시 수행되는 핸들러 등록
        http.exceptionHandling()
                .authenticationEntryPoint(
                        new CustomAuthenticationEntryPoint()
                );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리 하위 파일은 인증 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 인증
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());    // 비밀번호 암호화
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
