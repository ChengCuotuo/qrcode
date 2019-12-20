package entity;

public class Entity {
    private String id;
    private String content;
    private String name;

    public Entity() {
    }

    public Entity(String content, String name) {
        this.content = content;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" + id + ", " + content + ", " + name  + "}";
    }
}
