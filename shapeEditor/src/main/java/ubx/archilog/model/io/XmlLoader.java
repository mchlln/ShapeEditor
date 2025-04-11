package ubx.archilog.model.io;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import ubx.archilog.model.*;

public class XmlLoader implements FileLoader {

  private Document document;

  public XmlLoader() {}

  public void load(final String filePath) throws Exception {
    System.out.println("Loading " + filePath);
    final File file = new File(filePath);
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    final DocumentBuilder builder = factory.newDocumentBuilder();
    document = builder.parse(file);
    document.getDocumentElement().normalize();

    loadDocument();
  }

  private void loadDocument() {
    final NodeList shapeEditorNodes = document.getElementsByTagName("shapeEditor");
    if (shapeEditorNodes.getLength() > 0) {
      final Element shapeEditorElement = (Element) shapeEditorNodes.item(0);
      loadGroups(shapeEditorElement);
    }
  }

  private void loadGroups(final Element parentElement) {
    final NodeList toolBarNodes = parentElement.getElementsByTagName("toolBar");
    for (int i = 0; i < toolBarNodes.getLength(); i++) {
      final Element toolBarElement = (Element) toolBarNodes.item(i);
      final ToolBar toolBar = new ToolBar();
      loadGroup(toolBarElement, toolBar);
      Model.getInstance().setToolBar(toolBar);
    }

    final NodeList canvasNodes = parentElement.getElementsByTagName("canvas");
    for (int i = 0; i < canvasNodes.getLength(); i++) {
      final Element canvasElement = (Element) canvasNodes.item(i);
      final Group canvas = new Group();
      loadGroup(canvasElement, canvas);
      canvas.setZindex(0);
      Model.getInstance().setCanvas(canvas);
    }
  }

  private void loadGroup(final Element groupElement, final Group group) {
    final NodeList groupChildren = groupElement.getChildNodes();
    for (int i = 0; i < groupChildren.getLength(); i++) {
      final Node node = groupChildren.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        final Element element = (Element) node;
        if (element.getTagName().equals("group")) {
          final Group childGroup = new Group();
          loadGroup(element, childGroup);
          group.add(childGroup);
        } else if (element.getTagName().equals("square")) {
          final Square square = loadSquare(element);
          group.add(square);
        } else if (element.getTagName().equals("rectangle")) {
          final Rectangle rectangle = loadRectangle(element);
          group.add(rectangle);
        } else if (element.getTagName().equals("circle")) {
          final Circle circle = loadCircle(element);
          group.add(circle);
        }
      }
    }
  }

  private Square loadSquare(final Element squareElement) {
    final Square square = new Square(0, 0, 0, 0);
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

  private Rectangle loadRectangle(final Element rectangleElement) {
    final Rectangle rectangle = new Rectangle(0, 0, 0, 0, 0, false);
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

  private Circle loadCircle(final Element circleElement) {
    final Circle circle = new Circle(0, 0, 0, 0);
    loadShapeAttributes(circleElement, circle);
    circle.setRadius(
        Integer.parseInt(circleElement.getElementsByTagName("radius").item(0).getTextContent()));
    return circle;
  }

  private void loadShapeAttributes(final Element shapeElement, final Shape shape) {
    final Element coordinatesElement =
        (Element) shapeElement.getElementsByTagName("coordinates").item(0);
    shape.setX(
        Integer.parseInt(coordinatesElement.getElementsByTagName("x").item(0).getTextContent()));
    shape.setY(
        Integer.parseInt(coordinatesElement.getElementsByTagName("y").item(0).getTextContent()));
    shape.setZindex(
        Integer.parseInt(
            coordinatesElement.getElementsByTagName("zIndex").item(0).getTextContent()));

    final Element colorElement = (Element) shapeElement.getElementsByTagName("color").item(0);
    final Color color =
        new Color(
            Integer.parseInt(colorElement.getElementsByTagName("r").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("g").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("b").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("a").item(0).getTextContent()));
    shape.setColor(color);
  }
}
