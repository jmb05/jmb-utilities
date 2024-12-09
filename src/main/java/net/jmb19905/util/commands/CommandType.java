/*
 * A simple Messenger written in Java
 * Copyright (C) 2020-2022  Jared M. Bennett
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.jmb19905.util.commands;

import net.jmb19905.util.Logger;
import net.jmb19905.util.registry.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * CommandType Holds the handler for a specific type of commands
 * @param <P> the type of the commands
 */
public class CommandType<P extends ICommand> extends Type<P> {

    private final Class<P> commandClass;
    private final CommandHandler<P> handler;

    /**
     * Creates an object of type CommandType
     * @param commandClass class of the commands
     * @param handler handler for the commands
     */
    public CommandType(Class<P> commandClass, CommandHandler<P> handler) {
        this.commandClass = commandClass;
        this.handler = handler;
    }

    /**
     * Provides the CommandHandler of this CommandType which executes the actions of this command
     * @return the command handler
     */
    public CommandHandler<P> getHandler() {
        return handler;
    }

    /**
     * Creates a new instance of the command
     * @return the command instance
     */
    public P newCommandInstance() {
        try {
            Constructor<P> constructor = commandClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Logger.error(e);
            return null;
        }
    }

    /**
     * Handles a command
     * @param args the arguments of the command
     */
    public void handle(String[] args) {
        handler.handle(newCommandInstance(), args);
    }

}
