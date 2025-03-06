package tn.esprit.projetpi.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetpi.entities.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
}