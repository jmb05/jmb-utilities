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

/**
 * Abstract Command Handler, should be implemented for every implementor of ICommand
 * @param <C> the Command Type
 */
public abstract class CommandHandler<C extends ICommand> {

    /**
     * Creates an object of type CommandHandler
     */
    public CommandHandler() {}

    /**
     * Abstract method that handles the command
     * @param command the command
     * @param args the arguments
     */
    public abstract void handle(C command, String[] args);
}