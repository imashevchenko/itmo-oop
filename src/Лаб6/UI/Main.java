package Лаб6.UI;

import Лаб6.BLL.Employee;
import Лаб6.DAL.LocalRepository;
import Лаб6.ReportManagementSystemException;

import java.nio.file.Path;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws ReportManagementSystemException {
        LocalRepository repository = new LocalRepository(Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\tasks.json"),
                Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\employees.json"),
                Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\reports.txt"),
                Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\SprintReport"),
                Path.of("C:\\Users\\ashee\\OneDrive\\Рабочий стол\\Учеба (5 сем)\\ООП\\Lab6Repository\\reportconfig.json"));
        SystemManagement systemManagement = new SystemManagement(repository, new Employee("Arseniy Shevchenko"));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name to authorize:");
        systemManagement.authorize(scanner.nextLine());

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String additional1, additional2, additional3, additional4;
            switch (line) {
                case ("create report"):
                    systemManagement.createReport();
                case ("Get employees with ready reports"):
                    systemManagement.getEmployeesWithReports();
                case ("Get employees without reports"):
                    systemManagement.getEmployeesWithoutReport();
                case ("Create sprint report"):
                    systemManagement.createSprintReport();
                case ("Get all employees"):
                    systemManagement.getAllEmployees();
                case ("Update employee"):
                    System.out.println("Enter the name of the employee");
                    additional1 = scanner.nextLine();
                    System.out.println("Field to change:");
                    additional2 = scanner.nextLine();
                    System.out.println("Enter new value");
                    additional3 = scanner.nextLine();
                    systemManagement.updateEmployee(additional1, additional2, additional3);
                case ("Create employee"):
                    System.out.println("Enter the name");
                    additional1 = scanner.nextLine();
                    System.out.println("Enter chief name");
                    additional2 = scanner.nextLine();
                    systemManagement.createEmployee(additional1, additional2);
                case ("Get all tasks"):
                    systemManagement.getAllTasks();
                case ("Get task by employee"):
                    System.out.println("Enter the name");
                    systemManagement.getTaskByEmployee(scanner.nextLine());
                case ("Get tasks by changes author"):
                    System.out.println("Enter the name");
                    systemManagement.getTasksByChangesAuthor(scanner.nextLine());
                case ("Get task by ID"):
                    System.out.println("Enter task id");
                    systemManagement.getTaskByID(scanner.nextInt());
                case ("Get task by employee's subordinates"):
                    System.out.println("Enter the name");
                    systemManagement.getTasksByEmployeesSubordinates(scanner.nextLine());
                case ("Create task"):
                    System.out.println("Enter the name of the task");
                    additional1 = scanner.nextLine();
                    System.out.println("Enter the comment:");
                    additional2 = scanner.nextLine();
                    System.out.println("Enter task's status");
                    additional3 = scanner.nextLine();
                    System.out.println("Enter employee's name");
                    additional4= scanner.nextLine();
                    systemManagement.createTask(additional1, additional2, additional3, additional4);
                case ("Update task"):
                    System.out.println("Enter id of the task");
                    additional1 = scanner.nextLine();
                    System.out.println("Field to change:");
                    additional2 = scanner.nextLine();
                    System.out.println("Enter new value");
                    additional3 = scanner.nextLine();
                    systemManagement.updateTask(parseInt(additional1), additional2, additional3);
                case ("Get this week tasks"):
                    systemManagement.getThisWeekTasks();
            }
        }
    }
}
