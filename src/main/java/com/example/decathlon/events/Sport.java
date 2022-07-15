package com.example.decathlon.events;

/*
 * Class containing all the information about a sport in Decathlon.
 * */

import com.example.decathlon.events.utils.Parameters;
import com.example.decathlon.events.utils.SportsCategory;
import com.example.decathlon.events.utils.SportsType;
import com.example.decathlon.events.utils.Units;

/*
*   Main Sport class - contains details of each sport - name,type, category, units in which its recorded and
*   parameters associated for calculation of points
* */
public class Sport {

    private String name;
    private SportsType type;// enum of TRACK, FIELD
    private SportsCategory category; // enum of sub-category - RUNNING, THROWING,JUMPING
    private Parameters parameters;
    private Units unit; // enum of units - seconds, metres etc

    public Sport(String name, SportsType type, SportsCategory category, Parameters parameters, Units unit) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.parameters = parameters;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SportsType getType() {
        return type;
    }

    public void setType(SportsType type) {
        this.type = type;
    }

    public SportsCategory getCategory() {
        return category;
    }

    public void setCategory(SportsCategory category) {
        this.category = category;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }
}
