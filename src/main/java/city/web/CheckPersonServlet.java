package city.web;
import city.dao.PoolConnectionBuilder;
import org.slf4j.Logger;
import java.io.IOException;
import java.time.LocalDate;
import org.slf4j.LoggerFactory;
import city.dao.PersonCheckDao;
import city.domain.PersonRequest;
import city.domain.PersonResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import city.exception.PersonCheckException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {



    private static final Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);
    private PersonCheckDao dao;


    @Override
    public void init() throws ServletException {
        logger.info("SERVLET is created");
        dao = new PersonCheckDao();
        dao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

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



