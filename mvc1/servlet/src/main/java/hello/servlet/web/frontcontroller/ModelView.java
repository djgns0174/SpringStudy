package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

public class ModelView {

    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

}
