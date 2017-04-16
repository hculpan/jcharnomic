package com.charnomic.jcharnomic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by harryculpan on 3/30/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface ServiceMethod {

    public String targetPath();

    public boolean playerOnly() default false;

    public boolean gameMonitorOnly() default false;

}
