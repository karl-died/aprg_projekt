package com.example.aprg_projekt.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class ProfileDTO {

    private UUID id;
    private String name;
    private Integer age;
    private String gender;
    private String degreeCourse;
    private Integer semester;
    private String aboutMe;

    public ProfileDTO() {}

    public ProfileDTO(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getFirstName();
        this.age = Period.between(profile.getDateOfBirth(), LocalDate.now()).getYears();
        this.gender = profile.getGender();
        this.degreeCourse = profile.getDegreeCourse();
        this.semester = profile.getSemester();
        this.aboutMe = profile.getAboutMe();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getDegreeCourse() {
        return degreeCourse;
    }

    public Integer getSemester() {
        return semester;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDegreeCourse(String degreeCourse) {
        this.degreeCourse = degreeCourse;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
