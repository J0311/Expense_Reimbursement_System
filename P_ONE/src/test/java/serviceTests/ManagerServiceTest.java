package serviceTests;

import repositories.EmployeeDAO;
import repositories.ManagerDAO;
import repositories.ReimbursementDAO;
import models.Employee;
import models.Manager;
import models.Reimbursement;
import org.junit.Assert;
import org.junit.Test;
import services.ManagerService;
import util.DBConnection;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManagerServiceTest {
    DBConnection dbManagerMock = mock(DBConnection.class);
    EmployeeDAO eDAOMock = mock(EmployeeDAO.class);
    ManagerDAO mDAOMock = mock(ManagerDAO.class);
    ReimbursementDAO rDAOMock = mock(ReimbursementDAO.class);
    ManagerService ms = new ManagerService(eDAOMock, mDAOMock, rDAOMock, dbManagerMock);

    @Test
    public void shouldLoginSuccessfullyAndReturnOne() {
        Manager test = new Manager(1, "Josh", "Turner", "username", "password",
                "jt@gmail.com");
        when(mDAOMock.verifyManager("username", "password")).thenReturn(test);
        int actual = ms.login("username", "password");
        Assert.assertEquals(1, ms.currentUserId);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void shouldFailedAndReturnZero() {
        when(mDAOMock.verifyManager("wrongusername", "wrongpassword")).thenReturn(null);
        int actual = ms.login("wrongusername", "wrongpassword");
        Assert.assertEquals(0, actual);
    }

    @Test
    public void returnAllEmployeesTest() {
        ArrayList<Employee> employees = new ArrayList<>();
        Employee e1 = new Employee(1, "Josh", "Taylor", "JT", "password", "email");
        Employee e2 = new Employee(2, "Alex", "Cruz", "AC", "password", "email");
        employees.add(e1);
        employees.add(e2);
        when(eDAOMock.getAll()).thenReturn(employees);
        ArrayList<Employee> actual = ms.viewEmployees();
        Assert.assertEquals(2, actual.size());
        Assert.assertEquals(1, actual.get(0).getE_id());
        Assert.assertEquals(2, actual.get(1).getE_id());
    }

    @Test
    public void returnAllRequestsByEmployeeOne() {
        ArrayList<Reimbursement> requests = new ArrayList<>();
        Reimbursement request1 = new Reimbursement(1, 100, 1, 0, "pending", "ticket");
        Reimbursement request2 = new Reimbursement(2, 80, 1, 1, "approved", "travel");
        Reimbursement request3 = new Reimbursement(3, 90, 1, 2, "pending", "food");
        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        when(rDAOMock.getByEmployeeId(1)).thenReturn(requests);
        ArrayList<Reimbursement> actual = ms.viewEmployeeRequests(1);
        Assert.assertEquals(3, actual.size());
        Assert.assertEquals(1, actual.get(0).getE_id());
        Assert.assertEquals(1, actual.get(1).getE_id());
        Assert.assertEquals(1, actual.get(2).getE_id());

    }

    @Test
    public void returnAllPendingRequests() {
        ArrayList<Reimbursement> requests = new ArrayList<>();
        Reimbursement request1 = new Reimbursement(1, 100,1,0,"pending","seminar");
        Reimbursement request2 = new Reimbursement(2,470,1,1,"pending","flight");
        requests.add(request1);
        requests.add(request2);
        when(rDAOMock.getAllPending()).thenReturn(requests);
        ArrayList<Reimbursement> actual = ms.getAllPending();
        Assert.assertEquals(2,actual.size());
        Assert.assertEquals("pending",actual.get(0).getStatus());
        Assert.assertEquals("pending",actual.get(1).getStatus());
    }

    @Test
    public void returnAllResolvedRequests() {
        ArrayList<Reimbursement> requests = new ArrayList<>();
        Reimbursement request1 = new Reimbursement(1, 400,1,0,"resolved","biking");
        Reimbursement request2 = new Reimbursement(2,999,1,1,"resolved","seminar");
        requests.add(request1);
        requests.add(request2);
        when(rDAOMock.getAllResolved()).thenReturn(requests);
        ArrayList<Reimbursement> actual = ms.getAllResolved();
        Assert.assertEquals(2,actual.size());
        Assert.assertEquals("resolved",actual.get(0).getStatus());
        Assert.assertEquals("resolved",actual.get(1).getStatus());
    }


    @Test
    public void shouldReturnTrueWhenManagerApprovesReimbursementRequest() {
        ms.currentUserId = 1;
        when(rDAOMock.resolve(1, 1, "approved")).thenReturn(true);
        Assert.assertEquals(true, ms.resolve(1, "approved"));
    }
}