package ru.schu.test;

public class Student {
    private Long id;
    private String name;
    private String lastname;
    private String patronymic;
    private String dateOfBirth;
    private String group;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGroup() {
        return group;
    }

    public Student setId(Long val) {
        this.id = val;
        return this;
    }

    public Student setName(String val) {
        this.name = val;
        return this;
    }

    public Student setLastname(String val) {
        this.lastname = val;
        return this;
    }

    public Student setPatronymic(String val) {
        this.patronymic = val;
        return this;
    }

    public Student setDateOfBirth(String val) {
        this.dateOfBirth = val;
        return this;
    }

    public Student setGroup(String val) {
        this.group = val;
        return this;
    }
}
