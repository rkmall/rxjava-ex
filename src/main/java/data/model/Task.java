package data.model;

public class Task {

    private int id;
    private String desc;
    private boolean isComplete;

    public Task(int id, String desc, boolean isComplete) {
        this.id = id;
        this.desc = desc;
        this.isComplete = isComplete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public String toString() {
        return "Note id: " + this.id + ", Description: " + this.desc;
    }
}
