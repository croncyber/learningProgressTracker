package tracker.entity;

public enum Courses {
    JAVA(1, "Java", 600),
    DSA(2, "DSA", 400),
    DATABASES(3, "Databases", 480),
    SPRING(4, "Spring", 550);

    final int id;
    final int maxPoints;
    final String name;

    Courses(int id, String name, int maxPoints) {
        this.id = id;
        this.name = name;
        this.maxPoints = maxPoints;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Courses getCourses(int id){
        if (id>Courses.values().length){
            return null;
        }
        return Courses.values()[id];
    }

    public int getMaxPoints() {
        return maxPoints;
    }
}
