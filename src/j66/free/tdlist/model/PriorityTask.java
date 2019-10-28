package j66.free.tdlist.model;

public enum PriorityTask {
    VERY_HIGH("Very high"),HIGH("High"),NORMAL("Normal"),LOW("Low"),VERY_LOW("Very low");

    String name;

    PriorityTask(String name) {
        this.name = name;
    }
}
