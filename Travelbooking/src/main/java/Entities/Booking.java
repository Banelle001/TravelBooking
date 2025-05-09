package entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int paid;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    
    private String fullname;
    private String email;
    
    @Temporal(TemporalType.DATE)
    private Date checkindate;
    
    @Temporal(TemporalType.DATE)
    private Date checkoutdate;    
    private int numberOfTravelers;
    private String specialRequests;

    // Constructors
    public Booking() {}

    public Booking(User user, String fullname, String email, Date checkindate, Date checkoutdate, 
                  int numberOfTravelers, String specialRequests) {
        this.user = user;
        this.fullname = fullname;
        this.email = email;
        this.checkindate = checkindate;
        this.checkoutdate = checkoutdate;
        this.numberOfTravelers = numberOfTravelers;
        this.specialRequests = specialRequests;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Date getCheckindate() { return checkindate; }
    public void setCheckindate(Date checkindate) { this.checkindate = checkindate; }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }
    
    
    
    public Date getCheckoutdate() { return checkoutdate; }
    public void setCheckoutdate(Date checkoutdate) { this.checkoutdate = checkoutdate; }

    public int getNumberOfTravelers() { return numberOfTravelers; }
    public void setNumberOfTravelers(int numberOfTravelers) { 
        this.numberOfTravelers = numberOfTravelers; 
    }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { 
        this.specialRequests = specialRequests; 
    }
}