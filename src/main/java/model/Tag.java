package model;

import model.interfaces.Entity;

import java.util.Objects;

public class Tag implements Entity {
    private Long id;
    private String tagName;

    public Tag() {
        this.id = -1L;
        this.tagName = "null";
    }

    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.getId(), ((Tag) obj).getId());
    }

    @Override
    public String toString() {
        return  " :.:.:.: Tag :.:.:.:\n"  +
                " id: " + this.getId() + "\n" +
                " name: " + this.getTagName() + "\n" +
                " :.:.:.::.:.:.::.:.:\n";
    }
}
