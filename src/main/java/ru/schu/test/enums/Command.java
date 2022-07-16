package ru.schu.test.enums;

public enum Command {
    SHOW_ALL("Show all students"),
    ADD_NEW("Add new student"),
    DELETE_BY_ID("Delete a student by Id"),
    EXIT("Exit the programm");

    private final String title;

    Command(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
