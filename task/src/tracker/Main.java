package tracker;


import tracker.entity.Courses;
import tracker.entity.Point;
import tracker.entity.Student;
import tracker.utils.Statistics;
import tracker.utils.StringParser;

import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static StringParser stringParser = new StringParser();
    private static final String MSG_INTRO = "Learning Progress Tracker";
    // msg action
    private static final String MSG_ENTER_STUDENT = "Enter student credentials or 'back' to return:";
    private static final String MSG_ENTER_POINTS = "Enter an id and points or 'back' to return:";
    private static final String MSG_FIND_POINTS = "Enter an id or 'back' to return:";
    private static final String MSG_STATISTIC = "Type the name of a course to see details or 'back' to quit:";
    private static final String MSG_EXIT = "Enter 'exit' to exit the program.";
    // result notification
    private static final String MSG_FORMAT_TOTAL_ADDED = "Total %d students have been added.\n";
    private static final String MSG_FORMAT_TOTAL_NOTIFIED = "Total %d students have been notified.\n";
    private static final String MSG_FORMAT_FOUND_POINTS = "%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n";
    private static final String MSG_SUCCESSFUL_ADDED_STUDENT = "The student has been added.";
    private static final String MSG_SUCCESSFUL_UPDATE_POINTS = "Points updated.";
    private static final String MSG_BYE = "Bye!";
    private static final String MSG_EMPTY = "No input.";

    private static final String MSG_EMAIL_ALREADY_TAKEN = "This email is already taken.";
    private static final String MSG_NO_FOUND_STUDENT = "No students found.";
    private static final String MSG_UNKNOWN_COURSE = "Unknown course.";
    private static final String MSG_FORMAT_NO_FOUND_STUDENT_ID = "No student is found for id=%s\n";


    // msg commands
    private static final String COMMAND_ADD = "add students";
    private static final String COMMAND_ADD_POINTS = "add points";
    private static final String COMMAND_BACK = "back";
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_STATISTICS = "statistics";
    private static final String COMMAND_NOTIFY = "notify";
    private static final String MSG_STATISTICS_INFO = """
            Most popular: %s
            Least popular: %s
            Highest activity: %s
            Lowest activity: %s
            Easiest course: %s
            Hardest course: %s
            """;

    private static final String MSG_NOTIFY_TO_EMAIl = """
            To: %s
            Re: Your Learning Progress
            Hello, %s! You have accomplished our %s course!
            """;

    public static List<Student> studentList = new ArrayList<>();
    public static Map<Long, Point> studentBlackNotifyList = new HashMap<>();
    public static Map<Long, Point> notifyList = new HashMap<>();
    public static Map<Long, List<Point>> studentPoint = new HashMap<>();
    public static List<Point> pointList = new ArrayList<>();

    public static boolean isModify = false;


    public static void process() {
        System.out.println(MSG_INTRO);
        String command = scanner.nextLine();
        boolean flag = true;
        while (flag) {
            if (command == null || command.isBlank()) {
                System.out.println(MSG_EMPTY);
                command = scanner.nextLine();
            }

            switch (command) {
                case COMMAND_ADD -> {
                    System.out.println(MSG_ENTER_STUDENT);
                    Scanner scanner1 = new Scanner(System.in);
                    String studentLine = scanner1.nextLine();

                    while (command.equalsIgnoreCase(COMMAND_ADD)) {

                        if (studentLine.equalsIgnoreCase(COMMAND_BACK)) {
                            System.out.printf(MSG_FORMAT_TOTAL_ADDED, studentList.size());
                            isModify = true;
                            command = COMMAND_BACK;
                        } else {
                            stringParser.studentParser(studentLine)
                                    .ifPresent(student -> {
                                        if (studentList.stream().noneMatch(e -> e.getMail().equalsIgnoreCase(student.getMail()))) {
                                            student.setStudentId(student.getStudentId() + studentList.size() + 1L);
                                            studentList.add(student);
                                            System.out.println(MSG_SUCCESSFUL_ADDED_STUDENT);
                                        } else {
                                            System.out.println(MSG_EMAIL_ALREADY_TAKEN);
                                        }
                                    });
                            studentLine = scanner1.nextLine();
                        }
                    }
                }
                case COMMAND_LIST -> {
                    if (studentList.isEmpty()) {
                        System.out.println(MSG_NO_FOUND_STUDENT);
                    } else {
                        System.out.println("Students:");
                        studentList.stream()
                                .sorted(Comparator.comparingLong(Student::getStudentId))
                                .forEach(student -> System.out.println(student.getStudentId()));
                    }

                    command = scanner.nextLine();
                }
                case COMMAND_ADD_POINTS -> {
                    System.out.println(MSG_ENTER_POINTS);
                    Scanner scanner = new Scanner(System.in);
                    String outputText = scanner.nextLine();

                    while (command.equalsIgnoreCase(COMMAND_ADD_POINTS)) {
                        if (outputText.equalsIgnoreCase(COMMAND_BACK)) {
                            command = COMMAND_BACK;
                            isModify = true;
                        } else {

                            try {
                                long studentId = Long.parseLong(outputText.split(" ")[0]);
                                if (studentList.stream().noneMatch(student -> student.getStudentId() == studentId)) {
                                    System.out.printf(MSG_FORMAT_NO_FOUND_STUDENT_ID, studentId);
                                }

                            } catch (NumberFormatException e) {
                                System.out.printf(MSG_FORMAT_NO_FOUND_STUDENT_ID, outputText.split(" ")[0]);
                            }

                            stringParser.pointParser(outputText).ifPresent(points -> {
                                        if (studentList.stream()
                                                .anyMatch(student ->
                                                        student.getStudentId().equals(points.get(0).getStudentId()))) {
                                            pointList.addAll(points);
                                            if (studentPoint.containsKey(points.get(0).getStudentId())) {
                                                for (int i = 0; i < 4; i++) {
                                                    studentPoint.get(points.get(0).getStudentId()).get(i).setMark(
                                                            studentPoint.get(points.get(0).getStudentId()).get(i).getMark()
                                                                    + points.get(i).getMark()
                                                    );
                                                    if (studentPoint.get(points.get(0).getStudentId()).get(i).getMark()
                                                            >= Courses.values()[i].getMaxPoints()) {
                                                        notifyList.put(points.get(0).getStudentId() + i,
                                                                points.get(0));
                                                    } else
                                                        studentPoint.get(points.get(0).getStudentId()).get(i).setNotify(false);
                                                }

                                            } else {
                                                studentPoint.put(points.get(0).getStudentId(), points);
                                                for (int i = 0; i < 4; i++) {
                                                    if (studentPoint.get(points.get(0).getStudentId()).get(i).getMark()
                                                            >= Courses.values()[i].getMaxPoints()) {
                                                        notifyList.put(points.get(0).getStudentId() + i,
                                                                points.get(i));
                                                    }
                                                }
                                            }

                                            System.out.println(MSG_SUCCESSFUL_UPDATE_POINTS);
                                        } else {
                                            System.out.printf(MSG_FORMAT_NO_FOUND_STUDENT_ID, points.get(0).getStudentId());
                                        }
                                    }
                            );
                            outputText = scanner.nextLine();
                        }

                    }

                }

                case COMMAND_FIND -> {
                    System.out.println(MSG_FIND_POINTS);
                    Scanner scanner = new Scanner(System.in);
                    String outputText = scanner.nextLine();

                    while (command.equalsIgnoreCase(COMMAND_FIND)) {
                        if (outputText.equalsIgnoreCase(COMMAND_BACK)) {
                            command = COMMAND_BACK;
                        } else {
                            long studentId;
                            try {
                                studentId = Long.parseLong(outputText);
                                if (studentPoint.containsKey(studentId)) {
                                    List<Point> points = studentPoint.get(studentId);
                                    System.out.printf(MSG_FORMAT_FOUND_POINTS, studentId,
                                            points.get(0).getMark(),
                                            points.get(1).getMark(),
                                            points.get(2).getMark(),
                                            points.get(3).getMark());

                                } else {
                                    System.out.printf(MSG_FORMAT_NO_FOUND_STUDENT_ID, studentId);
                                }

                            } catch (NumberFormatException e) {
                                System.out.printf(MSG_FORMAT_NO_FOUND_STUDENT_ID, outputText);
                            }
                            outputText = scanner.nextLine();
                        }
                    }


                }
                case COMMAND_STATISTICS -> {
                    System.out.println(MSG_STATISTIC);
                    Statistics stat = new Statistics(studentPoint, pointList);
                    System.out.printf(MSG_STATISTICS_INFO, stat.findMostPopular(),
                            stat.findLeastPopular(),
                            stat.findHighestActivity(),
                            stat.findLowestActivity(),
                            stat.findEasiestCourse(),
                            stat.findHardestCourse()
                    );
                    while (command.equalsIgnoreCase(COMMAND_STATISTICS)) {
                        Scanner scanner1 = new Scanner(System.in);
                        String outputCourse = scanner1.nextLine();
                        if (outputCourse.equalsIgnoreCase(COMMAND_BACK)) {
                            command = COMMAND_BACK;
                            isModify = true;
                        } else {
                            Arrays.stream(Courses.values())
                                    .filter(courses -> courses.getName().equalsIgnoreCase(outputCourse))
                                    .findAny()
                                    .ifPresentOrElse(course -> {
                                                System.out.println(course.getName());
                                                stat.getCourseStatistics(course);
                                            },
                                            () -> System.out.println(MSG_UNKNOWN_COURSE));

                        }

                    }

                }
                case COMMAND_NOTIFY -> {
                    Set<Long> countNotifyID = new HashSet<>();
                    if (!notifyList.isEmpty()) {
                        notifyList.forEach((key, value) -> {
                            if (!studentBlackNotifyList.containsKey(key)) {
                                Optional<Student> student = studentList.stream()
                                        .filter(s -> s.getStudentId().longValue() == value.getStudentId().longValue()).findFirst();
                                student.ifPresent(s -> {
                                    System.out.printf(MSG_NOTIFY_TO_EMAIl,
                                            s.getMail(),
                                            s.getFirstName() + " " + s.getLastName(),
                                            value.getCourse().getName());
                                    studentBlackNotifyList.putIfAbsent(key, value);
                                    countNotifyID.add(value.getStudentId());
                                });

                            }

                        });
                    }


                    System.out.printf(MSG_FORMAT_TOTAL_NOTIFIED, countNotifyID.size());
                    isModify = true;
                    command = COMMAND_BACK;
                }
                case COMMAND_EXIT -> {
                    System.out.println(MSG_BYE);
                    flag = false;
                }
                case COMMAND_BACK -> {
                    if (!isModify) {
                        System.out.println(MSG_EXIT);
                    }

                    command = scanner.nextLine();
                    isModify = false;
                }
                default -> {
                    System.out.println("Unknown command!");
                    command = scanner.nextLine();
                }
            }
        }
    }

    public static void main(String[] args) {
        process();


    }
}
