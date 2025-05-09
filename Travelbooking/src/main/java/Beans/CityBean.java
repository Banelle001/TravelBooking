package beans;

import entities.City;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CityBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "travelPU") // Update this if necessary
    private EntityManager em;

    private City city = new City();

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    // Method to save a city
    public void saveCity() {
        if (city.getId() == null) {
            em.persist(city);
        } else {
            em.merge(city);
        }
        city = new City(); // Reset form
    }

    // Method to get all cities from the database
    public List<City> getAllCities() {
        return em.createQuery("SELECT c FROM City c", City.class).getResultList();
    }

    // Method to delete a city
    public void deleteCity(City c) {
        City toDelete = em.find(City.class, c.getId());
        if (toDelete != null) {
            em.remove(toDelete);
        }
    }
}
