package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;

public class RoleService {
    public Role get(int roleID) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleID);
        
        return role;
    }
    
    public List<Role> getAll() throws Exception {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        
        return roles;
    }
}
