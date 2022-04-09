package ua.epam.payments.payments.model.entity;

import java.util.Arrays;

public enum Role {
    ADMINISTRATOR(1), CLIENT(2);
    private long id;

    Role(long id) {
        this.id = id;
    }

    String getRole() {
        System.out.println(Arrays.toString(values()));
        return "";
    }


}
