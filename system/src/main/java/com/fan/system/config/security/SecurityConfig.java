package com.fan.system.config.security;

import com.fan.system.filter.JwtAuthencationTokenFilter;
import com.fan.system.pojo.LoginParams;
import com.fan.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security配置类
 * @author fgq
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "/favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha/**",
                "/test/**",
                "/statics/**",
                "/upload"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
//                new WhiteListedAllowFromStrategy(
//                        Arrays.asList("http://localhost:8082"))));
//        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//                .requestMatchers()
//                .antMatchers("/system/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/system/**").authenticated()
//                .and()
//                .csrf().disable();

//        使用jwt 不需要csrf
        http.csrf().disable()
//                基于token 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                允许登录访问
//                .antMatchers(
//                        "/system/**",
//                "/login",
//                "/logout",
//                "/css/**",
//                "/js/**",
//                "/index.html",
//                "/favicon.ico",
//                "/doc.html",
//                "/webjars/**",
//                "/swagger-resources/**",
//                "/v2/api-docs/**",
//                "/captcha/**"
//                ).permitAll()
//                除了上面 所有请求都需要请求认证
                .anyRequest()
                .authenticated()
                .and()
//                禁用缓存
                .headers()
                .cacheControl();
//        http.cors().and().authorizeRequests().antMatchers("/hr/**").authenticated();
//        添加jwt 登录授权过滤器
        http.addFilterBefore( jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        添加自定义未登录未授权结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> {
            LoginParams admin = sysUserService.getSysUserByUserName(username);
            if(admin!=null){
                return admin;
            }
            return null;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){
        return new JwtAuthencationTokenFilter();
    }
}
