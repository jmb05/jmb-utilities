package net.jmb19905.util.bootstrapping;

public enum DeployState {
    CLIENT,
    DEPLOY,
    DEV,
    DEV_DEPLOY;

    public static DeployState fromString(String s) {
        return switch (s) {
            case "deploy" -> DEPLOY;
            case "dev" -> DEV;
            case "dev-deploy" -> DEV_DEPLOY;
            default -> CLIENT;
        };
    }

}
