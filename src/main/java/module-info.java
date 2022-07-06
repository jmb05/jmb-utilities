module jmb.utilities {
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;

    exports net.jmb19905.util;
    exports net.jmb19905.util.events;
    exports net.jmb19905.util.registry;
    exports net.jmb19905.util.commands;
    exports net.jmb19905.util.config;
    exports net.jmb19905.util.bootstrapping;
    exports net.jmb19905.util.crypto;
    exports net.jmb19905.util.tasks;
}