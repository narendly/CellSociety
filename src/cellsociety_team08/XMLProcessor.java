package cellsociety_team08;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Processes XML files and extracts a Configuration class
 * to be used by the model
 *
 */
public class XMLProcessor {

    public Configuration parse (File xmlFile) throws SimulationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            Configuration config = docToConfig(doc);
            return config;
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            throw new SimulationException(e);
        }
    }

    /**
     * Private method that converts document to a Configuration object
     * 
     * @param doc
     * @return
     * @throws SimulationException
     */
    private Configuration docToConfig (Document doc) throws SimulationException {
        Configuration config = new Configuration();
        doc.getDocumentElement().normalize();
        parseType(doc, config);
        parseAuthor(doc, config);
        parseParameters(doc, config);
        parseInitialStates(doc, config);
        parseRandom(doc, config);
        return config;
    }

    private void parseRandom (Document doc, Configuration config) {
        Node randomTag = doc.getElementsByTagName("generateRandom").item(0);
        if (randomTag != null) {
            boolean generateRandom = Boolean.parseBoolean(randomTag.getTextContent());
            config.setRandomStates(generateRandom);
            String widthS = doc.getElementsByTagName("randomWidth").item(0).getTextContent();
            String heightS = doc.getElementsByTagName("randomHeight").item(0).getTextContent();
            config.setRandomWidth(Integer.parseInt(widthS));
            config.setRandomHeight(Integer.parseInt(heightS));
        }
    }

    /**
     * Initial states are added to the Configuration object
     * 
     * @param doc
     * @param config
     * @throws SimulationException
     */
    private void parseInitialStates (Document doc,
                                     Configuration config) throws SimulationException {
        List<List<Integer>> initialStates = new LinkedList<List<Integer>>();
        for (Element element : getElementsByTagName(doc, "row")) {
            String row = element.getTextContent();
            List<Integer> stateRow = new LinkedList<Integer>();
            for (int y = 0; y < row.length(); y++) {
                stateRow.add(Character.getNumericValue(row.charAt(y)));
            }
            initialStates.add(stateRow);
        }
        config.setInitialStates(initialStates);
    }

    private void parseParameters (Document doc, Configuration config) {
        for (Element element : getElementsByTagName(doc, "parameter")) {
            config.setParameter(element.getAttribute("id"),
                                Double.parseDouble(element.getTextContent()));
        }
    }

    private void parseAuthor (Document doc, Configuration config) {
        String author = doc.getElementsByTagName("author").item(0).getTextContent();
        config.setAuthorName(author);
    }

    private void parseType (Document doc, Configuration config) {
        String type = doc.getElementsByTagName("title").item(0).getTextContent();
        config.setSimulationType(type);
    }

    private Collection<Element> getElementsByTagName (Document doc, String tag) {
        NodeList docElements = doc.getElementsByTagName(tag);
        List<Element> elements = new ArrayList<Element>();
        for (int i = 0; i < docElements.getLength(); i++) {
            elements.add((Element)docElements.item(i));
        }
        return elements;
    }
}
