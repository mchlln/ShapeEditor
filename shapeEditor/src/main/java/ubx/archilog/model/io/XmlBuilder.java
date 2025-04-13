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
    for (final Shape shape : group.shapes()) {
      if (shape instanceof ImageRectangle) {
        continue;
      } else if (shape instanceof Rectangle) {
        buildRectangle((Rectangle) shape);
      } else if (shape instanceof Circle) {
        buildCircle((Circle) shape);
      } else if (shape instanceof RegularPolygon) {
        buildRegularPolygon((RegularPolygon) shape);
      } else if (shape instanceof Group) {
        beginGroup((Group) shape);
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
    stringBuilder.append("<sides>").append(polygon.sides()).append("</sides>\n");
    stringBuilder.append("</polygon>\n");
  }

  @Override
  public void buildRegularPolygon(final RegularPolygon polygon) {
    stringBuilder.append("<regularPolygon>\n");
    setCoordinates(polygon);
    setCompleteSize(polygon);
    stringBuilder.append("<sides>").append(polygon.sides()).append("</sides>\n");
    stringBuilder.append("<rotation>").append(polygon.rotation()).append("</rotation>\n");
    setColor(polygon.color());
    stringBuilder.append("</regularPolygon>\n");
  }

  @Override
  public void buildEllipsoid(final Ellipsoid ellipsoid) {}

  @Override
  public void buildCircle(final Circle circle) {
    stringBuilder.append("<circle>\n");
    setCoordinates(circle);
    setCompleteSize(circle);
    stringBuilder.append("<radius>").append(circle.radius()).append("</radius>\n");
    setColor(circle.color());
    stringBuilder.append("</circle>\n");
  }

  @Override
  public void buildRectangle(final Rectangle rectangle) {
    stringBuilder.append("<rectangle>\n");
    setCoordinates(rectangle);
    setCompleteSize(rectangle);
    setColor(rectangle.color());
    stringBuilder.append("<fill>").append(rectangle.isFill()).append("</fill>\n");
    stringBuilder.append("</rectangle>\n");
  }

  public String getResult() {

    return stringBuilder.toString();
  }

  private void setCoordinates(final Shape shape) {
    stringBuilder.append("<coordinates>\n");
    stringBuilder.append("<x>").append(shape.x()).append("</x>\n");
    stringBuilder.append("<y>").append(shape.y()).append("</y>\n");
    stringBuilder.append("<zIndex>").append(shape.zIndex()).append("</zIndex>\n");
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
    stringBuilder.append("<width>").append(shape.width()).append("</width>\n");
    stringBuilder.append("<height>").append(shape.height()).append("</height>\n");
    stringBuilder.append("</size>\n");
  }
}
