package ru.schu.test.enums;

public enum StudentColumns {
    ID("id_student"),
    NAME("name"),
    LASTNAME("lastname"),
    PATRONYMIC("patronymic"),
    DATE_OF_BIRTH("date_of_birth"),
    GROUP("group");

    private final String columnName;

    StudentColumns(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return this.columnName;
    }
}
