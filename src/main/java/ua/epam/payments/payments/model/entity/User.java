package ua.epam.payments.payments.model.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 123L;

    private long id;
    private String firstName;
    private String lastName;
    private String surname;
    private String email;
    private String password;
    private boolean blocked;
    private long rolesId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public long getRolesId() {
        return rolesId;
    }

    public void setRolesId(long rolesId) {
        this.rolesId = rolesId;
    }

    public static class Builder {
        private User newUser;

        public Builder() {
            newUser = new User();
        }

        public Builder withFirstName(String firstName){
            newUser.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            newUser.lastName = lastName;
            return this;
        }

        public Builder withSurname(String surname){
            newUser.surname = surname;
            return this;
        }

        public Builder withEmail(String email){
            newUser.email = email;
            return this;
        }

        public Builder withPassword(String password){
            newUser.password = password;
            return this;
        }

        public Builder withBlocked(boolean blocked){
            newUser.blocked = blocked;
            return this;
        }

        public Builder withRolesId(long rolesId){
            newUser.rolesId = rolesId;
            return this;
        }

        public User build(){
            return newUser;
        }

    }
}
