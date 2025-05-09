package beans;

import entities.Accommodation;
import entities.Room;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class RoomBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "travelPU")
    private EntityManager em;

    private List<Room> rooms;
    private Room selectedRoom;

    public void loadRooms(Accommodation accommodation) {
        rooms = em.createQuery("SELECT r FROM Room r WHERE r.accommodation = :acc", Room.class)
                  .setParameter("acc", accommodation)
                  .getResultList();
    }
    public void initRooms() {
        rooms = em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
    }

    public List<Room> getRoomsByAccommodation(Accommodation accommodation) {
        return em.createQuery("SELECT r FROM Room r WHERE r.accommodation = :acc", Room.class)
                 .setParameter("acc", accommodation)
                 .getResultList();
    }
    public void saveRoom() {
        if (selectedRoom != null) {
            em.persist(selectedRoom);
            selectedRoom = new Room(); // reset form
        }
    }


    // === Getters and Setters ===

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }
}
