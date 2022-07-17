package com.example.decathlon.athlete;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/*
 * Class to convert the list of Athletes to XML format.
 * */
@XmlRootElement(name = "athletes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Athletes {

    @XmlElement(name = "athlete")
    List<Athlete> list = null;

    public List<Athlete> getList() {
        return list;
    }

    public void setList(List<Athlete> list) {
        this.list = list;
    }
}
