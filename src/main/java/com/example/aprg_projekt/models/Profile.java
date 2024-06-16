package com.example.aprg_projekt.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Table("PROFILE_IMAGE")
public class Profile {

    @Id
    private UUID id;
    @Column("FIRSTNAME")
    private String firstName;
    @Column("LASTNAME")
    private String lastName;
    @Column("DATEOFBIRTH")
    private LocalDate dateOfBirth;
    @Column("GENDER")
    private String gender;
    @Column("INTERESTEDIN")
    private List<String> interestedIn;
    @Column("DEGREECOURSE")
    private String degreeCourse;
    @Column("SEMESTER")
    private Integer semester;
    @Column("ABOUTME")
    private String aboutMe;
    @Column("PROFILEPICTURENAME")
    private String profilePicture;
    @Column("IMAGENAMES")
    private String[] imageNames;

    public Profile() {

    }

    public Profile(UUID id,
                   String firstName,
                   String lastName,
                   LocalDate dateOfBirth,
                   String gender,
                   String degreeCourse,
                   Integer semester,
                   String aboutMe) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.degreeCourse = degreeCourse;
        this.semester = semester;
        this.aboutMe = aboutMe;
    }

    public Profile(String firstName,
                   String lastName,
                   LocalDate dateOfBirth,
                   String gender,
                   String degreeCourse,
                   Integer semester,
                   String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.degreeCourse = degreeCourse;
        this.semester = semester;
        this.aboutMe = aboutMe;
    }

    public Profile(UUID id,
                   String firstName,
                   String lastName,
                   LocalDate dateOfBirth,
                   String gender,
                   String degreeCourse,
                   Integer semester,
                   String aboutMe,
                   String profilePicture,
                   String[] imageNames) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.degreeCourse = degreeCourse;
        this.semester = semester;
        this.aboutMe = aboutMe;
        this.profilePicture = profilePicture;
        this.imageNames = imageNames;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getInterestedIn() { return interestedIn; }

    public String getDegreeCourse() {
        return degreeCourse;
    }

    public Integer getSemester() {
        return semester;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getProfilePicture() { return profilePicture; }

    public String[] getImageNames() { return imageNames; }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setInterestedIn(List<String> interestedIn) { this.interestedIn = interestedIn; }

    public void setDegreeCourse(String degreeCourse) {
        this.degreeCourse = degreeCourse;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public void setImageNames(String[] imageNames) { this.imageNames = imageNames; }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", degreeCourse='" + degreeCourse + '\'' +
                ", semester=" + semester +
                ", aboutMe='" + aboutMe + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", imageNames=" + Arrays.toString(imageNames) +
                '}';
    }
}
