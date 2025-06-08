package ru.bmstu.model;

public class Student {
    private String firstName;
    private String lastName;
    private int tokens;

    public Student(String firstName, String lastName, int tokens) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tokens = tokens;
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

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
