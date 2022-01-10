package Лаб6;

import Лаб6.BLL.EmployeeManagement;
import Лаб6.BLL.ReportManagement;
import Лаб6.BLL.TaskManagement;
import Лаб6.Common.EmployeeDTO;
import Лаб6.DAL.LocalRepository;

import java.nio.file.Path;

public class testing {

    public static void main(String[] args) throws ReportManagementSystemException {

        LocalRepository repository = new LocalRepository(Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\tasks.json"),
                Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\employees.json"),
                Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\reports.txt"),
                Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\SprintReport"),
                Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\reportconfig.json"));
        EmployeeDTO employee1 = new EmployeeDTO("Arseniy Shevchenko");
        EmployeeDTO employee2 = new EmployeeDTO("Arseniy Vityazev");
        EmployeeDTO employee3 = new EmployeeDTO("Dmitriy Kukulidi");
        TaskManagement taskManagement = new TaskManagement(repository);
        EmployeeManagement employeeManagement = new EmployeeManagement(repository);
        employeeManagement.addChief(employee1);
        employeeManagement.addEmployee(employee1, employee2);
        employeeManagement.addEmployee(employee1, employee3);


        taskManagement.createTask("main", "tgy", "unresolved", employee2);
        taskManagement.updateTask(0, "status", "resolved", employee2);
        taskManagement.createTask("smth", "very hard", "unresolved", employee2);
        taskManagement.createTask("normal", "", "unresolved", employee3);

        ReportManagement reportManagement = new ReportManagement(employee2, taskManagement, repository);
        reportManagement.createReport();

        ReportManagement reportManagement2 = new ReportManagement(employee3, taskManagement, repository);
        reportManagement2.createReport();

        ReportManagement reportManagement0 = new ReportManagement(employee1, taskManagement, repository);


    }

}
