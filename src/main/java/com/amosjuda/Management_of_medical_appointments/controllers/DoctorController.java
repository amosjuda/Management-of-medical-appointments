package com.amosjuda.Management_of_medical_appointments.controllers;

import com.amosjuda.Management_of_medical_appointments.dtos.DoctorRecordDto;
import com.amosjuda.Management_of_medical_appointments.models.Doctor;
import com.amosjuda.Management_of_medical_appointments.repositories.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class DoctorController {
    @Autowired
    DoctorRepository doctorRepository;

    @PostMapping("/doctor")
    public ResponseEntity<Object> saveDoctor(@RequestBody @Valid DoctorRecordDto doctorRecordDto){
        try{
            var doctor = new Doctor();
            BeanUtils.copyProperties(doctorRecordDto, doctor);
            Doctor savedDoctor = doctorRepository.save(doctor);

            EntityModel<Doctor> resource = EntityModel.of(savedDoctor);
            resource.add(linkTo(methodOn(DoctorController.class).getOneDoctor(savedDoctor.getIdDoctor())).withSelfRel());
            resource.add(linkTo(methodOn(DoctorController.class).getALLDoctors()).withRel("all-doctors"));

            return ResponseEntity.status(HttpStatus.CREATED).body(resource.getContent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving Patient: " + e.getMessage());
        }
    }

    @GetMapping("/doctor")
    public ResponseEntity<CollectionModel<EntityModel<Doctor>>> getALLDoctors() {
        try {
            List<Doctor> doctors = doctorRepository.findAll();

            List<EntityModel<Doctor>> doctorResources = doctors.stream()
                    .map(doctor -> EntityModel.of(doctor)
                            .add(linkTo(methodOn(DoctorController.class).getOneDoctor(doctor.getIdDoctor())).withSelfRel()))
                    .toList();

            CollectionModel<EntityModel<Doctor>> collection = CollectionModel.of(doctorResources);
            collection.add(linkTo(methodOn(DoctorController.class).getALLDoctors()).withSelfRel());

            return ResponseEntity.status(HttpStatus.OK).body(collection);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<Object> getOneDoctor(@PathVariable(value="id") UUID id) {
        try{
            Optional<Doctor> doctorO = doctorRepository.findById(id);
            if (doctorO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
            }
            Doctor doctor = doctorO.get();
            EntityModel<Doctor> resource = EntityModel.of(doctor);
            resource.add(linkTo(methodOn(DoctorController.class).getOneDoctor(id)).withSelfRel());
            resource.add(linkTo(methodOn(DoctorController.class).getALLDoctors()).withRel("all-doctors"));
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving doctor.");
        }
    }

    @PutMapping("/doctor/{id}")
    public ResponseEntity<Object> updateDoctor(@PathVariable(value="id") UUID id,
                                               @RequestBody @Valid DoctorRecordDto doctorRecordDto){
        try {
            Optional<Doctor> doctorO = doctorRepository.findById(id);
            if (doctorO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
            }
            Doctor doctor = doctorO.get();
            BeanUtils.copyProperties(doctorRecordDto, doctor);
            Doctor updatedDoctor = doctorRepository.save(doctor);
            EntityModel<Doctor> resource = EntityModel.of(updatedDoctor);
            resource.add(linkTo(methodOn(DoctorController.class).getOneDoctor(id)).withSelfRel());
            resource.add(linkTo(methodOn(DoctorController.class).getALLDoctors()).withRel("all-doctors"));
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating doctor.");
        }
    }

    @DeleteMapping("/doctor/{id}")
    public ResponseEntity<Object> deleteDoctor(@PathVariable(value="id") UUID id) {
        try {
            Optional<Doctor> doctorO = doctorRepository.findById(id);
            if (doctorO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
            }
            doctorRepository.delete(doctorO.get());
            return ResponseEntity.status(HttpStatus.OK).body("Doctor deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting doctor.");
        }
    }
}