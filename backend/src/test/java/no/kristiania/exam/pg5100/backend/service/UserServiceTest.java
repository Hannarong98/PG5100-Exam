package no.kristiania.exam.pg5100.backend.service;


import no.kristiania.exam.pg5100.backend.StubApplication;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest extends ServiceTestBase{

    @Autowired
    UserService userService;

    @Test
    public void testCreateUser(){
      assertTrue(userService.createUser("foo@bar.baz", "1234", "foo", "bar"));
    }

    @Test
    public void testFailAlreadyCreated(){
        boolean isCreated = userService.createUser("foo@bar.baz", "1234", "foo", "bar");

        assertTrue(isCreated);

        boolean isCreatedAgain = userService.createUser("foo@bar.baz", "1234", "foo", "bar");

        assertFalse(isCreatedAgain);
    }

    @Test
    public void testCanRetrieveUser(){

        String email = "foo@bar.baz";
        boolean isCreated = userService.createUser(email, "1234", "foo", "bar");

        assertTrue(isCreated);

        User user = userService.getUser(email);

        assertEquals("foo", user.getForename());
        assertEquals("foo@bar.baz", user.getEmail());
        assertEquals("bar", user.getSurname());
        assertEquals(3000, user.getMillCurrency());
        assertEquals(3, user.getLootBoxesLeft());
        assertTrue(user.getEnabled());
    }
}
