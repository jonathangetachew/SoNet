package edu.mum.sonet.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests(authorizeRequests ->
                        {
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
//                                        .and()
//                                        .apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
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
                );

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
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
}