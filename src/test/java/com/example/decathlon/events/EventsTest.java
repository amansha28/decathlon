package com.example.decathlon.events;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventsTest {
    @Test
    public void getSportsList_ListNotEmptyTest() {
        Events events = new Events();
        List<Sport> list = events.getSportsList();
        assertFalse(() -> list.isEmpty(), () -> "List of sports events is Empty");
    }

    @Test
    public void getSportsList_ListEmptyTest() {
        Events events = new Events();
        List<Sport> list = events.getSportsList();
        list.clear();
        assertTrue(() -> list.isEmpty(), () -> "List of sports events is not Empty");
    }
}
