package Лаб0.Tests;

import Лаб0.Entities.Group;
import Лаб0.Entities.IsuService;
import Лаб0.Entities.Student;
import Лаб0.Exceptions.IsuException;

import java.util.ArrayList;
import java.util.Arrays;

public class testing {
    private static IsuService isuService;

    public static void main(String[] args) throws IsuException {
        setupIsuService();
        AddStudentToGroup_StudentHasGroupAndGroupContainsStudent();
        ReachMaxStudentPerGroup_ThrowException();
        CreateGroupWithInvalidName_ThrowException();
        TransferStudentToAnotherGroup_GroupChanged();
    }

    public static void setupIsuService() {
        isuService = new IsuService();
        isuService.setMaxGroupSize(30);
    }

    public static void AddStudentToGroup_StudentHasGroupAndGroupContainsStudent() throws IsuException {
        Group m3312 = isuService.AddGroup("M3312");
        Student me = isuService.AddStudent(m3312, "Arseniy Shevchenko");
        Student friend1 = isuService.AddStudent(m3312, "Arseniy Vityazev");
        Student friend2 = isuService.AddStudent(m3312, "Dmitriy Kukulidi");

        if (m3312.getStudentsList().containsAll(Arrays.asList(me, friend1, friend2)))
            System.out.println("Test AddStudentToGroup_StudentHasGroupAndGroupContainsStudent: [OK]");
        else
            System.out.println("Test AddStudentToGroup_StudentHasGroupAndGroupContainsStudent: [FAILED]");
    }

    public static void ReachMaxStudentPerGroup_ThrowException() {
        boolean exceptionFlag = false;
        try {
            Group m3301 = isuService.AddGroup("M3301");
            for (int i = 0; i < 32; i++) {
                isuService.AddStudent(m3301, "NoName");
            }
        } catch (IsuException e) {
            exceptionFlag = true;
        }
        if (exceptionFlag)
            System.out.println("Test ReachMaxStudentPerGroup_ThrowException: [OK]");
        else
            System.out.println("Test ReachMaxStudentPerGroup_ThrowException: [FAILED]");
    }


    public static void CreateGroupWithInvalidName_ThrowException() {
        boolean exceptionFlag = false;
        try {
            Group invalid = isuService.AddGroup("M3333345");
        } catch (IsuException e) {
            exceptionFlag = true;
        }
        if (exceptionFlag)
            System.out.println("Test CreateGroupWithInvalidName_ThrowException: [OK]");
        else
            System.out.println("Test CreateGroupWithInvalidName_ThrowException: [FAILED]");
    }

    public static void TransferStudentToAnotherGroup_GroupChanged() throws IsuException {
        Group oldGroup = isuService.AddGroup("M3305");
        Group newGroup = isuService.AddGroup("M3306");
        Student student =isuService.AddStudent(oldGroup,"Nikolay Fedotenko");
        isuService.ChangeStudentGroup(student, newGroup);
        if (newGroup.isInGroup(student) && !oldGroup.isInGroup(student))
            System.out.println("Test TransferStudentToAnotherGroup_GroupChanged(): [OK]");
        else
            System.out.println("Test TransferStudentToAnotherGroup_GroupChanged: [FAILED]");
    }
}
