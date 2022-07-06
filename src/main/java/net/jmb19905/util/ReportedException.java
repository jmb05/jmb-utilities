package net.jmb19905.util;

public class ReportedException extends Exception {

    private final boolean exit;

    public ReportedException(boolean exit) {
        super();
        this.exit = exit;
    }

    public ReportedException(String message, boolean exit) {
        super(message);
        this.exit = exit;
    }

    public ReportedException(String message, Throwable cause, boolean exit) {
        super(message, cause);
        this.exit = exit;
    }

    public ReportedException(Throwable cause, boolean exit) {
        super(cause);
        this.exit = exit;
    }

    public boolean shouldExit() {
        return exit;
    }

    public void handle() {
        Logger.fatal(this);
        if(shouldExit()) ShutdownManager.shutdown(-1);
    }

}
