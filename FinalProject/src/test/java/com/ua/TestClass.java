package com.ua;

import com.ua.entity.Doctor;
import com.ua.entity.Nurse;
import com.ua.entity.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestClass {
    Doctor doctor1;
    Doctor doctor2;
    Patient patient1;
    Patient patient2;
    Nurse nurse1;
    Nurse nurse2;

    @Before
    public void initialized() {
        doctor1 = new Doctor(1, "Вася", "Валерьев",
                "Хирургия", "АН1234567", "155676");
        patient1 = new Patient(1, "Одег", "Рубинштейн",
                "АН1234567", "12345");
        nurse1 = new Nurse(1,"АН1234567", "nurse",
                "12345", "Вадим","Васильев");
    }

    @Test
    public void checkCreateDoctorShouldTrue() {
        doctor2 = new Doctor();
        doctor2.setId(3);
        doctor2.setName("Вася");
        doctor2.setSurname("Валерьев");
        doctor2.setDepartment("Хирургия");
        doctor2.setPassport("АН1234567");
        doctor2.setTelephone("155676");
        Assert.assertEquals(doctor1, doctor2);
    }

    @Test
    public void checkCreatePatientShouldFalse() {
        patient2 = new Patient();
        patient2.setId(3);
        patient2.setName("Вася");
        patient2.setSurname("Валерьев");
        patient2.setPassport("АН1234267");
        patient2.setTelephone("155676");
        Assert.assertFalse(patient1.equals(patient2));
    }

    @Test
    public void checkCreateNurseShouldTrue() {
        nurse2 = new Nurse();
        nurse2.setId(3);
        nurse2.setName("Вася");
        nurse2.setSurname("Валерьев");
        nurse2.setPassport("АН1234567");
        nurse2.setTelephone("155676");
        Assert.assertEquals(nurse1, nurse2);
    }
}
