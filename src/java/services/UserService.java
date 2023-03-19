package services;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author Karsten Monteiro
 */
public class UserService {
    
    // get all
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        
        return users;
    }
    
    // get
    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        
        return user;
    }
    
    // insert
    public void insert(String email, String firstName, String lastName, String password, int roleId) throws Exception {
        User user = new User(email, firstName, lastName, password);
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleId);
        user.setRole(role);
        
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }
    
    // edit
    public void edit(String email, String firstName, String lastName, String password, int roleId) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleId);
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        
        userDB.edit(user);
    }
    
    // delete
    public void delete(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        
        userDB.delete(user);
    }
}