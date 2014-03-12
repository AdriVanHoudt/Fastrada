package be.fastrada.pojo;

import java.io.Serializable;

public class Test implements Serializable {
    private static final long serialVersionUID = 1L;
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
