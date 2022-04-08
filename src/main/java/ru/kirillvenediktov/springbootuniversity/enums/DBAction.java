package ru.kirillvenediktov.springbootuniversity.enums;

public enum DBAction {
    EXIT(0),
    FIND_GROUPS_WITH_LESS_OR_EQUALS_STUDENT_COUNT(1),
    FIND_STUDENTS_RELATED_TO_COURSE(2),
    CREATE_STUDENT(3),
    DELETE_STUDENT(4),
    ADD_STUDENT_TO_COURSE(5),
    REMOVE_STUDENT_FROM_COURSE(6);

    private final int value;

    DBAction(int value) {
        this.value = value;
    }

    public static DBAction fetchValue(int inputValue) {
        DBAction dbAction = null;
        for (DBAction action : DBAction.values()) {
            if (action.value == inputValue) {
                dbAction = action;
            }
        }
        return dbAction;
    }

}
