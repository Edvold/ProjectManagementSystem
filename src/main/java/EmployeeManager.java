import io.cucumber.java.an.E;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    private static ArrayList<Employee> employees = new ArrayList();
    private static EmployeeManager instance;

    private EmployeeManager(){}

    public static EmployeeManager getInstance() {
        if(instance == null) {
            instance = new EmployeeManager();

            employees.add(new Employee("jama"));
            employees.add(new Employee("mved"));
            employees.add(new Employee("bbje"));
            employees.add(new Employee("kaha"));
            employees.add(new Employee("opja"));
            employees.add(new Employee("mnpo"));
            employees.add(new Employee("ebi"));
            employees.add(new Employee("rol"));
            employees.add(new Employee("lime"));
            employees.add(new Employee("olfe"));
            employees.add(new Employee("pop"));
            employees.add(new Employee("jul"));
            employees.add(new Employee("ture"));
            employees.add(new Employee("hyva"));
            employees.add(new Employee("aaje"));
            employees.add(new Employee("soej"));
            employees.add(new Employee("haha"));
            employees.add(new Employee("hci"));
            employees.add(new Employee("ater"));
            employees.add(new Employee("done"));
        }
        return instance;
    }

    public Employee getEmployeeByName(String name){
        for(Employee employee : employees){
            if(employee.getName().equals(name)){
                return employee;
            }
        }
        return null;
    }

    public ArrayList<Employee> getAvailableEmployees(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<Employee> availableEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.isAvailable(startDate, endDate)) availableEmployees.add(employee);
        }

        return availableEmployees;
    }

}
