package city.dao;

import city.domain.PersonRequest;
import city.domain.PersonResponse;
import city.exception.PersonCheckException;

import java.sql.*;

public class PersonCheckDao {
    private static final String SQL_REQUEST =
            """
             SELECT temporal FROM city_register_address_person AS ap
             INNER JOIN city_register_person AS p ON p.person_id = ap.person_id
             INNER JOIN city_register_address AS a ON a.address_id = ap.address_id
             WHERE
             CURRENT_DATE >= ap.start_date
             and (CURRENT_DATE <= ap.end_date or ap.end_date is null)
             and UPPER(p.sur_name) = UPPER(?)
             and UPPER(p.given_name) = UPPER(?)
             and UPPER(p.patronymic) = UPPER(?)
             and p.date_of_birth = ?
             and a.street_code = ?
             and UPPER(a.building) = UPPER(?)""";

    public PersonCheckDao() {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();

        String sql = SQL_REQUEST;
        if (request.getExtension() != null) {
            sql += " and UPPER(a.extension) = UPPER(?) ";
        } else {
            sql += " and extension is null ";
        }

        if (request.getApartment() != null) {
            sql += " and UPPER(a.apartment) = UPPER(?) ";
        } else {
            sql += " and apartment is null ";
        }

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            int count = 1;
            stmt.setString(count++, request.getSurName());
            stmt.setString(count++, request.getGivenName());
            stmt.setString(count++, request.getPatronymic());
            stmt.setDate(count++, java.sql.Date.valueOf(request.getDateOfBirth()));
            stmt.setInt(count++, request.getStreetCode());
            stmt.setString(count++, request.getBuilding());

            if (request.getExtension() != null) {
                stmt.setString(count++, request.getExtension());
            }
            if (request.getApartment() != null) {
                stmt.setString(count, request.getApartment());
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                response.setRegistered(true);
                response.setTemporal(rs.getBoolean("temporal"));
            }
        } catch (SQLException ex) {
            throw new PersonCheckException(ex);
        }

        return response;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost/city_register",
                "postgres",
                "0979727877");
    }


}
