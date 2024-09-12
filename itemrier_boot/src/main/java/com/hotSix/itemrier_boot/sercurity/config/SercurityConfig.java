package com.hotSix.itemrier_boot.sercurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.hotSix.itemrier_boot.sercurity.handler.CustomAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SercurityConfig {

	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http	.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/myPage/**", "/groupPurchase/create",
								"/usedGoods/create","/auction/create",
								"/usedGoods/view/**", "/groupPurchase/view/**", "/auction/view/**").authenticated()
						.anyRequest().permitAll())
    
				.formLogin(formLogin -> formLogin
						.loginPage("/user/login/form")
						.loginProcessingUrl("/user/login")
						.usernameParameter("email")
						.successHandler(new CustomAuthenticationSuccessHandler())
						.permitAll())
				.logout((logout) -> logout
						.logoutUrl("/logout")   // 로그아웃 처리 URL (= form action url)
			            //.logoutSuccessUrl("/") // 로그아웃 성공 후 targetUrl, logoutSuccessHandler 가 있다면 효과 없으므로 주석처리.
			            .addLogoutHandler((request, response, authentication) -> { 
			                // 사실 굳이 내가 세션 무효화하지 않아도 됨. 
			                // LogoutFilter가 내부적으로 해줌.
			                HttpSession session = request.getSession();
			                if (session != null) {
			                    session.invalidate();
			                }
			            })  // 로그아웃 핸들러 추가
			            .logoutSuccessHandler((request, response, authentication) -> {
			                response.sendRedirect("/");
			            }) // 로그아웃 성공 핸들러
			            .deleteCookies("remember-me")) // 로그아웃 후 삭제할 쿠키 지정
				.exceptionHandling().accessDeniedPage("/user/login/form");
		return http.build();
	}
	


		
		
}
