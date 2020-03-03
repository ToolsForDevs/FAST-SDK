package com.toolsfordevs.fast.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Target(ElementType.TYPE) // on class level
@Retention(CLASS)
public @interface FastIncludePlugin {}
