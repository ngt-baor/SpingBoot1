package com.example.buoi1.controller;

import com.example.buoi1.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    @GetMapping
    public List<Student> getAll() {
        return students;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Integer id, @RequestBody Student input) {
        for (Student student : students) {
            if (id.equals(student.getId())) {
                student.setName(input.getName());
                student.setAddress(input.getAddress());
                student.setGender(input.getGender());
                student.setStatus(input.getStatus());
                return ResponseEntity.ok(student);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (students.removeIf(student -> id.equals(student.getId()))) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
