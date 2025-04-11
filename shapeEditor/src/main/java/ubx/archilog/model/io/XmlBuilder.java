package ubx.archilog.model.io;

import ubx.archilog.model.*;
import ubx.archilog.model.shapes.*;

public class XmlBuilder implements FileBuilder {

  private final StringBuilder stringBuilder;

  public XmlBuilder() {
    stringBuilder = new StringBuilder();
  }

  @Override
  public void beginDocument() {
    stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    stringBuilder.append("<shapeEditor>\n");
  }

  @Override
  public void endDocument() {
    stringBuilder.append("</shapeEditor>\n");
  }

  @Override
  public void beginGroup(final Group group) {
    stringBuilder.append("<group>\n");
    setCoordinates(group);
    setCompleteSize(group);
    parseGroup(group);
  }

  public void parseGroup(final Group group) {
    for (final Shape shape : group.getShapes()) {
      if (shape instanceof Square) {
        buildSquare((Square) shape);
      } else if (shape instanceof Rectangle) {
        buildRectangle((Rectangle) shape);
      } else if (shape instanceof Circle) {
        buildCircle((Circle) shape);
      } else if (shape instanceof Group) {
        beginGroup((Group) shape);
        parseGroup((Group) shape);
        endGroup();
      }
    }
  }

  @Override
  public void endGroup() {
    stringBuilder.append("</group>\n");
  }

  @Override
  public void beginToolBar() {
    stringBuilder.append("<toolBar>\n");
    final ToolBar toolBar = Model.getInstance().getToolBar();
    setCoordinates(toolBar);
    setCompleteSize(toolBar);
    parseGroup(toolBar);
  }

  @Override
  public void endToolBar() {
    stringBuilder.append("</toolBar>\n");
  }

  @Override
  public void beginCanvas() {
    stringBuilder.append("<canvas>\n");
    final Group canvas = Model.getInstance().getCanvas();
    setCoordinates(canvas);
    setCompleteSize(canvas);
    parseGroup(canvas);
  }

  @Override
  public void endCanvas() {
    stringBuilder.append("</canvas>\n");
  }

  @Override
  public void buildPolygon(final Polygon polygon) {
    stringBuilder.append("<polygon>\n");
    stringBuilder.append("<sides>").append(polygon.getSides()).append("</sides>\n");
    stringBuilder.append("</polygon>\n");
  }

  @Override
  public void buildEllipsoid(final Ellipsoid ellipsoid) {}

  @Override
  public void buildCircle(final Circle circle) {
    stringBuilder.append("<circle>\n");
    setCoordinates(circle);
    stringBuilder.append("<radius>").append(circle.getRadius()).append("</radius>\n");
    setColor(circle.getColor());
    stringBuilder.append("</circle>\n");
  }

  @Override
  public void buildRectangle(final Rectangle rectangle) {
    stringBuilder.append("<rectangle>\n");
    setCoordinates(rectangle);
    setCompleteSize(rectangle);
    setColor(rectangle.getColor());
    stringBuilder.append("<fill>").append(rectangle.isFill()).append("</fill>\n");
    stringBuilder.append("</rectangle>\n");
  }

  @Override
  public void buildSquare(final Square square) {
    stringBuilder.append("<square>\n");
    setCoordinates(square);
    stringBuilder.append("<size>\n");
    stringBuilder.append("<width>").append(square.getWidth()).append("</width>\n");
    stringBuilder.append("</size>\n");
    setColor(square.getColor());
    stringBuilder.append("</square>\n");
  }

  public String getResult() {

    return stringBuilder.toString();
  }

  private void setCoordinates(final Shape shape) {
    stringBuilder.append("<coordinates>\n");
    stringBuilder.append("<x>").append(shape.getX()).append("</x>\n");
    stringBuilder.append("<y>").append(shape.getY()).append("</y>\n");
    stringBuilder.append("<zIndex>").append(shape.getZindex()).append("</zIndex>\n");
    stringBuilder.append("</coordinates>\n");
  }

  private void setColor(final Color color) {
    stringBuilder.append("<color>\n");
    stringBuilder.append("<r>").append(color.r()).append("</r>\n");
    stringBuilder.append("<g>").append(color.g()).append("</g>\n");
    stringBuilder.append("<b>").append(color.b()).append("</b>\n");
    stringBuilder.append("<a>").append(color.a()).append("</a>\n");
    stringBuilder.append("</color>\n");
  }

  private void setCompleteSize(final Shape shape) {
    stringBuilder.append("<size>\n");
    stringBuilder.append("<width>").append(shape.getWidth()).append("</width>\n");
    stringBuilder.append("<height>").append(shape.getHeight()).append("</height>\n");
    stringBuilder.append("</size>\n");
  }
}
