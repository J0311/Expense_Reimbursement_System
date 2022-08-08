package serviceTests;

import repositories.EmployeeDAO;
import repositories.ReimbursementDAO;
import models.Employee;
import models.Reimbursement;
import org.junit.Assert;
import org.junit.Test;
import services.EmployeeService;
import util.DBConnection;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    DBConnection connectionManagerMock = mock(DBConnection.class);
    EmployeeDAO eDAOMock = mock(EmployeeDAO.class);
    ReimbursementDAO rDAOMock = mock(ReimbursementDAO.class);
    EmployeeService es = new EmployeeService(connectionManagerMock, eDAOMock, rDAOMock);

    @Test
    public void shouldLoginSuccessfullyAndReturnOne() {
        Employee test = new Employee(1, "Bob","Myers","BMyers","password","bm@aol.com");
        when(eDAOMock.verifyEmployee("username","password")).thenReturn(test);
        int actual = es.login("username","password");
        Assert.assertEquals(1, es.currentUserId);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void shouldFailAndReturnZero() {
        when(eDAOMock.verifyEmployee("wrongusername","wrongpassword")).thenReturn(null);
        int actual = es.login("wrongusername","wrongpassword");
        Assert.assertEquals(0,actual);
    }


    @Test
    public void shouldReturnEmployeeInfo() {
        es.currentUserId = 1;
        when(eDAOMock.getById(1)).thenReturn(new Employee(1, "Peter","Quillen","PQuil",
                "password","pquil@gmail.com"));
        Employee actual = es.viewInfo();
        Assert.assertEquals(1,actual.getE_id());
        Assert.assertEquals("Peter",actual.getfName());
        Assert.assertEquals("Quillen",actual.getlName());
        Assert.assertEquals("PQuil",actual.getUsername());
        Assert.assertEquals("password",actual.getPassword());
        Assert.assertEquals("pquil@gmail.com",actual.getEmail());
    }

    @Test
    public void updateInfoTestShouldReturnFalse() {

        es.currentUserId = 1;
        when(eDAOMock.updateInfo(1, "Peter","Quillen","pquil24@gmail.com",
                "PQuil")).thenReturn(true);
        Assert.assertEquals(false, es.updateInfo("Peter","Quillen","password",
                "pquil24@gmail.com"));
    }

    @Test
    public void submitRequestTestShouldReturnIDOf4() {
        es.currentUserId = 1;
        Reimbursement request = new Reimbursement(4, 95.1,1,0,"pending","travel");
        when(rDAOMock.insert(request)).thenReturn(4);
        Assert.assertEquals(4,es.submitRequest(request));
    }

    @Test
    public void viewPendingRequestsTest() {
        es.currentUserId = 1;
        ArrayList<Reimbursement> requests = new ArrayList<>();
        Reimbursement request1 = new Reimbursement(1, 88,1,0,"pending","online");
        Reimbursement request2 = new Reimbursement(2,43.94,1,1,"approved","food");
        Reimbursement request3 = new Reimbursement(3, 75.11,1,0,"pending","travel");
        Reimbursement request4 = new Reimbursement(4, 23.0,1,1,"denied","lodging");
        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        requests.add(request4);
        when(rDAOMock.getByEmployeeId(1)).thenReturn(requests);
        ArrayList<Reimbursement> actual = es.viewPendingRequests();
        Assert.assertEquals(2,actual.size());
        Assert.assertEquals(1,actual.get(0).getR_id());
        Assert.assertEquals(3,actual.get(1).getR_id());
    }

    @Test
    public void viewResolvedRequestsTest() {
        es.currentUserId = 1;
        ArrayList<Reimbursement> requests = new ArrayList<>();
        Reimbursement request1 = new Reimbursement(1, 88,1,0,"pending","online");
        Reimbursement request2 = new Reimbursement(2,43.94,1,1,"approved","food");
        Reimbursement request3 = new Reimbursement(3, 75.11,1,0,"pending","travel");
        Reimbursement request4 = new Reimbursement(4, 23.0,1,1,"denied","lodging");
        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        requests.add(request4);
        when(rDAOMock.getByEmployeeId(1)).thenReturn(requests);
        ArrayList<Reimbursement> actual = es.viewResolvedRequests();
        Assert.assertEquals(2,actual.size());
        Assert.assertEquals(2,actual.get(0).getR_id());
        Assert.assertEquals(4,actual.get(1).getR_id());
    }
}
