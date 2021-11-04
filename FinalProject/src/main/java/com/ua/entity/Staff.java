package com.ua.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Staff {

    private int id;
    private String telephone;
    private String passport;
    private String login;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String department;
    private long years;
    private int dayBorn;
    private int monthBorn;
    private int yearBorn;
    private List<CaseRecord> caseRecords =new ArrayList<>();

    public long getYears() {
        return years;
    }

    public void setYears(long years) {
        this.years = years;
    }

    public int getDayBorn() {
        return dayBorn;
    }

    public void setDayBorn(int dayBorn) {
        this.dayBorn = dayBorn;
    }

    public int getMonthBorn() {
        return monthBorn;
    }

    public void setMonthBorn(int monthBorn) {
        this.monthBorn = monthBorn;
    }

    public int getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public List<CaseRecord> getCaseRecords() {
        return caseRecords;
    }

    public void setCaseRecords(List<CaseRecord> patients) {
        this.caseRecords = patients;
    }

    @Override
    public String toString() {
        return "login - " + login
                + "\nid - " + id
                + "\nname - " + name
                + "\nsurname - " + surname
                + "\nrole - " + role
                + "\npassport - " + passport
                + "\ntelephone - " + telephone
                + "\ndepartment - " + department + "\n";
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
}
