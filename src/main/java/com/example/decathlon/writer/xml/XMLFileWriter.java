package com.example.decathlon.writer.xml;

import com.example.decathlon.athlete.Athlete;
import com.example.decathlon.athlete.Athletes;
import com.example.decathlon.util.validator.ListValidator;
import com.example.decathlon.util.validator.PathValidator;
import com.example.decathlon.writer.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/*
 * Implementation of Writer for writing XML type file.
 * */

public class XMLFileWriter implements Writer {

    public static final Logger logger = Logger.getLogger(XMLFileWriter.class.getName());
    ListValidator listValidator = new ListValidator();
    PathValidator pathValidator = new PathValidator();


    /*
     * This class will take a  and write them to a xml file at specified location.
     * @Input : list of Athletes
     * @Input : resultPath to write a file.
     * @Output : xml file at resultPath location
     * */
    @Override
    public void write(Map<String, Object> obj) throws JAXBException {
        logger.info("==== Starting XMLFileWriter : write ====");
        List<Athlete> athleteList = null;
        String resultPath = null;
        if (!obj.isEmpty()) {
            if (obj.containsKey(XMLWriterOutputParams.PLAYER_LIST)) {
                athleteList = (List<Athlete>) obj.get(XMLWriterOutputParams.PLAYER_LIST);
                listValidator.validate(athleteList);
            }
            if (obj.containsKey(XMLWriterOutputParams.RESULT_PATH)) {
                resultPath = (String) obj.get(XMLWriterOutputParams.RESULT_PATH);
                pathValidator.validate(resultPath);
            }
        }

        Athletes xmlOutputList = new Athletes();
        File resultFile = new File(resultPath);
        xmlOutputList.setList(athleteList);
        JAXBContext jaxbContext = JAXBContext.newInstance(Athletes.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(xmlOutputList, resultFile);

        logger.info("==== Ending XMLFileWriter : write ====");
    }

}
