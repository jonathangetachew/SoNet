package edu.mum.sonet.config;
import edu.mum.sonet.models.User;
import edu.mum.sonet.models.enums.Role;
import edu.mum.sonet.models.enums.UserStatus;
import edu.mum.sonet.security.*;
import edu.mum.sonet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true,
//        prePostEnabled = true
//)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private final OidcUserService oidcUserService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserService userService;

    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider, OidcUserService oidcUserService,
                             @Lazy CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.jwtTokenProvider = jwtTokenProvider;

        this.oidcUserService = oidcUserService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().ignoringAntMatchers().disable()
                .headers().frameOptions().disable()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> {
                        try {
//                            .authorizeRequests(authorizeRequests ->
                            // Failure handler invoked after authentication failure
                            authorizeRequests
                                    .antMatchers("/h2-console/**").permitAll()
                                    .antMatchers("/css/**", "/index").permitAll()
                                    .antMatchers("/js/**", "/index").permitAll()
                                    .antMatchers("/").permitAll()
                                    .antMatchers("/login").permitAll()
                                    .antMatchers("/login-error").permitAll()
                                    .antMatchers("/signup").permitAll()
                                    .antMatchers("/register").permitAll()
                                    ///> Admin specific route
                                    .antMatchers("/admin/**").hasAuthority(Role.ADMIN.toString())
                                    ///> User specific route
                                    .antMatchers("/user/**").hasAuthority(Role.USER.toString())
                                    .anyRequest().authenticated()
                                    .and()
                                    .exceptionHandling()
                                        .accessDeniedPage("/error")
                                    .and()
                                    .formLogin()
                                    .loginPage("/login")
                                    .failureUrl("/login-error")
//                                    .defaultSuccessUrl("/user/index")
//                                    .loginProcessingUrl("/user/index")
                                    .usernameParameter("email")
                                    .passwordParameter("password")
                                    .successHandler(this::successAuthenticate)
                                    .failureHandler(this::failAuthenticate)
                                    .and()
                                    .logout()
//                                    .logoutUrl("/signout")   // Specifies the logout URL, default URL is '/logout'
                                    .logoutSuccessHandler((req,res,auth)->{   // Logout handler called after successful logout
                                        req.getSession().removeAttribute("token");
                                        res.sendRedirect("/login?logout"); // Redirect user to login page with message.
                                    })
                                    .permitAll() // Allow access to any URL associate to logout()
                                    .and()
                                        .apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
                                    .and()
                                        ///> Enable oauth2
                                        .oauth2Login()
                                            .loginPage("/login")
                                            .authorizationEndpoint()
                                                .baseUri("/oauth2/authorize")
                                                .authorizationRequestRepository(customAuthorizationRequestRepository())
                                                .and()
                                            .redirectionEndpoint()
                                                .baseUri("/oauth2/callback/*")
                                                .and()
                                                .userInfoEndpoint()
                                                .oidcUserService(oidcUserService)
                                                .and()
                                                .successHandler(customAuthenticationSuccessHandler);
//                                            .failureHandler(oAuth2AuthenticationFailureHandler);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                );

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
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenProvider);
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> customAuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    public void successAuthenticate(HttpServletRequest req, HttpServletResponse res, Authentication auth){

        try {

            //Success handler invoked after successful authentication
            String token = jwtTokenProvider.createToken(auth);
            System.out.println("----- success login ----"+auth.getName() + "   token : " + token);

            req.getSession().setAttribute("token", token);
            req.getSession().setAttribute("loginMessage", "login successfully");

//                                        SecurityContext sc = SecurityContextHolder.getContext();
//                                        sc.setAuthentication(auth);
//                                        HttpSession session = req.getSession(true);
//                                        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
//                                        res.sendRedirect("/user/index");

            ///> If user is an Admin -> redirect to admin dashboard
            String userRole = Role.USER.toString();

            for (GrantedAuthority authority : auth.getAuthorities()) {
                userRole = authority.getAuthority();
                System.out.println(userRole);
                if ( userRole.equals(Role.ADMIN.toString())) break;
            }


            boolean isAdmin = userRole.equals(Role.ADMIN.toString());
            String userPath = "/user/index";
            if(!isAdmin){
                User user = userService.findByEmail(auth.getName());
                if(user.getUserStatus() == UserStatus.BLOCKED){
                    //id will change to unhealthyContent number
                    userPath = "/user/blocked?num="+user.getId();
                }
            }
            redirectStrategy.sendRedirect(req, res, isAdmin ? "/admin/dashboard" : userPath); // Redirect user
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