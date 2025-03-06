package tn.esprit.projetpi.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Data
@Setter

public class Course {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private @Setter Long id;

    private @Setter  String name;

    private @Setter String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "formation_id")
    private @Setter Formation formation;
}