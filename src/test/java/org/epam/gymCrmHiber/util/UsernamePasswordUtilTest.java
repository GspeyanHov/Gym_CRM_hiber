package org.epam.gymCrmHiber.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UsernamePasswordUtilTest {

    @Test
    void generateUsername_ShouldReturnCorrectFormat() {
        String firstName = "John";
        String lastName = "Doe";

        String expectedUsername = "John.Doe";
        String actualUsername = UsernamePasswordUtil.generateUsername(firstName, lastName);

        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    void generatePassword_ShouldReturnNonEmptyString() {
        String password = UsernamePasswordUtil.generatePassword();

        assertNotNull(password);
        assertEquals(10, password.length());
        assertTrue(password.chars().allMatch(Character::isLetterOrDigit));
    }
}
