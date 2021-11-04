package com.ua.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Staff implements Serializable {
    private int id;
    private String telephone;
    private String passport;
    private String name;
    private String surname;
    private long years;
    private int dayBorn;
    private int monthBorn;
    private int yearBorn;
    private List<CaseRecord> caseRecords = new ArrayList<>();

    public Patient() {

    }

    public Patient(int id, String name, String surname, String passport, String telephone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.telephone = telephone;
    }

    public long getYears() {
        return years;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
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


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTelephone() {
        return telephone;
    }

    @Override
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String getPassport() {
        return passport;
    }

    @Override
    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public List<CaseRecord> getCaseRecords() {
        return caseRecords;
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
        return "Patient{" +
                "id=" + id +
                ", telephone='" + telephone + '\'' +
                ", passport='" + passport + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", caseRecords=" + caseRecords +
                ", years=" + years +
                ", birthday=" + yearBorn + "/" + monthBorn + "/" + dayBorn +
                '}' + "\n";
    }
}
