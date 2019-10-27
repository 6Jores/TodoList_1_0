package j66.free.tdlist.model;

public enum PriorityTask {
    VERY_LOW("Very low"),LOW("Low"),NORMAL("Normal"),HIGH("High"),VERY_HIGH("Very high");

    String name;

    PriorityTask(String name) {
        this.name = name;
    }
}
