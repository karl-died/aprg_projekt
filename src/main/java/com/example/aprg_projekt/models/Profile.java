package com.example.aprg_projekt.models;


import java.util.UUID;

public class Profile {

    private UUID id;
    private String displayName;
    private Integer age;
    private String degreeCourse;
    private Integer semester;
    private String aboutMe;

    public Profile() {

    }

    public Profile(UUID id, String displayName, Integer age, String degreeCourse, Integer semester, String aboutMe) {
        this.id = id;
        this.degreeCourse = degreeCourse;
        this.semester = semester;
        this.aboutMe = aboutMe;
    }

    public Profile(String degreeCourse,
                   Integer semester,
                   String aboutMe) {
        this.degreeCourse = degreeCourse;
        this.semester = semester;
        this.aboutMe = aboutMe;
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getAge() {
        return age;
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

    public void setDegreeCourse(String degreeCourse) {
        this.degreeCourse = degreeCourse;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", age=" + age +
                ", degreeCourse='" + degreeCourse + '\'' +
                ", semester=" + semester +
                ", aboutMe='" + aboutMe + '\'' +
                '}';
    }
}
