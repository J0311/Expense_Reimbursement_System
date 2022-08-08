package repoTests;

import models.Employee;
import models.Manager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.Driver;
import repositories.ManagerDAO;
import util.DBConnection;

public class ManagerDAOTest {
    ManagerDAO mDAO;
    DBConnection db;
    @Before
    public void initBefore() {
        db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
                "J0311", "BestPasswordEver", new Driver());
        mDAO = new ManagerDAO(db);
    }

    @Test
    public void getByIDMethodShouldReturnTheFourthManager() {
        Manager result = mDAO.getById(4);
        Assert.assertEquals(4, result.getM_id());
        Assert.assertEquals("Mike", result.getfName());
        Assert.assertEquals("Jones", result.getlName());
        Assert.assertEquals("WhoIsMike", result.getUsername());
        Assert.assertEquals("password", result.getPassword());
    }

    @Test
    public void shouldLoginAsSecondManager() {
        Manager result = mDAO.verifyManager("SOG","password");
        Assert.assertEquals(2, result.getM_id());
        Assert.assertEquals("Andre", result.getfName());
        Assert.assertEquals("Ward", result.getlName());
        Assert.assertEquals("SOG", result.getUsername());
        Assert.assertEquals("password", result.getPassword());
    }

    @Test
    public void shouldReturnNullAsLoginFailure() {
        Manager result = mDAO.verifyManager("wrongusername", "wrongpassword");
        Assert.assertEquals(null,result);
    }
}
