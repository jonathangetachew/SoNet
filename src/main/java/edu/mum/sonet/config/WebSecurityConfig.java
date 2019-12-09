package edu.mum.sonet.config;
import edu.mum.sonet.security.TokenAuthenticationFilter;
import edu.mum.sonet.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import edu.mum.sonet.security.oauth2.OAuth2AuthenticationFailureHandler;
import edu.mum.sonet.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private final MyUserDetails userDetails;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    public WebSecurityConfig(@Lazy JwtTokenProvider jwtTokenProvider, @Lazy MyUserDetails userDetails,
                             OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                             OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler,
                             HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetails = userDetails;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().ignoringAntMatchers().disable()
                .headers().frameOptions().disable()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(authorizeRequests -> {
                        try {
                            authorizeRequests
                                    .antMatchers("/h2-console/**").permitAll()
                                    .antMatchers("/css/**", "/index").permitAll()
                                    .antMatchers("/js/**", "/index").permitAll()
                                    .antMatchers("/").permitAll()
                                    .antMatchers("/login").permitAll()
                                    .antMatchers("/register").permitAll()
    //                                .antMatchers("/user/**").hasRole("USER")
                                    .anyRequest().authenticated()
                                        .and()
                                        .apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
                                    ;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .failureUrl("/login-error")
                )
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//        http.exceptionHandling().accessDeniedPage("/login");

        // Apply JWT
//        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
//        =========================================================================
//        // Disable CSRF (cross site request forgery)
//        http.csrf().disable();
//
//        // No session will be created or used by spring security
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // Entry points
//        http.authorizeRequests()//
//                .antMatchers("/h2-console/**/**").permitAll()
//                .antMatchers("/css/**", "/index").permitAll()
//                .antMatchers("/js/**", "/index").permitAll()
//                .antMatchers("/user/**").hasRole("USER")
//                // Disallow everything else..
//                .anyRequest().authenticated();
//
//        // If a user try to access a resource without having enough permissions
//        http.exceptionHandling().accessDeniedPage("/login");
//
//        // Apply JWT
//        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
//
//        // Optional, if you want to test the API from a browser
//        // http.httpBasic();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // Allow swagger to be accessed without authentication
//        web.ignoring().antMatchers("/v2/api-docs")//
//                .antMatchers("/swagger-resources/**")//
//                .antMatchers("/swagger-ui.html")//
//                .antMatchers("/configuration/**")//
//                .antMatchers("/webjars/**")//
//                .antMatchers("/public")
//                .antMatchers("/login")
//                .antMatchers("/index")
//                .antMatchers("/")
//                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
//                .and()
//                .ignoring()
//                .antMatchers("/h2-console/**/**");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//
//    @Bean
//    public FilterRegistrationBean < JwtTokenFilter > filterTokenBean() {
//        FilterRegistrationBean < CustomURLFilter > registrationBean = new FilterRegistrationBean();
//        CustomURLFilter customURLFilter = new CustomURLFilter();
//
//        registrationBean.setFilter(customURLFilter);
//        registrationBean.addUrlPatterns("/greeting/*");
//        registrationBean.setOrder(2); //set precedence
//        return registrationBean;
//    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
}