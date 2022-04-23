package city.web;

import city.dao.PersonCheckDao;
import city.domain.PersonRequest;
import city.domain.PersonResponse;
import city.exception.PersonCheckException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String surname = req.getParameter("surname");
        PersonRequest personRequest = new PersonRequest();
        personRequest.setSurName(surname);
        personRequest.setGivenName("Дмитрий");
        personRequest.setPatronymic("Романович");
        personRequest.setDateOfBirth(LocalDate.of(2000,5,7));
        personRequest.setStreetCode(1);
        personRequest.setBuilding("2");
        personRequest.setExtension("6");
        personRequest.setApartment("333");


        try {
            PersonCheckDao dao = new PersonCheckDao();
            PersonResponse ps = dao.checkPerson(personRequest);

            if(ps.isRegistered()){
                resp.getWriter().println("Found");
            } else{
                resp.getWriter().println("Not register!");
            }
        } catch (PersonCheckException e) {
            e.printStackTrace();
        }
    }
}



