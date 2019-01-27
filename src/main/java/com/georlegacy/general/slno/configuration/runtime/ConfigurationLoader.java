package com.georlegacy.general.slno.configuration.runtime;

import com.georlegacy.general.slno.configuration.Reference;
import com.georlegacy.general.slno.configuration.enumeration.ConfigValues;
import com.georlegacy.general.slno.configuration.util.ConfigurationUtil;
import com.georlegacy.general.slno.configuration.validation.Validator;
import com.georlegacy.general.slno.configuration.validation.enumeration.ValidatorRules;
import com.georlegacy.general.slno.runtime.RuntimeExecutable;
import com.georlegacy.general.slno.runtime.SLNORuntime;
import com.georlegacy.general.slno.runtime.enumeration.StopLevel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("TryWithIdenticalCatches")
public class ConfigurationLoader extends RuntimeExecutable {

    @Override
    protected int firstStart(SLNORuntime runtime) {
        return 0;
    }

    @Override
    protected int start(SLNORuntime runtime) {
        File settings = new File(Reference.CONFIG_DIR, "settings.properties");
        File columns = new File(Reference.CONFIG_DIR, "columns.xml");

        Map<String, String> settingsValues;
        Map<String, String> columnsValues;

        try (InputStream inputStream = new FileInputStream(settings)) {
            Properties settingsProperties = new Properties();
            settingsProperties.load(inputStream);
            settingsValues = ConfigurationUtil.readProperties(ConfigValues.getKeys(), settingsProperties);
        } catch (FileNotFoundException e) {
            System.out.println("Critical configuration file not found: 'settings.properties'");
            System.out.println("Creating critical configuration file: 'settings.properties'");
            try {
                if (!settings.createNewFile()) {
                    System.out.println("Error while creating critical configuration file: 'settings.properties'");
                    System.out.println("Please submit a formal issue request on the OSS GitHub page.");
                    SLNORuntime.shutdown();
                    return 1;
                }
            } catch (IOException e1) {
                System.out.println("Error while creating critical configuration file: 'settings.properties'");
                System.out.println("Please submit a formal issue request on the OSS GitHub page.");
                SLNORuntime.shutdown();
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }

        try (InputStream inputStream = new FileInputStream(columns)) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(columns);
            document.getDocumentElement().normalize();
            NodeList columnsChildrenList = document.getElementsByTagName("columns").item(0).getChildNodes();
            for (int i = 0; i < columnsChildrenList.getLength(); i++) {
                if (!(columnsChildrenList.item(i).getNodeType() == Node.ELEMENT_NODE))
                    continue;
                if (columnsChildrenList.item(i).getNodeName().equals("column")) {
                    Element element = (Element) columnsChildrenList.item(i);

                    String id = element.getAttribute("id");
                    String viewPermissionNode = element.getElementsByTagName("viewPermissionNode").getLength() > 0 ?
                            element.getElementsByTagName("viewPermissionNode").item(0).getTextContent() :
                            "columns.default.view";
                    String editPermissionNode = element.getElementsByTagName("editPermissionNode").getLength() > 0 ?
                            element.getElementsByTagName("editPermissionNode").item(0).getTextContent() :
                            "columns.default.edit";
                    String required = element.getElementsByTagName("required").getLength() > 0 ?
                            element.getElementsByTagName("required").item(0).getTextContent() :
                            "false";
                    String emptyBehaviour = element.getElementsByTagName("emptyBehaviour").getLength() > 0 ?
                            element.getElementsByTagName("emptyBehaviour").item(0).getTextContent() :
                            "null"; // null or default
                    String defaultValue = element.getElementsByTagName("defaultValue").getLength() > 0 ?
                            element.getElementsByTagName("defaultValue").item(0).getTextContent() :
                            null;
                    String displayName = element.getElementsByTagName("displayName").getLength() > 0 ?
                            element.getElementsByTagName("displayName").item(0).getTextContent() :
                            null;
                    String max = element.getElementsByTagName("max").getLength() > 0 ?
                            element.getElementsByTagName("max").item(0).getTextContent() :
                            "-1";
                    String min = element.getElementsByTagName("min").getLength() > 0 ?
                            element.getElementsByTagName("min").item(0).getTextContent() :
                            "0";
                    String type = element.getElementsByTagName("type").getLength() > 0 ?
                            element.getElementsByTagName("type").item(0).getTextContent() :
                            null; // str or num or int or list or place
                    List<ValidatorRules> idFailedRules = Validator.validate(id, "");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Critical configuration file not found: 'columns.xml'");
            System.out.println("Creating critical configuration file: 'columns.xml'");
            try {
                if (!settings.createNewFile()) {
                    System.out.println("Error while creating critical configuration file: 'columns.xml'");
                    System.out.println("Please submit a formal issue request on the OSS GitHub page.");
                    SLNORuntime.shutdown();
                    return 1;
                }
            } catch (IOException e1) {
                System.out.println("Error while creating critical configuration file: 'columns.xml'");
                System.out.println("Please submit a formal issue request on the OSS GitHub page.");
                SLNORuntime.shutdown();
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return 1;
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    protected int stop(StopLevel stopLevel) {
        return 0;
    }

}