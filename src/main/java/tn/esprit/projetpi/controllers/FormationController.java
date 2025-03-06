package tn.esprit.projetpi.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetpi.dto.UserInfoDTO;
import tn.esprit.projetpi.entities.Course;
import tn.esprit.projetpi.entities.Formation;
import tn.esprit.projetpi.entities.User;
import tn.esprit.projetpi.services.EmailSender;
import tn.esprit.projetpi.services.FormationService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    private final FormationService formationService;
    private final EmailSender emailSender;
    public FormationController(FormationService formationService, EmailSender emailSender) {
        this.formationService = formationService;
        this.emailSender = emailSender;
    }

    //  Get all formations
    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        return ResponseEntity.ok(formationService.getAllFormations());
    }

    //  Get formation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Formation>> getFormationById(@PathVariable Long id) {
        return ResponseEntity.ok(formationService.getFormationById(id));
    }

    //  Create a new formation
    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        return ResponseEntity.ok(formationService.saveFormation(formation));
    }

    //  Update formation
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        return ResponseEntity.ok(formationService.updateFormation(id, formation));
    }


    //  Delete formation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assignusers/{id}")
    public ResponseEntity<Formation> assignUsersToFormation(@PathVariable Long id, @RequestBody List<User> users) {

        Optional<Formation> formation = formationService.getFormationById(id);

        formation.ifPresent(value -> value.setParticipants(users));
        if (formation.isPresent()) {

            formation.get().setParticipants(users);
            formationService.saveFormation(formation.get());
            for (User user : users) {
                emailSender.sendSimpleMessage(user.getEmail(),"Formation Confirmation", "You have been successfully assigned to this formation.");
            }
            return ResponseEntity.ok(formation.get());


        };
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assigncourses/{id}")
    public ResponseEntity<Formation> assignCoursesToFormation(@PathVariable Long id, @RequestBody List<Course> courses) {
        Optional<Formation> formation = formationService.getFormationById(id);
        if (formation.isPresent()) {
            Formation modifiedFormation = formation.get();
            modifiedFormation.setCoursesList(courses);
            formationService.saveFormation(modifiedFormation);
            return ResponseEntity.ok(modifiedFormation);
        }
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody UserInfoDTO userInfo, HttpServletResponse response) throws DocumentException, IOException {
        // Create PDF Document
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();
        document.add(new Paragraph("User Information"));
        document.add(new Paragraph("Name: " + userInfo.getName()));
        document.add(new Paragraph("Email: " + userInfo.getEmail()));
        document.add(new Paragraph("Address: " + userInfo.getAddress()));
        document.close();

        byte[] pdfBytes = baos.toByteArray();

        // Set HTTP Headers for PDF response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=user_info.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


}