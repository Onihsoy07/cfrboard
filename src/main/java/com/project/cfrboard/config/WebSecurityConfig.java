package com.project.cfrboard.config;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 1. 정적 자원(Resource)에 대해서 인증된 사용자가  정적 자원의 접근에 대해 ‘인가’에 대한 설정을 담당하는 메서드이다.
     *
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 자원에 대해서 Security를 적용하지 않음으로 설정
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // [STEP1] 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.
        http.csrf().disable();

        // [STEP2] form 기반의 로그인에 대해 비 활성화하며 커스텀으로 구성한 필터를 사용한다.
        http.formLogin().disable();

        // [STEP3] 토큰을 활용하는 경우 모든 요청에 대해 '인가'에 대해서 사용.
        http.authorizeHttpRequests(authz -> authz.anyRequest().permitAll());

        // [STEP4] Spring Security Custom Filter Load - Form '인증'에 대해서 사용
//        http.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // [STEP5] Session 기반의 인증기반을 사용하지 않고 추후 JWT를 이용하여서 인증 예정
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // [STEP6] Spring Security JWT Filter Load
//        http.addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class);

        // [STEP7] 최종 구성한 값을 사용함.
        return http.build();
    }


}
