package be.fastrada.pojo;

import java.io.Serializable;

/**
 * Created by xaviergeerinck on 11/03/14.
 */
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
