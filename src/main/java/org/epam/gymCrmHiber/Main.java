package org.epam.gymCrmHiber;

import org.epam.gymCrmHiber.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
      new AnnotationConfigApplicationContext(AppConfig.class);
    }
}