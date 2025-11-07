package model;

public class Department {
    private int id;
    private String name;

    public Department() {}

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Lưu ý: method tên là setId (chữ d thường), không phải setID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // setName/getName theo chuẩn camelCase
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Optionally override toString for debugging
    @Override
    public String toString() {
        return "Department{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}

