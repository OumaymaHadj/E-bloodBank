package hadj.tn.test.recyclerViewHome;

import java.util.List;

import hadj.tn.test.recyclerViewHome.ChildModelClass;

public class ParentModelClass {
    String title;
    List<ChildModelClass> childModelClassList;

    public ParentModelClass(String title, List<ChildModelClass> childModelClassList ) {
        this.title = title;
        this.childModelClassList=childModelClassList;
    }
}
