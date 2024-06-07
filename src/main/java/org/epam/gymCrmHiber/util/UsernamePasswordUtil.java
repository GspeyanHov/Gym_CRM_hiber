package org.epam.gymCrmHiber.util;

import org.apache.commons.lang3.RandomStringUtils;

public class UsernamePasswordUtil {

    public static String generateUsername(String firstName, String lastName) {
        return firstName + "." + lastName;
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}