package net.vincedgy.aws.library.controller;

/**
 * Created by vincent on 08/05/2017.
 */
public class Greeting {

    private long id;
    private String content;

    public Greeting() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}