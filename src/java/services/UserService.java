package services;

import dataaccess.RoleDB;
import models.User;
import java.util.List;
import dataaccess.UserDB;
import models.Role;


public class UserService {
    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        
        return user;
    }

    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        
        return users;
    }
    
    public void insert(String email, String firstName, String lastName, String password, int roleId) throws Exception {
        User user = new User(email, firstName, lastName, password);
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleId);
        user.setRole(role);
        
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    public void update(String email, String firstName, String lastName, String password, int roleId) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleId);
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        
        userDB.update(user);
    }

    public void delete(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        
        userDB.delete(user);
    }
}
