package repoTests;

import models.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.Driver;
import repositories.EmployeeDAO;
import util.DBConnection;
import java.util.ArrayList;


public class EmployeeDAOTest
{

    EmployeeDAO eDAO;
    DBConnection db;
    @Before
    public void initBefore() {
        db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
                "J0311", "BestPasswordEver", new Driver());
        eDAO = new EmployeeDAO(db);
    }
    @Test
    public void employeeDAOShouldReturnAllDataForFirstThreeEmployeesInEmployeeTable() {
        ArrayList<Employee> result = eDAO.getAll();
        Assert.assertEquals(1, result.get(0).getE_id());
        Assert.assertEquals(2, result.get(1).getE_id());
        Assert.assertEquals(4, result.get(2).getE_id());

        Assert.assertEquals("Mark", result.get(0).getfName());
        Assert.assertEquals("Todd", result.get(1).getfName());
        Assert.assertEquals("Tim", result.get(2).getfName());

        Assert.assertEquals("Murphy", result.get(0).getlName());
        Assert.assertEquals("Clark", result.get(1).getlName());
        Assert.assertEquals("Smith", result.get(2).getlName());

        Assert.assertEquals("MandM", result.get(0).getUsername());
        Assert.assertEquals("TClark", result.get(1).getUsername());
        Assert.assertEquals("ESmith", result.get(2).getUsername());

        Assert.assertEquals("password", result.get(0).getPassword());
        Assert.assertEquals("password", result.get(1).getPassword());
        Assert.assertEquals("password", result.get(2).getPassword());

        Assert.assertEquals("murphy@aol.com", result.get(0).getEmail());
        Assert.assertEquals("todd@gmail.com", result.get(1).getEmail());
        Assert.assertEquals("emily@yahoo.com", result.get(2).getEmail());
    }

    @Test
    public void shouldReturnFourthEmployee() {
        Employee result = eDAO.getById(4);
        Assert.assertEquals(4, result.getE_id());
        Assert.assertEquals("Tim", result.getfName());
        Assert.assertEquals("Beaver", result.getlName());
        Assert.assertEquals("TBtheGreat", result.getUsername());
        Assert.assertEquals("password", result.getPassword());
        Assert.assertEquals("tim@aol.com",result.getEmail());
    }

    @Test
    public void shouldUpdateInfoOf() {
        eDAO.updateInfo(3, "Floyd", "Mayweather", "moneymay@gmail.com", "MoneyMay");
        Employee result = eDAO.getById(3);
        Assert.assertEquals(3, result.getE_id());
        Assert.assertEquals("Floyd", result.getfName());
        Assert.assertEquals("Mayweather", result.getlName());
        Assert.assertEquals("moneymay@gmail.com", result.getEmail());
        Assert.assertEquals("MoneyMay",result.getUsername());
       // eDAO.updateInfo(3,"Floyd","Mayweather","password",27);
    }

    @Test
    public void shouldLoginAsThirdEmployee() {
        Employee result = eDAO.verifyEmployee("marywilliams","password");
        Assert.assertEquals(3, result.getE_id());
        Assert.assertEquals("Mary", result.getfName());
        Assert.assertEquals("Williams", result.getlName());
        Assert.assertEquals("marywilliams", result.getUsername());
        Assert.assertEquals("password", result.getPassword());
        Assert.assertEquals(27,result.getEmail());
    }

    @Test
    public void shouldReturnNullAsLoginFailure() {
        Employee result = eDAO.verifyEmployee("wrongusername", "wrongpassword");
        Assert.assertEquals(null,result);
    }
}
