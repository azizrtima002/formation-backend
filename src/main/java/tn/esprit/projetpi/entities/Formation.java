package tn.esprit.projetpi.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Data
public class Formation {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ElementCollection
    @CollectionTable(name = "formation_images", joinColumns = @JoinColumn(name = "formation_id"))
    @Column(name = "image_url")
    private List<String> imagesUrls;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Double price;

    // ManyToMany relationship with User (Participants)
    @ManyToMany
    @JoinTable(
            name = "formation_participants",
            joinColumns = @JoinColumn(name = "formation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;

    // OneToMany relationship with Course
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> coursesList;

    // OneToMany relationship with Rating

    public Formation() {}

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}