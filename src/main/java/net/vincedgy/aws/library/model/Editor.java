package net.vincedgy.aws.library.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vincent on 05/05/2017.
 */
@Entity
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    public Editor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Editor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
