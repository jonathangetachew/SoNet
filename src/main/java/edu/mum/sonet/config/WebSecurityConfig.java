package edu.mum.sonet.config;
import edu.mum.sonet.security.*;
import edu.mum.sonet.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import edu.mum.sonet.security.oauth2.OAuth2AuthenticationFailureHandler;
import edu.mum.sonet.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();



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
//                .csrf().ignoringAntMatchers().disable()
//                .headers().frameOptions().disable()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> {
                        try {
//                            .authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .antMatchers("/h2-console/**").permitAll()
                                    .antMatchers("/css/**", "/index").permitAll()
                                    .antMatchers("/js/**", "/index").permitAll()
                                    .antMatchers("/").permitAll()
                                    .antMatchers("/login").permitAll()
                                    .antMatchers("/register").permitAll()
                                    .anyRequest().authenticated()
                                    .and()
                                    .formLogin()
                                    .loginPage("/login")
                                    .failureUrl("/login-error")
//                                    .defaultSuccessUrl("/user/index")
//                                    .loginProcessingUrl("/user/index")
                                    .usernameParameter("email")
                                    .passwordParameter("password")
                                    .successHandler((req,res,auth)->{
                                        successAuthenticate(req,res,auth);
                                    })
                                    .failureHandler((req,res,exp)->{  // Failure handler invoked after authentication failure
                                        failAuthenticate(req,res,exp);
                                    })
                                    .and()
                                    .logout()
//                                    .logoutUrl("/signout")   // Specifies the logout URL, default URL is '/logout'
                                    .logoutSuccessHandler((req,res,auth)->{   // Logout handler called after successful logout
                                        System.out.println("==== logout ====");
                                        req.getSession().removeAttribute("token");
                                        res.sendRedirect("/login"); // Redirect user to login page with message.
                                    })
                                    .permitAll() // Allow access to any URL associate to logout()
                                    .and()
                                        .apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
                                    ;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                );


//                .oauth2Login()
//                    .authorizationEndpoint()
//                        .baseUri("/oauth2/authorize")
//                        .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
//                        .and()
//                    .redirectionEndpoint()
//                        .baseUri("/oauth2/callback/*")
//                        .and()
//                    .successHandler(oAuth2AuthenticationSuccessHandler)
//                    .failureHandler(oAuth2AuthenticationFailureHandler);
//
//        // Add our custom Token based authentication filter
//        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        // Apply JWT
//        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder());
    }


    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }


    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenProvider);
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    public void successAuthenticate(HttpServletRequest req, HttpServletResponse res, Authentication auth){

        try {

            //Success handler invoked after successful authentication


            for (GrantedAuthority authority : auth.getAuthorities()) {
                System.out.println(authority.getAuthority());
            }
            String token = jwtTokenProvider.createToken(auth);
            System.out.println("----- success login ----"+auth.getName() + "   token : " + token);

            req.getSession().setAttribute("token", token);
            req.getSession().setAttribute("loginMessage", "login successfully");

//                                        SecurityContext sc = SecurityContextHolder.getContext();
//                                        sc.setAuthentication(auth);
//                                        HttpSession session = req.getSession(true);
//                                        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
//                                        res.sendRedirect("/user/index");
            redirectStrategy.sendRedirect(req, res, "/user/index"); // Redirect user to index/home page
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void failAuthenticate(HttpServletRequest req, HttpServletResponse res, Exception exp){

        try {
            String errMsg = "";
            if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
                errMsg = "Invalid username or password.";
            } else {
                errMsg = "Unknown error - " + exp.getMessage();
            }
            System.out.println(" ------ fali login -------" + errMsg);
            res.sendRedirect("/login-error"); // Redirect user to login page with error message.
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}