package beans;

import entities.Accommodation;
import entities.Booking;
import entities.Room;
import entities.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class BookingBean implements Serializable {

    @PersistenceContext(unitName = "travelPU")
    private EntityManager em;
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private RoomBean roomBean;

    private Booking booking = new Booking();
    private Long selectedRoomId; // This was missing - added the field declaration

    @Transactional
    public String createBooking() {
        try {
            if (!userBean.isLoggedIn()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "You must be logged in to make a booking.", null));
                return "login.xhtml?faces-redirect=true";
            }
            
            // Set the user for the booking
            booking.setUser(userBean.getLoggedInUser());
            
            // Find and set the selected room
            if (selectedRoomId == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Please select a room", null));
                return null;
            }
            
            Room selectedRoom = em.find(Room.class, selectedRoomId);
            if (selectedRoom == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Selected room not found", null));
                return null;
            }
            booking.setRoom(selectedRoom);
            
            // Validate checkout date is after checkin date
            if (booking.getCheckoutdate().before(booking.getCheckindate())) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Check-out date must be after check-in date", null));
                return null;
            }
            
            em.persist(booking);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Booking created successfully!", null));
            
            // Reset the booking form
            booking = new Booking();
            selectedRoomId = null; // Also reset the selected room ID
            
            // Redirect to confirmation page
            return "confirmation.xhtml?faces-redirect=true";
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error creating booking: " + e.getMessage(), null));
            return null;
        }
    }
    public String prepareBooking(Long roomId) {
        this.selectedRoomId = roomId;
        return "booking.xhtml?faces-redirect=true";
    }


    public List<Booking> getUserBookings() {
        if (!userBean.isLoggedIn()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                "Please login to view your bookings", null));
            return Collections.emptyList();
        }

        try {
            return em.createQuery(
                "SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.checkindate DESC", Booking.class)
                .setParameter("userId", userBean.getLoggedInUser().getId())
                .getResultList();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error fetching bookings: " + e.getMessage(), null));
            return Collections.emptyList();
        }
    }

    public List<Booking> getAllBookings() {
        return em.createQuery(
            "SELECT b FROM Booking b ORDER BY b.checkindate DESC", Booking.class)
            .getResultList();
    }

    public String getTodayDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

    // Getters and Setters
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    
    public Long getSelectedRoomId() { return selectedRoomId; }
    public void setSelectedRoomId(Long selectedRoomId) { this.selectedRoomId = selectedRoomId; }
}