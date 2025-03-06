package tn.esprit.projetpi.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetpi.entities.Formation;
import tn.esprit.projetpi.repos.FormationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    public Formation saveFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }

    public Formation updateFormation(Long id, Formation updatedFormation) {
        return formationRepository.findById(id)
                .map(formation -> {
                    formation.setTitle(updatedFormation.getTitle());
                    formation.setDescription(updatedFormation.getDescription());
                    formation.setImagesUrls(updatedFormation.getImagesUrls());
                    formation.setStartDate(updatedFormation.getStartDate());
                    formation.setEndDate(updatedFormation.getEndDate());
                    formation.setPrice(updatedFormation.getPrice());
                    formation.setParticipants(updatedFormation.getParticipants());
                    formation.setCoursesList(updatedFormation.getCoursesList());
                    return formationRepository.save(formation);
                }).orElseThrow(() -> new RuntimeException("Formation not found"));
    }


}
