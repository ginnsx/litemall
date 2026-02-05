package org.linlinjava.litemall.core.util.bcrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.security.SecureRandom;

public class BCryptTest {

    @Test
    public void testHashpwSaltIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", null);
        });
    }

    @Test
    public void testHashpwSaltTooShort() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "foo");
        });
    }

    @Test
    public void testHashpwInvalidSaltVersion() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "+2a$10$.....................");
        });
    }

    @Test
    public void testHashpwInvalidSaltVersion2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "$1a$10$.....................");
        });
    }

    @Test
    public void testHashpwInvalidSaltRevision() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "$2+$10$.....................");
        });
    }

    @Test
    public void testHashpwInvalidSaltRevision2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "$2a+10$.....................");
        });
    }

    @Test
    public void testHashpwSaltTooShort2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "$2a$10+.....................");
        });
    }

    @Test
    public void testHashpwMissingSaltRounds() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "$2$a10$.....................");
        });
    }

    @Test
    public void testHashpwTooLittleRounds() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "$2a$03$......................");
        });
    }

    @Test
    public void testHashpwTooManyRounds() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.hashpw("foo", "$2a$32$......................");
        });
    }

    @Test
    public void testHashpw() {
        Assertions.assertEquals(
                "$2a$10$......................0li5vIK0lccG/IXHAOP2wBncDW/oa2u",
                BCrypt.hashpw("foo", "$2a$10$......................"));

        Assertions.assertEquals(
                "$2$09$......................GlnmyWmDnFB.MnSSUnFsiPvHsC2KPBm",
                BCrypt.hashpw("foo", "$2$09$......................"));
    }

    @Test
    public void testGensalt() {
        // Since we cannot easily deterministically mock SecureRandom in all environments without PowerMock,
        // we check the format of the generated salt instead of the exact value.
        String salt = BCrypt.gensalt();
        Assertions.assertTrue(salt.startsWith("$2a$10$"));
        Assertions.assertEquals(29, salt.length());

        String salt9 = BCrypt.gensalt(9);
        Assertions.assertTrue(salt9.startsWith("$2a$09$"));
        Assertions.assertEquals(29, salt9.length());
    }

    @Test
    public void testGensaltTooLittleRounds() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.gensalt(3);
        });
    }

    @Test
    public void testGensaltTooManyRounds() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BCrypt.gensalt(32);
        });
    }

    @Test
    public void testCheckpw() {
        Assertions.assertFalse(BCrypt.checkpw("foo", "$2a$10$......................"));

        final String hashed = BCrypt.hashpw("foo", BCrypt.gensalt());
        Assertions.assertTrue(BCrypt.checkpw("foo", hashed));
        Assertions.assertFalse(BCrypt.checkpw("bar", hashed));
    }
}
