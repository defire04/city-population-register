package city.dao;


import city.domain.PersonRequest;
import city.domain.PersonResponse;
import city.exception.PersonCheckException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PersonCheckDaoTest {

    @Test
    public void checkPerson() throws PersonCheckException {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setSurName("Соколов");
        personRequest.setGivenName("Дмитрий");
        personRequest.setPatronymic("Романович");
        personRequest.setDateOfBirth(LocalDate.of(2000,5,7));
        personRequest.setStreetCode(1);
        personRequest.setBuilding("2");
        personRequest.setExtension("6");
        personRequest.setApartment("333");

        PersonCheckDao dao = new PersonCheckDao();

        PersonResponse ps = dao.checkPerson(personRequest);
        Assert.assertTrue(ps.isRegistered());
        Assert.assertFalse(ps.isTemporal());

    }

    public void checkPerson2() throws PersonCheckException {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setSurName("Соколова");
        personRequest.setGivenName("София");
        personRequest.setPatronymic("Олеговна");
        personRequest.setDateOfBirth(LocalDate.of(2001,9,10));
        personRequest.setStreetCode(2);
        personRequest.setBuilding("271");
        personRequest.setApartment("333");

        PersonCheckDao dao = new PersonCheckDao();

        PersonResponse ps = dao.checkPerson(personRequest);
        Assert.assertTrue(ps.isRegistered());
        Assert.assertFalse(ps.isTemporal());

    }
}