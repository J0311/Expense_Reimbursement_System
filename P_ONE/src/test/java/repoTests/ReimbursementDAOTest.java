package repoTests;

import models.Reimbursement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.Driver;
import repositories.ReimbursementDAO;
import util.DBConnection;

import java.util.ArrayList;

public class ReimbursementDAOTest {
    ReimbursementDAO rDAO;
    DBConnection db;
    @Before
    public void initBefore() {
      db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
        "J0311", "BestPasswordEver", new Driver());
      rDAO = new ReimbursementDAO(db);
    }

    @Test
    public void shouldReturnAllRequests(){
        ArrayList<Reimbursement> result = rDAO.getAll();

        Assert.assertEquals(1, result.get(0).getR_id());
        Assert.assertEquals(2, result.get(1).getR_id());
        Assert.assertEquals(3, result.get(2).getR_id());
        Assert.assertEquals(4, result.get(3).getR_id());

        Assert.assertEquals(48, result.get(0).getAmount() ,0);
        Assert.assertEquals(88, result.get(1).getAmount(), 0);
        Assert.assertEquals(60.7, result.get(2).getAmount(), 0);
        Assert.assertEquals(73.01, result.get(3).getAmount(), 0);

        Assert.assertEquals(1, result.get(0).getE_id());
        Assert.assertEquals(2, result.get(1).getE_id());
        Assert.assertEquals(4, result.get(2).getE_id());
        Assert.assertEquals(3, result.get(3).getE_id());

        Assert.assertEquals(3, result.get(0).getM_id());
        Assert.assertEquals(2, result.get(1).getM_id());
        Assert.assertEquals(1, result.get(2).getM_id());
        Assert.assertEquals(4, result.get(3).getM_id());

        Assert.assertEquals("pending", result.get(0).getStatus());
        Assert.assertEquals("pending", result.get(1).getStatus());
        Assert.assertEquals("denied", result.get(2).getStatus());
        Assert.assertEquals("approved", result.get(3).getStatus());

        Assert.assertEquals("parking", result.get(0).getType());
        Assert.assertEquals("travel", result.get(1).getType());
        Assert.assertEquals("food", result.get(2).getType());
        Assert.assertEquals("lodging", result.get(3).getType());
    }

    @Test
    public void shouldReturnAllPendingRequests() {
        ArrayList<Reimbursement> result = rDAO.getAllPending();
        Assert.assertEquals(1, result.get(0).getR_id());
        Assert.assertEquals(48, result.get(0).getAmount() ,0);
        Assert.assertEquals(1, result.get(0).getE_id());
        Assert.assertEquals(3, result.get(0).getM_id());
        Assert.assertEquals("pending", result.get(0).getStatus());
        Assert.assertEquals("parking", result.get(0).getType());

        Assert.assertEquals(2, result.get(1).getR_id());
        Assert.assertEquals(88, result.get(1).getAmount() ,0);
        Assert.assertEquals(2, result.get(1).getE_id());
        Assert.assertEquals(2, result.get(1).getM_id());
        Assert.assertEquals("pending", result.get(1).getStatus());
        Assert.assertEquals("travel", result.get(1).getType());
    }

    @Test
    public void shouldReturnResolvedRequests() {
        ArrayList<Reimbursement> result = rDAO.getAllResolved();

        Assert.assertEquals(3, result.get(0).getR_id());
        Assert.assertEquals(4, result.get(1).getR_id());

        Assert.assertEquals(60.7, result.get(0).getAmount(), 0);
        Assert.assertEquals(73.01, result.get(1).getAmount(), 0);

        Assert.assertEquals(4, result.get(0).getE_id());
        Assert.assertEquals(3, result.get(1).getE_id());

        Assert.assertEquals(1, result.get(0).getM_id());
        Assert.assertEquals(4, result.get(1).getM_id());

        Assert.assertEquals("denied", result.get(0).getStatus());
        Assert.assertEquals("approved", result.get(1).getStatus());

        Assert.assertEquals("food", result.get(0).getType());
        Assert.assertEquals("lodging", result.get(1).getType());
    }

    @Test
    public void shouldReturnThirdReimbursementRequest() {
        Reimbursement result = rDAO.getById(3);
        Assert.assertEquals(3, result.getR_id());
        Assert.assertEquals(60.7, result.getAmount(),0);
        Assert.assertEquals(4, result.getE_id());
        Assert.assertEquals(1, result.getM_id());
        Assert.assertEquals("denied", result.getStatus());
        Assert.assertEquals("food", result.getType());
    }

    @Test
    public void shouldReturnRequestsFromFourthEmployee() {
        ArrayList<Reimbursement> result = rDAO.getByEmployeeId(4);
        Assert.assertEquals(3, result.get(0).getR_id());
        Assert.assertEquals(60.7, result.get(0).getAmount(),0);
        Assert.assertEquals(4, result.get(0).getE_id());
        Assert.assertEquals(1, result.get(0).getM_id());
        Assert.assertEquals("denied", result.get(0).getStatus());
        Assert.assertEquals("food", result.get(0).getType());
    }

    @Test
    public void shouldInsertNewReimbursementRequest() {
        Reimbursement request = new Reimbursement(5, 24, 2, 0, "pending", "education");
        rDAO.insert(request);
        Reimbursement result = rDAO.getById(5);
        Assert.assertEquals(5, result.getR_id());
        Assert.assertEquals(24, result.getAmount(),0);
        Assert.assertEquals(2, result.getE_id());
        Assert.assertEquals(0, result.getM_id());
        Assert.assertEquals("pending", result.getStatus());
        Assert.assertEquals("education", result.getType());

    }

    @Test
    public void shouldReturnFifthRequestApprovedByOurThirdManager() {
        rDAO.resolve(5,3,"approved");
        Reimbursement result = rDAO.getById(5);
        Assert.assertEquals(5, result.getR_id());
        Assert.assertEquals(24, result.getAmount(),0);
        Assert.assertEquals(2, result.getE_id());
        Assert.assertEquals(3, result.getM_id());
        Assert.assertEquals("approved", result.getStatus());
        Assert.assertEquals("education", result.getType());
    }
}