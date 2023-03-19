package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.UserService;

/**
 *
 * @author Karsten Monteiro
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        
        // get all
        try {
            List<User> users = us.getAll();
            if (users.size() <= 0) {
                request.setAttribute("message", "noUsers");
                request.setAttribute("numUsers", null);
            }
            else {
                request.setAttribute("numUsers", "<0");
                request.setAttribute("users", users);
            }
        }
        catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "edit":
                    String email = request.getParameter("email");
                    User user = us.get(email);
                    Role role = user.getRole();
                    String userRole = role.getRoleName();

                    request.setAttribute("editUser", user);

                    // set the value of <select> tag (in the 'EDIT USER' section) to the user's role by setting the respective attribute to "selected" (attributes are within <option> tag, i.e., <option ${sysAdmin}>system admin</option>
                    if (userRole.equals("system admin")) {
                        request.setAttribute("sysAdmin", "selected");
                    }
                    else if (userRole.equals("regular user")) {
                        request.setAttribute("regUser", "selected");
                    }
                    break;
                case "delete":
                    us.delete(request.getParameter("email"));
                
                    // get updated users list
                    List<User> users = us.getAll();
                    if (users.size() <= 0) {
                       request.setAttribute("message", "allUsersDeleted");
                       request.setAttribute("users", users);
                       request.setAttribute("numUsers", null);
                    }
                    else {
                        request.setAttribute("users", users);
                    }
            }
        }
        catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UserService us = new UserService();

        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String password = request.getParameter("password");
        String roleName = request.getParameter("rolename");
        int roleId = 0;

        // determine role id
        if (roleName.equals("system admin")) {
            roleId = 1;
        }
        else if (roleName.equals("regular user")) {
            roleId = 2;
        }

        try {
            switch (action) {
                case "insert":
                    // verify there are no empty fields
                    if (!(email.equals("") || firstName.equals("") || lastName.equals("") || password.equals(""))) {
                        us.insert(email, firstName, lastName, password, roleId);
                        request.setAttribute("numUsers", "");
                    }
                    else {
                        request.setAttribute("error", "<span style=\"color: crimson; font-style: italic; font-weight: 500; font-size: 13px;\">All fields are required.</span>");
                        List<User> users = us.getAll();
                        if (users.size() <= 0) {
                            request.setAttribute("message", "noUsers");
                            request.setAttribute("numUsers", null);
                        }
                        else {
                            request.setAttribute("numUsers", "<0");
                            request.setAttribute("users", users);
                        }
                    }
                    
                    break;
                case "edit":
                    User user = us.get(email);
                    String userPswd = user.getPassword();
                    String userPswdIn = request.getParameter("editpassword");
                    
                    /** VERIFY PASSWORD (compare user input to the user's password) */
                    if (userPswdIn.equals(userPswd)) {
                        us.edit(email, firstName, lastName, password, roleId);
                    }
                    else {
                        request.setAttribute("invalidPassMsg", "<span style=\"color: crimson; font-style: italic; font-weight: 500; font-size: 13px;\">Incorrect password. Please try again.</span><br>");
                    }
                    request.setAttribute("numUsers", "");
            }
        }
        catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "duplicateUser");
            request.setAttribute("duplicateError", "");
        }
        
        try {
            List<User> users = us.getAll();
            request.setAttribute("users", users);
        }
        catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}