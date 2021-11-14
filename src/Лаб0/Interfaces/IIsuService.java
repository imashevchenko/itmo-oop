package Лаб0.Interfaces;

import Лаб0.Entities.CourseNumber;
import Лаб0.Entities.Group;
import Лаб0.Entities.Student;
import Лаб0.Exceptions.IsuException;

import java.util.List;

public interface IIsuService
{
    Group AddGroup(String name) throws IsuException;
    Student AddStudent(Group group, String name) throws IsuException;

    Student GetStudent(int id) throws IsuException;
    Student FindStudent(String name) throws IsuException;
    List<Student> FindStudents(String groupName) throws IsuException;
    List<Student> FindStudents(CourseNumber courseNumber) throws IsuException;

    Group FindGroup(String groupName) throws IsuException;
    List<Group> FindGroups(CourseNumber courseNumber) throws IsuException;

    void ChangeStudentGroup(Student student, Group newGroup) throws IsuException;
}
