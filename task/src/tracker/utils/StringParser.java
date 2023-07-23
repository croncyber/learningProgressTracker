package tracker.utils;

import org.apache.commons.lang3.StringUtils;
import tracker.entity.Courses;
import tracker.entity.Point;
import tracker.entity.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class StringParser {
    // msg validate
    private static final String MSG_ERROR_CRED = "Incorrect credentials.";
    private static final String MSG_ERROR_EMAIL = "Incorrect email.";
    private static final String MSG_ERROR_FIRST_NAME = "Incorrect first name.";
    private static final String MSG_ERROR_LAST_NAME = "Incorrect last name.";
    private static final String MSG_ERROR_FORMAT_POINTS = "Incorrect points format.";
    Validator validator = new Validator();


    public Optional<Student> studentParser(String studentLine) {
        if (StringUtils.isBlank(studentLine)) {
            System.out.println(MSG_ERROR_CRED);
            return Optional.empty();
        }

        String[] studentArray = studentLine.split(" ");

        if (studentArray.length >= 3) {
            if (!validator.validateFirstName(studentArray[0])) {
                System.out.println(MSG_ERROR_FIRST_NAME);
                return Optional.empty();
            }

            if (validator.validateMail(studentArray[studentArray.length - 1])) {
                if (validator.validateFirstLastName(studentLine.substring(
                        0,
                        studentLine.length() - studentArray[studentArray.length - 1].length() - 1)) &&
                        validator.validateLastName(studentArray[studentArray.length - 2])) {
                    return Optional.of(new Student(
                            studentArray[0],
                            studentArray[1],
                            studentArray[studentArray.length - 1]
                    ));

                } else {
                    System.out.println(MSG_ERROR_LAST_NAME);
                    return Optional.empty();
                }
            } else {
                System.out.println(MSG_ERROR_EMAIL);
                return Optional.empty();
            }
        } else {
            System.out.println(MSG_ERROR_CRED);
            return Optional.empty();
        }
    }

    public Optional<List<Point>> pointParser(String pointLine) {
        if (StringUtils.isBlank(pointLine)) {
            System.out.println(MSG_ERROR_FORMAT_POINTS);
            return Optional.empty();
        }

        try {
            List<Long> pointsArray = Arrays.stream(pointLine.split(" ")).map(Long::parseLong).toList();
            if (!pointsArray.stream().allMatch(n -> n >= 0)){
                System.out.println(MSG_ERROR_FORMAT_POINTS);
                return Optional.empty();
            }
                if (pointsArray.size() == 5) {
                    List<Point> points = new ArrayList<>(4);
                    points.add(new Point(pointsArray.get(0), Courses.JAVA, pointsArray.get(Courses.JAVA.getId())));
                    points.add(new Point(pointsArray.get(0), Courses.DSA, pointsArray.get(Courses.DSA.getId())));
                    points.add(new Point(pointsArray.get(0), Courses.DATABASES, pointsArray.get(Courses.DATABASES.getId())));
                    points.add(new Point(pointsArray.get(0), Courses.SPRING, pointsArray.get(Courses.SPRING.getId())));
                    return Optional.of(points);
                } else {
                    System.out.println(MSG_ERROR_FORMAT_POINTS);
                    return Optional.empty();
                }
        } catch (NumberFormatException e) {
            System.out.println(MSG_ERROR_FORMAT_POINTS);
            return Optional.empty();
        }

    }

}
