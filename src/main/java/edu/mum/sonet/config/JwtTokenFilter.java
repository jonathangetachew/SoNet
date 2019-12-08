package edu.mum.sonet.config;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.GenericFilterBean;

import org.springframework.web.filter.OncePerRequestFilter;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
//public class JwtTokenFilter extends OncePerRequestFilter {
public class JwtTokenFilter extends GenericFilterBean {
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println(" >>> filter <<<");
//        String token = jwtTokenProvider.resolveToken(httpServletRequest);
////        String token = jwtTokenProvider.resolveToken(Session);
//        System.out.println(" >> token: "+token);
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpSession session = attr.getRequest().getSession(true);
//        System.out.println("session token: "+session.getAttribute("token"));
//        try {
//            if (token != null && jwtTokenProvider.validateToken(token)) {
//                Authentication auth = jwtTokenProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            return;
//        }
////        catch (CustomException ex) {
////            //this is very important, since it guarantees the user is not authenticated at all
////            SecurityContextHolder.clearContext();
////            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
////            return;
////        }
//
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        System.out.print(" >>> filter ->");
//        String token = jwtTokenProvider.resolveToken(httpServletRequest);
//        String token = jwtTokenProvider.resolveToken(Session);
//        System.out.print("  token: "+token);
        String path = httpServletRequest.getRequestURI();
        System.out.println(" >>> path : " + path);
        if (!path.startsWith("/login") && !path.startsWith("/index") && !path.startsWith("/register") && !path.equals("/")) {


        String token = null;
        HttpSession session = httpServletRequest.getSession(false);// don't create if it doesn't exist
        if (session != null && !session.isNew()) {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            session = attr.getRequest().getSession(true);
            token = session.getAttribute("token").toString();
        }

        System.out.println("  session token: " + token);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                System.out.println(" >> filter authenticate token");
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                chain.doFilter(request, response);
            } else {
                httpServletResponse.sendRedirect("/login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.sendRedirect("/login");
        }
//        catch (CustomException ex) {
//            //this is very important, since it guarantees the user is not authenticated at all
//            SecurityContextHolder.clearContext();
//            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
//            return;
//        }
    }else{
            chain.doFilter(request, response);
        }

    }
}