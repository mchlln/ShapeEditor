package ubx.archilog.model.io;

import ubx.archilog.model.*;

public class XmlBuilder implements FileBuilder {

  StringBuilder sb;

  public XmlBuilder() {
    sb = new StringBuilder();
  }

  @Override
  public void beginDocument() {
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sb.append("<shapeEditor>");
  }

  @Override
  public void endDocument() {
    sb.append("</shapeEditor>");
  }

  @Override
  public void beginGroup() {
    sb.append("<group>");
  }

  @Override
  public void endGroup() {
    sb.append("</group>");
  }

  @Override
  public void beginToolBar() {
    sb.append("<toolBar>");
  }

  @Override
  public void endToolBar() {
    sb.append("</toolBar>");
  }

  @Override
  public void buildPolygon(Polygon polygon) {
    sb.append("<polygon>");
    // sb.append();
    sb.append("</polygon>");
  }

  @Override
  public void buildEllipsoid(Ellipsoid ellipsoid) {}

  @Override
  public void buildCircle(Circle circle) {
    sb.append("<circle>");
    sb.append("<coordinates>");
    sb.append("<x>").append(circle.getX()).append("</x>");
    sb.append("<y>").append(circle.getY()).append("</y>");
    sb.append("</coordinates>");
    sb.append("<radius>").append("").append("</radius>"); // TODO: ADD RADIUS
    sb.append("<color>").append(circle.getColor()).append("</color>");
    sb.append("</circle>");
  }

  @Override
  public void buildRectangle(Rectangle rectangle) {
    sb.append("<rectangle>");
    sb.append("<coordinates>");
    sb.append("<x>").append(rectangle.getX()).append("</x>");
    sb.append("<y>").append(rectangle.getY()).append("</y>");
    sb.append("</coordinates>");
    sb.append("<size>");
    sb.append("<width>").append(rectangle.getWidth()).append("</width>");
    sb.append("<height>").append(rectangle.getHeight()).append("</height>");
    sb.append("</size>");
    sb.append("<color>").append(rectangle.getColor()).append("</color>");
    sb.append("</rectangle>");
  }

  public String getResult() {
    return sb.toString();
  }
}
