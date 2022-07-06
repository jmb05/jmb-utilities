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

import net.jmb19905.util.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EventHandler<C extends EventContext<?>> {

    private boolean valid = false;
    protected final Map<String, EventListenerList<C>> eventListeners = new ConcurrentHashMap<>();
    private final String id;

    public EventHandler(String id) {
        this.id = id;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void addEventListener(EventListener listener) {
        EventListenerList<C> listenerBatch = eventListeners.get(id + ":" + listener.getId());
        if (listenerBatch == null) {
            listenerBatch = new EventListenerList<>();
        }
        listenerBatch.addEvent(listener);
        eventListeners.put(id + ":" + listener.getId(), listenerBatch);
    }

    public <E extends Event> void performEvent(E evt) {
        if (isValid()) {
            Logger.trace("Performing Event: " + id + ":" + evt.getId());
            EventListenerList<C> listenerBatch = eventListeners.get(id + ":" + evt.getId());
            if (listenerBatch != null) {
                for (EventListener listener : listenerBatch) {
                    listener.perform(evt);
                }
            }
        } else {
            Logger.warn("Event Handler is not valid");
        }
    }

}
