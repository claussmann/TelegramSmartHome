package TelegramSmartHome.SmartHome.UserManagement;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void setup(){
        user = new User("test");
        user.addToGroup("foo");
        user.addToGroup("bar");
        user.addToGroup("foobar");
        user.addToGroup("admin");
        user.addToGroup("gollum");
    }

    @Test
    public void userIsInGroup(){
        assertTrue(user.isMemberOf("foo"));
    }

    @Test
    public void userIsNotInGroup(){
        assertFalse(user.isMemberOf("lol"));
    }

    @Test
    public void userIsRemovedFromGroup(){
        assertTrue(user.isMemberOf("foo"));
        user.removeFromGroup("foo");
        assertFalse(user.isMemberOf("foo"));
    }

    @Test
    public void userIsInGroupMultipleTimesButCanBeRemoved(){
        user.addToGroup("foo");
        assertTrue(user.isMemberOf("foo"));
        user.removeFromGroup("foo");
        assertFalse(user.isMemberOf("foo"));
    }
}