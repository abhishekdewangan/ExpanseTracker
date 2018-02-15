package com.money.expencetracker.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by abhishek on 06/12/17.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {
}
