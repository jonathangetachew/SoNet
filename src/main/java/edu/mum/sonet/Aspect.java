package edu.mum.sonet;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

    @Around("execution(* *.PostService.save(..))")
    public Object savePost(ProceedingJoinPoint pjp) {
        Object pj = pjp.getTarget();
        System.out.println("ASPECT");
        return null;
    }
}
