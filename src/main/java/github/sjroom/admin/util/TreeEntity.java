package github.sjroom.admin.util;

import java.util.List;

public interface TreeEntity<E> {

    Long getId();

    Long getParentId();

    void setChildren(List<E> childList);

}
