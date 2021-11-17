package Лаб2.Tests;

import Лаб0.Entities.Group;
import Лаб0.Entities.IsuService;
import Лаб0.Entities.Student;
import Лаб0.Exceptions.IsuException;
import Лаб2.Entities.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class testing {
    public static IsuServiceExtra isuService;

    public static void main(String[] args) throws IsuException {
        setupIsuService();
        EnrollmentTesting_NoException();
        EnrollmentTesting_Exception();
        CancelEnrollmentTesting();
        GettersTesting();
    }

    public static void setupIsuService() throws IsuException {
        isuService = new IsuServiceExtra();
        isuService.setMaxGroupSize(30);

        GroupExtra m3312 = isuService.AddGroup("M3312");
        StudentExtra me = isuService.AddStudent(m3312, "Arseniy Shevchenko");
        StudentExtra friend1 = isuService.AddStudent(m3312, "Arseniy Vityazev");
        StudentExtra friend2 = isuService.AddStudent(m3312, "Dmitriy Kukulidi");

        m3312.addLesson(new Lesson("Mayatin", 100,
                new GregorianCalendar(2021, Calendar.SEPTEMBER, 1, 9, 0),
                new GregorianCalendar(2021, Calendar.SEPTEMBER, 1, 10, 30)));
        m3312.addLesson(new Lesson("Khlopotov", 239,
                new GregorianCalendar(2021, Calendar.SEPTEMBER, 1, 10, 50),
                new GregorianCalendar(2021, Calendar.SEPTEMBER, 1, 12, 20)));

        OGNP ognp = isuService.addOGNP("N");
        GroupOGNP groupOGNP = isuService.addGroupOGNP("N");
        groupOGNP.addLesson(new Lesson("Petrov", 320,
                new GregorianCalendar(2021, Calendar.SEPTEMBER, 1, 13, 10),
                new GregorianCalendar(2021, Calendar.SEPTEMBER, 1, 14, 40)));
    }

    public static void EnrollmentTesting_NoException() throws IsuException {
        StudentExtra me = isuService.FindStudent("Arseniy Shevchenko");
        isuService.enrollStudent(me, "N");
        if (me.getOgnps().get(0).getMegafaculty().equals("N"))
            System.out.println("EnrollmentTesting_NoException : [OK]");
        else
            System.out.println("EnrollmentTesting_NoException : [FAILED]");
    }

    public static void EnrollmentTesting_Exception() throws IsuException {
        StudentExtra friend1 = isuService.FindStudent("Arseniy Vityazev");

        GroupOGNP groupOGNP = isuService.FindOGNPGroup("N").get(0);
        groupOGNP.addLesson(new Lesson("Petrov", 320,
                new GregorianCalendar(2021, Calendar.SEPTEMBER, 1, 9, 10),
                new GregorianCalendar(2021, Calendar.SEPTEMBER, 1, 10, 40)));

        try {
            isuService.enrollStudent(friend1, "N");
            System.out.println("EnrollmentTesting_Exception : [FAILED]");
        } catch (IsuException e) {
            System.out.println("EnrollmentTesting_Exception : [OK]");
        }
    }

    public static void CancelEnrollmentTesting() throws IsuException {
        StudentExtra me = isuService.FindStudent("Arseniy Shevchenko");
        isuService.cancelOgnpEnrollment(me, "N");

        if (me.getOgnps().isEmpty())
            System.out.println("CancelEnrollmentTest : [OK]");
        else
            System.out.println("CancelEnrollmentTest : [FAILED]");
    }

    public static void GettersTesting() throws IsuException {
        OGNP ognp = isuService.addOGNP("K");
        GroupOGNP groupOGNP = isuService.addGroupOGNP("K");

        GroupExtra m3301 = isuService.AddGroup("M3301");
        StudentExtra friend0 = isuService.AddStudent(m3301, "Papikyan");
        StudentExtra friend1 = isuService.AddStudent(m3301, "Yakimov");
        StudentExtra friend2 = isuService.AddStudent(m3301, "Rozhnovskiy");

        isuService.enrollStudent(friend0, "N");
        isuService.enrollStudent(friend0, "K");
        isuService.enrollStudent(friend1, "N");
        isuService.enrollStudent(friend1, "K");

        isuService.studentsOnCourse(groupOGNP).forEach(studentExtra -> System.out.println(studentExtra.getName()));
        System.out.println("----------");
        isuService.notEnrolledStudents(m3301).forEach(studentExtra -> System.out.println(studentExtra.getName()));
    }

}
