package dataaccess;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;


/**
 *
 * @author Karsten Monteiro
 */
public class RoleDB {
    
    public Role getRole(int roleID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Role role = em.find(Role.class, roleID);
            
            return role;
        } finally {
            em.close();
        }
    }
    
    public List<Role> getAll() throws SQLException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Role> roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            
            return roles;
        } finally {
            em.close();
        }
    }
}