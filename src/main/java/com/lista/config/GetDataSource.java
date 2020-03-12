package com.lista.config;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class GetDataSource {
    private String sourceName = "JNDINamedbStudentWL";
    private String connectionUrl = "jdbc:oracle:thin:@localhost:1521:xe";
    private String driverClass = "oracle.jdbc.OracleDriver";
    private String userName = "STUDENT";
    private String passWord = "admin";

    public GetDataSource(String cFileSourece, String cFileDtd) {
        Document doc = getXmlDoc(cFileSourece, cFileDtd);
        if (doc != null) {
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList oList = doc.getElementsByTagName("datasources");
            Node oNode = oList.item(0);
            //System.out.println("nCurrent Element :" + oNode.getNodeName());
            Element oElement = (Element) oNode;
            sourceName = oElement.getElementsByTagName("source-name").item(0).getTextContent();
            //System.out.println("nCurrent Element :" + sourceName);
            connectionUrl = oElement.getElementsByTagName("connection-url").item(0).getTextContent();
            driverClass = oElement.getElementsByTagName("driver-class").item(0).getTextContent();
            passWord  = oElement.getElementsByTagName("password").item(0).getTextContent();
            userName  = oElement.getElementsByTagName("user-name").item(0).getTextContent();
        }
    }


    private Document getXmlDoc(String cFileXml, final String cFileDtd) {
        Document doc = null;
        while (true) {
            try {
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                if (cFileDtd.isEmpty()) {
                    domFactory.setValidating(false); //true
                } else {
                    domFactory.setValidating(true); //true
                }
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                builder.setErrorHandler(new ErrorHandler() {
                    @Override
                    public void warning(SAXParseException e) throws SAXException {
                        // do something more useful in each of these handlers
                        //System.out.println("warning " + e.getMessage());
                        //exception.printStackTrace();
                        throw new SAXException("warning document builder", e);
                    }

                    @Override
                    public void error(SAXParseException e) throws SAXException {
                        // do something more useful in each of these handlers
                        //System.out.println("error " + e.getMessage());
                        //e.printStackTrace();
                        throw new SAXException("error builder " + e.getMessage(), e);
                    }

                    @Override
                    public void fatalError(SAXParseException e) throws SAXException {
                        // do something more useful in each of these handlers
                        //System.out.println("fatalError " + e.getMessage());
                        throw new SAXException("fatalError document builder", e);
                    }

                });

                File fXmlFile = new File(cFileXml);
                doc = builder.parse(fXmlFile);
                doc.getDocumentElement().normalize();

            } catch (SAXException e) {
                /* нет DOCTYPE добавим*/
                if (e.getMessage().contains("must match DOCTYPE")) {
                    cFileXml = addDOCTYPE(cFileXml, cFileDtd);
                    continue;
                } else {
                    System.out.println(e.getMessage());
                    //e.printStackTrace();
                }

                //e.printStackTrace();
            } catch (ParserConfigurationException | IOException e) {
                e.printStackTrace();
            }
            break;
        }
        //ParserConfigurationException
        return doc;
    }

    /**
     * https://stackoverflow.com/questions/1096365/validate-an-xml-file-against-local-dtd-file-with-java
     *
     * @param cFileXml -
     * @param cFileDtd -
     * @return
     */
    private String addDOCTYPE(String cFileXml, String cFileDtd) {
        String cNewXmlFile = "tmp.xml";
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, cFileDtd);
            transformer.transform(new StreamSource(cFileXml), new StreamResult(new FileOutputStream(cNewXmlFile))); //System.out
        } catch (FileNotFoundException | TransformerException e) {
            e.printStackTrace();
        }
        return cNewXmlFile;
    }

    /**
     * // Функция для сохранения DOM в файл
     * https://java-course.ru/begin/xml/
     *
     * @param document -
     * @throws TransformerFactoryConfigurationError
     */
    private void writeDocument(Document document, String cFile) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(cFile);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (IOException | TransformerException e) {
            e.printStackTrace(System.out);
        }
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "GetDataSource{" +
                "\n  sourceName='" + sourceName + '\'' +
                ", \n  connectionUrl='" + connectionUrl + '\'' +
                ", \n  driverClass='" + driverClass + '\'' +
                ", \n  userName='" + userName + '\'' +
                ", \n  passWord='" + passWord + '\'' +
                '}';
    }
}
