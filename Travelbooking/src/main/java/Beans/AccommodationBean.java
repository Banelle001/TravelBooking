package beans;

import entities.Accommodation;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AccommodationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "travelPU")
    private EntityManager em;

    private Accommodation accommodation = new Accommodation();
    private Accommodation selectedAccommodation;

    private String selectedCity;
    private String selectedType;

    private List<Accommodation> searchResultsList;

    // === Getters and Setters ===

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Accommodation getSelectedAccommodation() {
        return selectedAccommodation;
    }

    public void setSelectedAccommodation(Accommodation selectedAccommodation) {
        this.selectedAccommodation = selectedAccommodation;
    }

    public String getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public List<Accommodation> getSearchResultsList() {
        return searchResultsList;
    }

    public List<Accommodation> getAllAccommodations() {
        return em.createQuery("SELECT a FROM Accommodation a", Accommodation.class).getResultList();
    }

    // === Business Logic ===

    public void saveAccommodation() {
        if (accommodation.getId() == null) {
            em.persist(accommodation);
        } else {
            em.merge(accommodation);
        }
        accommodation = new Accommodation(); // reset form
    }

    public void deleteAccommodation(Accommodation acc) {
        Accommodation toDelete = em.find(Accommodation.class, acc.getId());
        if (toDelete != null) {
            em.remove(toDelete);
        }
    }

    // Method to search accommodations based on selected city and type
    public String searchResults() {
        searchResultsList = em.createQuery(
                "SELECT a FROM Accommodation a WHERE a.city.name = :city AND a.type = :type", Accommodation.class)
                .setParameter("city", selectedCity)
                .setParameter("type", selectedType)
                .getResultList();

        return "searchResults.xhtml?faces-redirect=true";
    }

    // Method to store selected accommodation and go to room selection page
    public String viewRooms(Accommodation accommodation) {
        this.selectedAccommodation = accommodation;
        return "roomselection.xhtml?faces-redirect=true";
    }
}
