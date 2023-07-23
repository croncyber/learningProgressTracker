package tracker.entity;

import java.util.Objects;


public class Point {
    private Long studentId;
    private Courses course;
    private Long mark;
    boolean notify;

    public Point(Long studentId, Courses course, Long mark) {
        this.studentId = studentId;
        this.course = course;
        this.mark = mark;
        this.notify = false;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(studentId, point.studentId) && course == point.course && Objects.equals(mark, point.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, course, mark);
    }


}

