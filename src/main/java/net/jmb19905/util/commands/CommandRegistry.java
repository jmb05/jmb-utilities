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

import net.jmb19905.util.registry.Registry;

/**
 * Registry for all the command types
 */
public class CommandRegistry extends Registry {

    private static final CommandRegistry instance = new CommandRegistry();

    /**
     * Creates an object of type CommandRegistry
     */
    public CommandRegistry() {}

    /**
     * Registers a new command type
     * @param id id of the command type
     * @param commandClass class of the command
     * @param handler handler for this type
     * @param <C> the command type
     */
    public <C extends ICommand> void register(String id, Class<C> commandClass, CommandHandler<C> handler) {
        super.register(id, new CommandType<>(commandClass, handler));
    }

    /**
     * Returns the command type for a specific id
     * @param id the command type id
     * @return the command type
     * @throws NullPointerException when there is no Command Type for this id
     */
    public CommandType<? extends ICommand> getCommandType(String id) {
        return (CommandType<? extends ICommand>) super.getRegistry(id);
    }

    /**
     * Provides the instance of the command registry - used to globally register commands
     * @return the instance of the registry (singleton)
     */
    public static CommandRegistry getInstance() {
        return instance;
    }
}
