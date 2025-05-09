package beans;

import entities.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserBean implements Serializable {

    @PersistenceContext(unitName = "travelPU")
    private EntityManager em;

    private User user = new User(); // input for login or registration
    private User loggedInUser;
    private boolean loggedIn = false;

    // Register a new traveler
    @Transactional
    public String register() {
        user.setRole("traveler");
        em.persist(user);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration successful. Please log in.", null));
        user = new User();
        return "login.xhtml?faces-redirect=true";
    }

    // Login logic with feedback
    public String login() {
        TypedQuery<User> query = em.createQuery(
            "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
        query.setParameter("email", user.getEmail());
        query.setParameter("password", user.getPassword());

        List<User> result = query.getResultList();

        if (!result.isEmpty()) {
            loggedInUser = result.get(0);
            user = new User(); // reset login form
            return loggedInUser.getRole().equals("admin")
                ? "admin/add-accommodation.xhtml?faces-redirect=true"
                : "index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email or password.", null));
            return null; // stay on login.xhtml
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        user = new User();
        return "login.xhtml?faces-redirect=true";
    }

    // Getters and Setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public User getLoggedInUser() { return loggedInUser; }
    public void setLoggedInUser(User loggedInUser) { this.loggedInUser = loggedInUser; }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public boolean isAdmin() {
        return loggedInUser != null && "admin".equalsIgnoreCase(loggedInUser.getRole());
    }

    public boolean isTraveler() {
        return loggedInUser != null && "traveler".equalsIgnoreCase(loggedInUser.getRole());
    }
}