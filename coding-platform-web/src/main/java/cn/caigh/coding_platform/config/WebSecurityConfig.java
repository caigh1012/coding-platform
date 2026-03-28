package cn.caigh.coding_platform.config;

import cn.caigh.coding_platform.exception.AuthenticationEntryHandler;
import cn.caigh.coding_platform.filter.JwtAuthenticationFilter;
import cn.caigh.coding_platform.service.impl.UserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  /**
   * 公共接口，无需登录
   */
  private static final String[] PUBLIC_URLS = {
      "/login/pwd.json",
      "/register.json",
      "/captcha.json",
      "/encryptappid.json",
      "/verifyAppid.json"
  };

  @Autowired
  private UserDetailsServiceImpl userDetailService;

  private final AuthenticationEntryHandler authenticationEntryHandler;

  private final AccessDeniedHandler accessDeniedHandlerImpl;

  /**
   * 自定义的jwt身份验证过滤器
   */
  @Resource
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  public WebSecurityConfig(AuthenticationEntryHandler authenticationEntryPoint,
                           AccessDeniedHandler accessDeniedHandler) {
    this.authenticationEntryHandler = authenticationEntryPoint;
    this.accessDeniedHandlerImpl = accessDeniedHandler;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 配置跨域
        .csrf(AbstractHttpConfigurer::disable) // 禁止 csrf
        .formLogin(AbstractHttpConfigurer::disable) // 禁止 formLogin
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(PUBLIC_URLS).permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(e -> e
            .authenticationEntryPoint(authenticationEntryHandler)  // 认证异常
            .accessDeniedHandler(accessDeniedHandlerImpl));        // 授权异常

    // 将 jwt 校验过滤器配置在 UsernamePasswordAuthenticationFilter 前
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * 跨域设置
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    configuration.setAllowedOrigins(List.of("*")); // 允许的前端地址
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(false); // 是否允许发送 Cookie
//    configuration.setMaxAge(3600L); // 预检请求的有效期（秒）
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailService);
    provider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(provider);
  }
}
