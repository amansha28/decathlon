package com.example.decathlon.events;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class EventsTest {

    @Test
    public void getSportsList_ListNotEmptyTest(){
         Events events = new Events();
        List<Sport> list = events.getSportsList();
//        list.clear();
//         assertEquals(list.isEmpty(), false,()->"List is not Empty");
         assertFalse(()-> list.isEmpty(),()->"List of sports events is Empty");
    }


    @Test
    public void getSportsList_ListEmptyTest(){
        Events events = new Events();
        List<Sport> list = events.getSportsList();
        list.clear();
//        assertEquals(list.isEmpty(), true,()->"List is Empty");
        assertTrue(()-> list.isEmpty(),()->"List of sports events is not Empty");
    }
}
