package net.jmb19905.util.bootstrapping;

/**
 * Represents where the application is deployed
 */
public enum DeployState {
    /**
     * client application
     */
    CLIENT,
    /**
     * server application
     */
    DEPLOY,
    /**
     * in development environment
     */
    DEV,
    /**
     * server application for development and testing purposes
     */
    DEV_DEPLOY;

    /**
     * Provides the DeployState for a string
     * @param s the string
     * @return the DeployState
     */
    public static DeployState fromString(String s) {
        return switch (s) {
            case "deploy" -> DEPLOY;
            case "dev" -> DEV;
            case "dev-deploy" -> DEV_DEPLOY;
            default -> CLIENT;
        };
    }

}
