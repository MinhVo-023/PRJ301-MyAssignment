package model;

/**
 *
 * @author Minh
 */

public class User {
    private int id;
    private String username;
    private String fullname;
    private int departmentId;
    private Integer managerId;
    private String roleName;
    private String departmentName;

    public User() {}
    public User(int id, String username, String fullname, int departmentId, Integer managerId) {
        this.id = id; this.username = username; this.fullname = fullname;
        this.departmentId = departmentId; this.managerId = managerId;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    
    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
    
    public String getRoleName() {return roleName;}
    public void setRoleName(String roleName) {this.roleName = roleName;}
    
    public String getDepartmentName() {return departmentName;}
    public void setDepartmentName(String departmentName) {this.departmentName = departmentName;}
}

