public enum Status {
    INITIAL,
    ASSIGNED,
    IN_PROGRESS,
    DONE;

    public Status next() {
        switch ( this ) {
            case INITIAL:
                return ASSIGNED;
            case ASSIGNED:
                return IN_PROGRESS;
            case IN_PROGRESS:
            default:
                return DONE;
        }
    }
}