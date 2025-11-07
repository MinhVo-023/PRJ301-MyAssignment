package model;

public class Feature {
    private int id;
    private String name;
    private String url;

    public Feature() {
    }

    public Feature(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    // --- GETTERS & SETTERS ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // --- TO STRING (optional for debugging) ---
    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
