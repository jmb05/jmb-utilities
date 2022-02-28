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

package net.jmb19905.util.events;

import org.jetbrains.annotations.NotNull;

import java.util.EventObject;

public abstract class Event<EC extends EventContext> extends EventObject {
    private final EC ctx;
    private final String id;

    public Event(@NotNull EC ctx, String id) {
        super(ctx.getSource());
        this.ctx = ctx;
        this.id = id;
    }

    public EC getContext() {
        return ctx;
    }

    public String getId() {
        return id;
    }
}
