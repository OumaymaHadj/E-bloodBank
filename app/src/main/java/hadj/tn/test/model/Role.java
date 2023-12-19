package hadj.tn.test.model;


public class Role {

    private Long id;


    private AppUserRole name;

    public Role() {}

    public Role(AppUserRole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUserRole getName() {
        return name;
    }

    public void setName(AppUserRole name) {
        this.name = name;
    }
}