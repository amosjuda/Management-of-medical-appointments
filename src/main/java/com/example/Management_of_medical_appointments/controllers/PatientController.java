package com.example.Management_of_medical_appointments.controllers;

import com.example.Management_of_medical_appointments.dtos.PatientRecordDto;
import com.example.Management_of_medical_appointments.models.Patient;
import com.example.Management_of_medical_appointments.repositories.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @PostMapping("/patient")
    public ResponseEntity<Object> savePatient(@RequestBody @Valid PatientRecordDto patientRecordDto){
        try {
            var patient = new Patient();
            BeanUtils.copyProperties(patientRecordDto, patient);
            Patient savedPatient = patientRepository.save(patient);

            EntityModel<Patient> resource = EntityModel.of(savedPatient);
            resource.add(linkTo(methodOn(PatientController.class).getOnePatient(savedPatient.getIdPatient())).withSelfRel());
            resource.add(linkTo(methodOn(PatientController.class).getALLPatients()).withRel("all-patients"));

            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving patient: " + e.getMessage());
        }

    }

    @GetMapping("/patient")
    public ResponseEntity<CollectionModel<EntityModel<Patient>>> getALLPatients() {
        try {
            List<Patient> patients = patientRepository.findAll();

            List<EntityModel<Patient>> patientResources = patients.stream()
                    .map(patient -> EntityModel.of(patient)
                            .add(linkTo(methodOn(PatientController.class).getOnePatient(patient.getIdPatient())).withSelfRel()))
                    .toList();

            CollectionModel<EntityModel<Patient>> collection = CollectionModel.of(patientResources);
            collection.add(linkTo(methodOn(PatientController.class).getALLPatients()).withSelfRel());

            return ResponseEntity.status(HttpStatus.OK).body(collection);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Object> getOnePatient(@PathVariable(value="id") UUID id) {
        try {
            Optional<Patient> patientO = patientRepository.findById(id);
            if (patientO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
            }

            var patient = patientO.get();
            EntityModel<Patient> resource = EntityModel.of(patient);
            resource.add(linkTo(methodOn(PatientController.class).getOnePatient(id)).withSelfRel());
            resource.add(linkTo(methodOn(PatientController.class).getALLPatients()).withRel("all-patients"));

            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching patient: " + e.getMessage());
        }
    }

    @PutMapping("/patient/{id}")
    public ResponseEntity<Object> updatePatient(@PathVariable(value="id") UUID id,
                                               @RequestBody @Valid PatientRecordDto patientRecordDto){
        try {
            Optional<Patient> patientO = patientRepository.findById(id);
            if (patientO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
            }
            var patient = patientO.get();
            BeanUtils.copyProperties(patientRecordDto, patient);
            Patient updatedPatient = patientRepository.save(patient);

            EntityModel<Patient> resource = EntityModel.of(updatedPatient);
            resource.add(linkTo(methodOn(PatientController.class).getOnePatient(updatedPatient.getIdPatient())).withSelfRel());
            resource.add(linkTo(methodOn(PatientController.class).getALLPatients()).withRel("all-patients"));

            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating patient: " + e.getMessage());
        }
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity<Object> deletePatient(@PathVariable(value="id") UUID id) {
        try {
            Optional<Patient> patientO = patientRepository.findById(id);
            if (patientO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
            }
            patientRepository.delete(patientO.get());

            return ResponseEntity.ok("Patient deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting patient: " + e.getMessage());
        }
    }
}