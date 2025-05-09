package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "accommodation")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type; // hotel or bnb

    private String image;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    // Constructors
    public Accommodation() {
    }

    public Accommodation(String name, String type, String image, String description) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
