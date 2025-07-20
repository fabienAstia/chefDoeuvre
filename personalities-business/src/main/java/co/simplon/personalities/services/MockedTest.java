package co.simplon.personalities.services;

import co.simplon.personalities.entities.Role;
import co.simplon.personalities.entities.User;

public class MockedTest {

    public User mockedUser() {
        Role role = new Role();
        role.setRole("USER");

        User user = new User();
        user.setRole(role);
        return user;
    }


}
