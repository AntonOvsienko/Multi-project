package com.ua.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Doctor extends Staff implements Serializable {
    private int id;
    private String passport;
    private String login;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String telephone;
    private String department;
    private List<CaseRecord> caseRecords = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(int id, String name, String surname, String department, String passport, String telephone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.passport = passport;
        this.telephone = telephone;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getPassport() {
        return passport;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getTelephone() {
        return telephone;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public List<CaseRecord> getCaseRecords() {
        return caseRecords;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public void setCaseRecords(List<CaseRecord> caseRecords) {
        this.caseRecords = caseRecords;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Staff staff = (Staff) obj;
        if (passport.equals(staff.getPassport())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", department='" + department + '\'' +
                ", caseRecords=" + caseRecords +
                '}' + "\n";
    }
}
