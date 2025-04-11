package ubx.archilog.model.io;

import ubx.archilog.model.*;

public class XmlBuilder implements FileBuilder {

  private final StringBuilder sb;

  public XmlBuilder() {
    sb = new StringBuilder();
  }

  @Override
  public void beginDocument() {
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    sb.append("<shapeEditor>\n");
  }

  @Override
  public void endDocument() {
    sb.append("</shapeEditor>\n");
  }

  @Override
  public void beginGroup(Group group) {
    sb.append("<group>\n");
    setCoordinates(group);
    setCompleteSize(group);
    parseGroup(group);
  }

  public void parseGroup(Group group) {
    for (final Shape s : group.getShapes()) {
      if (s instanceof Square) {
        buildSquare((Square) s);
      } else if (s instanceof Rectangle) {
        buildRectangle((Rectangle) s);
      } else if (s instanceof Circle) {
        buildCircle((Circle) s);
      } else if (s instanceof Group) {
        beginGroup((Group) s);
        parseGroup((Group) s);
        endGroup();
      }
    }
  }

  @Override
  public void endGroup() {
    sb.append("</group>\n");
  }

  @Override
  public void beginToolBar() {
    sb.append("<toolBar>\n");
    ToolBar toolBar = Model.getInstance().getToolBar();
    setCoordinates(toolBar);
    setCompleteSize(toolBar);
    parseGroup(toolBar);
  }

  @Override
  public void endToolBar() {
    sb.append("</toolBar>\n");
  }

  @Override
  public void beginCanvas() {
    sb.append("<canvas>\n");
    Group canvas = Model.getInstance().getCanvas();
    setCoordinates(canvas);
    setCompleteSize(canvas);
    parseGroup(canvas);
  }

  @Override
  public void endCanvas() {
    sb.append("</canvas>\n");
  }

  @Override
  public void buildPolygon(Polygon polygon) {
    sb.append("<polygon>\n");
    sb.append("<sides>").append(polygon.getSides()).append("</sides>\n");
    sb.append("</polygon>\n");
  }

  @Override
  public void buildEllipsoid(Ellipsoid ellipsoid) {}

  @Override
  public void buildCircle(Circle circle) {
    sb.append("<circle>\n");
    setCoordinates(circle);
    sb.append("<radius>").append(circle.getRadius()).append("</radius>\n");
    setColor(circle.getColor());
    sb.append("</circle>\n");
  }

  @Override
  public void buildRectangle(Rectangle rectangle) {
    sb.append("<rectangle>\n");
    setCoordinates(rectangle);
    setCompleteSize(rectangle);
    setColor(rectangle.getColor());
    sb.append("<fill>").append(rectangle.isFill()).append("</fill>\n");
    sb.append("</rectangle>\n");
  }

  @Override
  public void buildSquare(Square square) {
    sb.append("<square>\n");
    setCoordinates(square);
    sb.append("<size>\n");
    sb.append("<width>").append(square.getWidth()).append("</width>\n");
    sb.append("</size>\n");
    setColor(square.getColor());
    sb.append("</square>\n");
  }

  public String getResult() {

    return sb.toString();
  }

  private void setCoordinates(Shape s) {
    sb.append("<coordinates>\n");
    sb.append("<x>").append(s.getX()).append("</x>\n");
    sb.append("<y>").append(s.getY()).append("</y>\n");
    sb.append("<zIndex>").append(s.getZindex()).append("</zIndex>\n");
    sb.append("</coordinates>\n");
  }

  private void setColor(Color color) {
    sb.append("<color>\n");
    sb.append("<r>").append(color.r()).append("</r>\n");
    sb.append("<g>").append(color.g()).append("</g>\n");
    sb.append("<b>").append(color.b()).append("</b>\n");
    sb.append("<a>").append(color.a()).append("</a>\n");
    sb.append("</color>\n");
  }

  private void setCompleteSize(Shape s) {
    sb.append("<size>\n");
    sb.append("<width>").append(s.getWidth()).append("</width>\n");
    sb.append("<height>").append(s.getHeight()).append("</height>\n");
    sb.append("</size>\n");
  }
}
