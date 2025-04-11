package ubx.archilog.model.io;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import ubx.archilog.model.*;

public class XmlLoader implements FileLoader {

  private Document document;

  public XmlLoader() {}

  public void load(String filePath) throws Exception {
    System.out.println("Loading " + filePath);
    File file = new File(filePath);
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    document = builder.parse(file);
    document.getDocumentElement().normalize();

    loadDocument();
  }

  private void loadDocument() {
    NodeList shapeEditorNodes = document.getElementsByTagName("shapeEditor");
    if (shapeEditorNodes.getLength() > 0) {
      Element shapeEditorElement = (Element) shapeEditorNodes.item(0);
      loadGroups(shapeEditorElement);
    }
  }

  private void loadGroups(Element parentElement) {
    /* NodeList menuNodes = parentElement.getElementsByTagName("menu");
    for (int i = 0; i < menuNodes.getLength(); i++) {
        Element menuElement = (Element) menuNodes.item(i);
        Group menu = new Group();
        loadGroup(menuElement, menu);
        Model.getInstance().setMenu(menu);
    }*/

    NodeList toolBarNodes = parentElement.getElementsByTagName("toolBar");
    for (int i = 0; i < toolBarNodes.getLength(); i++) {
      Element toolBarElement = (Element) toolBarNodes.item(i);
      ToolBar toolBar = new ToolBar();
      loadGroup(toolBarElement, toolBar);
      Model.getInstance().setToolBar(toolBar);
    }

    NodeList canvasNodes = parentElement.getElementsByTagName("canvas");
    for (int i = 0; i < canvasNodes.getLength(); i++) {
      Element canvasElement = (Element) canvasNodes.item(i);
      Group canvas = new Group();
      loadGroup(canvasElement, canvas);
      canvas.setZindex(0);
      Model.getInstance().setCanvas(canvas);
      System.out.println("CANVAS = " + Model.getInstance().getCanvas());
    }
  }

  private void loadGroup(Element groupElement, Group group) {
    NodeList groupChildren = groupElement.getChildNodes();
    for (int i = 0; i < groupChildren.getLength(); i++) {
      Node node = groupChildren.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        if (element.getTagName().equals("group")) {
          Group childGroup = new Group();
          loadGroup(element, childGroup);
          group.add(childGroup);
        } else if (element.getTagName().equals("square")) {
          Square square = loadSquare(element);
          group.add(square);
        } else if (element.getTagName().equals("rectangle")) {
          Rectangle rectangle = loadRectangle(element);
          group.add(rectangle);
        } else if (element.getTagName().equals("circle")) {
          Circle circle = loadCircle(element);
          group.add(circle);
        }
      }
    }
  }

  private Square loadSquare(Element squareElement) {
    Square square = new Square(0, 0, 0, 0);
    loadShapeAttributes(squareElement, square);
    square.setWidth(
        Integer.parseInt(
            squareElement
                .getElementsByTagName("size")
                .item(0)
                .getChildNodes()
                .item(1)
                .getTextContent()));
    return square;
  }

  private Rectangle loadRectangle(Element rectangleElement) {
    Rectangle rectangle = new Rectangle(0, 0, 0, 0, 0, false);
    loadShapeAttributes(rectangleElement, rectangle);
    rectangle.setWidth(
        Integer.parseInt(
            rectangleElement
                .getElementsByTagName("size")
                .item(0)
                .getChildNodes()
                .item(1)
                .getTextContent()));
    rectangle.setHeight(
        Integer.parseInt(
            rectangleElement
                .getElementsByTagName("size")
                .item(0)
                .getChildNodes()
                .item(3)
                .getTextContent()));
    rectangle.setFill(
        Boolean.parseBoolean(
            rectangleElement.getElementsByTagName("fill").item(0).getTextContent()));
    return rectangle;
  }

  private Circle loadCircle(Element circleElement) {
    Circle circle = new Circle(0, 0, 0, 0);
    loadShapeAttributes(circleElement, circle);
    circle.setRadius(
        Integer.parseInt(circleElement.getElementsByTagName("radius").item(0).getTextContent()));
    return circle;
  }

  private void loadShapeAttributes(Element shapeElement, Shape shape) {
    Element coordinatesElement = (Element) shapeElement.getElementsByTagName("coordinates").item(0);
    shape.setX(
        Integer.parseInt(coordinatesElement.getElementsByTagName("x").item(0).getTextContent()));
    shape.setY(
        Integer.parseInt(coordinatesElement.getElementsByTagName("y").item(0).getTextContent()));
    shape.setZindex(
        Integer.parseInt(
            coordinatesElement.getElementsByTagName("zIndex").item(0).getTextContent()));

    Element colorElement = (Element) shapeElement.getElementsByTagName("color").item(0);
    Color color =
        new Color(
            Integer.parseInt(colorElement.getElementsByTagName("r").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("g").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("b").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("a").item(0).getTextContent()));
    shape.setColor(color);
  }
}
