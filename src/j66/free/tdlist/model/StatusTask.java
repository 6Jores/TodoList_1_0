package j66.free.tdlist.model;

public enum StatusTask {
    NO_PLAN(0),PLAN(1),DONE(2),LATE(3),CANCEL(4);

    private int status;

    StatusTask(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
