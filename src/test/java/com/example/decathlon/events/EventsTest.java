package com.example.decathlon.events;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventsTest {
    @Test
    void getSportsList_ListNotEmptyTest() {
//        Events events = Events.getInstance();
        List<Sport> list = Events.getSportsList();
        assertFalse(() -> list.isEmpty(), () -> "List of sports events is Empty");
    }

    @Test
    void getSportsList_ListEmptyTest() {
//        Events events = Events.getInstance();
        List<Sport> list = Events.getSportsList();
        list.clear();
        assertTrue(() -> list.isEmpty(), () -> "List of sports events is not Empty");
    }

}
