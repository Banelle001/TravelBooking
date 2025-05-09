package Beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;

@Named("paymentBean")
@SessionScoped
public class PaymentBean implements Serializable {

    @PersistenceContext
    private EntityManager em;

    private String paymentStatus = "Unpaid"; // Default status

    // Method to handle payment status change
    @Transactional
    public void updatePaymentStatus() {
        try {
            Long bookingId = 1L; // Replace with actual booking ID

            em.createQuery("UPDATE Booking b SET b.paid = 1 WHERE b.id = :id")
              .setParameter("id", bookingId)
              .executeUpdate();

            paymentStatus = "Paid"; // Assume paid after click
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
