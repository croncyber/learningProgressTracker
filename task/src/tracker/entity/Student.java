package tracker.entity;

import java.util.Objects;


public class Student {

    private Long studentId;
    private String firstName;
    private String lastName;
    private String mail;



    public Student(String firstName, String lastName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.studentId = 9999L;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(mail, student.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, mail);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
