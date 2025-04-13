package ubx.archilog.model.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import ubx.archilog.model.*;
import ubx.archilog.model.shapes.*;

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
      final ToolBar toolBar = new ToolBar(true);
      List<Shape> toAdd = loadGroup(toolBarElement, toolBar);
      for (Shape shape : toAdd) {
        if (shape.zIndex() != 0) {
          toolBar.addShapeToToolBar(shape);
        }
      }
      Model.getInstance().setToolBar(toolBar);
    }

    final NodeList canvasNodes = parentElement.getElementsByTagName("canvas");
    for (int i = 0; i < canvasNodes.getLength(); i++) {
      final Element canvasElement = (Element) canvasNodes.item(i);
      final Group canvas = new Group(false);
      List<Shape> toAdd = loadGroup(canvasElement, canvas);
      for (Shape shape : toAdd) {
        canvas.add(shape);
      }
      canvas.zIndex(0);
      Model.getInstance().setCanvas(canvas);
    }
  }

  private List<Shape> loadGroup(final Element groupElement, final Group group) {
    List<Shape> children = new ArrayList<>();
    final NodeList groupChildren = groupElement.getChildNodes();
    for (int i = 0; i < groupChildren.getLength(); i++) {
      final Node node = groupChildren.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        final Element element = (Element) node;
        switch (element.getTagName()) {
          case "group" -> {
            final Group childGroup = new Group(false);
            List<Shape> toAdd = loadGroup(element, childGroup);
            for (Shape shape : toAdd) {
              childGroup.add(shape);
            }
            children.add(childGroup);
          }
          case "rectangle" -> {
            final Rectangle rectangle = loadRectangle(element);
            children.add(rectangle);
          }
          case "circle" -> {
            final Circle circle = loadCircle(element);
            children.add(circle);
          }
          case "regularPolygon" -> {
            final RegularPolygon polygon = loadPolygon(element);
            children.add(polygon);
          }
        }
      }
    }
    return children;
  }

  private Rectangle loadRectangle(final Element rectangleElement) {
    final Rectangle rectangle = new Rectangle(0, 0, 0, 0, 0, false);
    loadShapeAttributes(rectangleElement, rectangle);
    rectangle.width(
        Integer.parseInt(
            rectangleElement
                .getElementsByTagName("size")
                .item(0)
                .getChildNodes()
                .item(1)
                .getTextContent()));
    rectangle.height(
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
    circle.radius(
        Integer.parseInt(circleElement.getElementsByTagName("radius").item(0).getTextContent()));
    circle.width(
        Integer.parseInt(
            circleElement
                .getElementsByTagName("size")
                .item(0)
                .getChildNodes()
                .item(1)
                .getTextContent()));
    circle.height(
        Integer.parseInt(
            circleElement
                .getElementsByTagName("size")
                .item(0)
                .getChildNodes()
                .item(3)
                .getTextContent()));
    return circle;
  }

  private RegularPolygon loadPolygon(final Element polygonElement) {
    final RegularPolygon polygon = new RegularPolygon(0, 0, 0, 0, 0, 0, null);
    loadShapeAttributes(polygonElement, polygon);
    polygon.sides(
        Integer.parseInt(polygonElement.getElementsByTagName("sides").item(0).getTextContent()));
    polygon.width(
        Integer.parseInt(
            polygonElement
                .getElementsByTagName("size")
                .item(0)
                .getChildNodes()
                .item(1)
                .getTextContent()));
    polygon.height(
        Integer.parseInt(
            polygonElement
                .getElementsByTagName("size")
                .item(0)
                .getChildNodes()
                .item(3)
                .getTextContent()));
    polygon.rotation(
        Integer.parseInt(
            polygonElement
                .getElementsByTagName("rotation")
                .item(0)
                .getChildNodes()
                .item(0)
                .getTextContent()));
    return polygon;
  }

  private void loadShapeAttributes(final Element shapeElement, final Shape shape) {
    final Element coordinatesElement =
        (Element) shapeElement.getElementsByTagName("coordinates").item(0);
    shape.x(
        Integer.parseInt(coordinatesElement.getElementsByTagName("x").item(0).getTextContent()));
    shape.y(
        Integer.parseInt(coordinatesElement.getElementsByTagName("y").item(0).getTextContent()));
    shape.zIndex(
        Integer.parseInt(
            coordinatesElement.getElementsByTagName("zIndex").item(0).getTextContent()));

    final Element colorElement = (Element) shapeElement.getElementsByTagName("color").item(0);
    final Color color =
        new Color(
            Integer.parseInt(colorElement.getElementsByTagName("r").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("g").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("b").item(0).getTextContent()),
            Integer.parseInt(colorElement.getElementsByTagName("a").item(0).getTextContent()));
    shape.color(color);
  }
}
