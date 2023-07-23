package tracker.utils;

import org.apache.commons.lang3.StringUtils;
import tracker.entity.Courses;
import tracker.entity.Point;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics {
    private static final String notApplicable = "n/a";

    private final Map<Long, List<Point>> studentPoint;
    private final List<Point> points;


    public Statistics(Map<Long, List<Point>> studentPoint, List<Point> points) {
        this.studentPoint = studentPoint;
        this.points = points;
    }


    public String findMostPopular() {
        if (points.isEmpty()) return notApplicable;

        StringBuilder stringBuilder = new StringBuilder();

        Map<Courses, Integer> courseToStudentCount = points.stream()
                .filter(point -> point.getMark() > 0)
                .collect(Collectors.groupingBy(Point::getCourse,
                        Collectors.mapping(Point::getStudentId,
                                Collectors.collectingAndThen(Collectors.toSet(), Set::size))));

        Integer mostEnrolledActiveCourse = courseToStudentCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue).orElse(null);


        List<Map.Entry<Courses, Integer>> entryList = new ArrayList<>(courseToStudentCount.entrySet());
        entryList.sort(Comparator.comparingInt(entry -> entry.getKey().getId()));
        Map<Courses, Integer> sortedMap = entryList.stream().collect
                (Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));


        sortedMap.entrySet().stream()
                .toList().forEach(m -> {
                    if (m != null && m.getValue() != null) {
                        assert mostEnrolledActiveCourse != null;
                        if (mostEnrolledActiveCourse > 0 && m.getValue().intValue() == mostEnrolledActiveCourse.intValue()) {
                            stringBuilder.append(m.getKey().getName()).append(", ");
                        }
                    }
                });

        if (!stringBuilder.isEmpty()) {
            return StringUtils.substring(stringBuilder.toString(), 0, stringBuilder.toString().length() - 2);
        }
        return notApplicable;
    }

    public String findLeastPopular() {
        if (points.isEmpty()) return notApplicable;

        StringBuilder stringBuilder = new StringBuilder();

        Map<Courses, Integer> courseToStudentCount = points.stream()
                .filter(point -> point.getMark() == 0)
                .collect(Collectors.groupingBy(Point::getCourse,
                        Collectors.mapping(Point::getStudentId,
                                Collectors.collectingAndThen(Collectors.toSet(), Set::size))));

        Integer leastEnrolledCourse = courseToStudentCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue).orElse(0);


        List<Map.Entry<Courses, Integer>> entryList = new ArrayList<>(courseToStudentCount.entrySet());
        entryList.sort(Comparator.comparingInt(entry -> entry.getKey().getId()));
        Map<Courses, Integer> sortedMap = entryList.stream().collect
                (Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

        sortedMap.entrySet().stream()
                .toList().forEach(m -> {
                    if (m != null && m.getValue().intValue() == leastEnrolledCourse) {
                        stringBuilder.append(m.getValue());
                        stringBuilder.append(m.getKey().getName()).append(", ");
                    }
                });


        if (!stringBuilder.isEmpty()) {
            return StringUtils.substring(stringBuilder.toString(), 0, stringBuilder.toString().length() - 2);
        }
        return notApplicable;
    }

    public String findHighestActivity() {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Courses, Integer> courseToStudentCount = points.stream()
                .filter(point -> point.getMark() > 0)
                .collect(Collectors.groupingBy(Point::getCourse,
                        Collectors.mapping(Point::getMark,
                                Collectors.collectingAndThen(Collectors.toList(), List::size))));
        Integer mostEnrolledActiveCourse = courseToStudentCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue).orElse(null);


        List<Map.Entry<Courses, Integer>> entryList = new ArrayList<>(courseToStudentCount.entrySet());
        entryList.sort(Comparator.comparingInt(entry -> entry.getKey().getId()));
        Map<Courses, Integer> sortedMap = entryList.stream().collect
                (Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

        sortedMap.entrySet().stream()
                .toList().forEach(m -> {
                    if (m != null && m.getValue() != null) {
                        assert mostEnrolledActiveCourse != null;
                        if (mostEnrolledActiveCourse > 0 && m.getValue().intValue() == mostEnrolledActiveCourse.intValue()) {
                            stringBuilder.append(m.getKey().getName()).append(", ");
                        }
                    }
                });

        if (!stringBuilder.isEmpty()) {
            return StringUtils.substring(stringBuilder.toString(), 0, stringBuilder.toString().length() - 2);
        }

        return notApplicable;
    }

    public String findLowestActivity() {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Courses, Integer> courseToStudentCount = points.stream()
                .filter(point -> point.getMark() == 0)
                .collect(Collectors.groupingBy(Point::getCourse,
                        Collectors.mapping(Point::getMark,
                                Collectors.collectingAndThen(Collectors.toList(), List::size))));
        Integer mostEnrolledActiveCourse = courseToStudentCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue).orElse(null);

        List<Map.Entry<Courses, Integer>> entryList = new ArrayList<>(courseToStudentCount.entrySet());
        entryList.sort(Comparator.comparingInt(entry -> entry.getKey().getId()));
        Map<Courses, Integer> sortedMap = entryList.stream().collect
                (Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

        sortedMap.entrySet().stream()
                .toList().forEach(m -> {
                    if (m != null && m.getValue() != null) {
                        assert mostEnrolledActiveCourse != null;
                        if (mostEnrolledActiveCourse > 0 && m.getValue().intValue() == mostEnrolledActiveCourse.intValue()) {
                            stringBuilder.append(m.getKey().getName()).append(", ");
                        }
                    }
                });

        if (!stringBuilder.isEmpty()) {
            return StringUtils.substring(stringBuilder.toString(), 0, stringBuilder.toString().length() - 2);
        }
        return notApplicable;
    }

    public String findEasiestCourse() {
        StringBuilder stringBuilder = new StringBuilder();
        if (calcAvgSum(points).entrySet().stream().max(Map.Entry.comparingByValue()).isPresent()) {
            long maxCourses = calcAvgSum(points).entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue().longValue();
            return getCourses(stringBuilder, maxCourses);
        }
        return notApplicable;
    }

    private String getCourses(StringBuilder stringBuilder, long limitValue) {
        if (points.isEmpty()) return notApplicable;

        calcAvgSum(points).entrySet().stream()
                .toList().forEach(m -> {
                    if (m != null && m.getValue() != null) {
                        if (limitValue > 0 && m.getValue().intValue() == limitValue) {
                            stringBuilder.append(m.getKey().getName()).append(", ");
                        }
                    }
                });

        if (!stringBuilder.isEmpty()) {
            return StringUtils.substring(stringBuilder.toString(), 0, stringBuilder.toString().length() - 2);
        }
        return notApplicable;
    }

    public String findHardestCourse() {
        StringBuilder stringBuilder = new StringBuilder();
        if (calcAvgSum(points).entrySet().stream().min(Map.Entry.comparingByValue()).isPresent()) {
            long minCourses = calcAvgSum(points).entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue().longValue();
            long maxCourses = calcAvgSum(points).entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue().longValue();
            if (maxCourses==minCourses) return notApplicable;
            return getCourses(stringBuilder, minCourses);
        }
        return notApplicable;
    }

    public void getCourseStatistics(Courses course) {
        System.out.println("id    points    completed");
        Comparator<Point> maxPoints = Comparator.comparing(Point::getMark).reversed().thenComparing(Point::getStudentId);
        List<Point> coursePoints = new ArrayList<>(studentPoint.values()
                .stream()
                .flatMap(List::stream)
                .filter(point -> point.getCourse().equals(course))
                .toList());

        coursePoints.stream().sorted(maxPoints).forEach(point -> {
            float completed = ((float) point.getMark() / point.getCourse().getMaxPoints()) * 100;
            DecimalFormat format = new DecimalFormat("0.0");
            format.setRoundingMode(RoundingMode.HALF_UP);
            if (!format.format(completed).equals("0.0")){
                System.out.println(point.getStudentId()
                        + " " + point.getMark()
                        + "        " + format.format(completed) + "%");
            }
        });
    }


    private Map<Courses, Double> calcAvgSum(List<Point> pointList) {

        Map<Courses, Long> sumMarks = pointList.stream()
                .filter(point -> point.getMark() > 0)
                .collect(Collectors.groupingBy(Point::getCourse,
                        Collectors.summingLong(Point::getMark)));

        Map<Courses, Integer> countAttempt = pointList.stream()
                .filter(point -> point.getMark() > 0)
                .collect(Collectors.groupingBy(Point::getCourse,
                        Collectors.mapping(Point::getMark,
                                Collectors.collectingAndThen(Collectors.toList(), List::size))));

        Map<Courses, Double> originalMap = sumMarks.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue() / countAttempt.getOrDefault(entry.getKey(), 1)
                ));


        List<Map.Entry<Courses, Double>> entryList = new ArrayList<>(originalMap.entrySet());

        entryList.sort(Comparator.comparingInt(entry -> entry.getKey().getId()));

        Map<Courses, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Courses, Double> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
