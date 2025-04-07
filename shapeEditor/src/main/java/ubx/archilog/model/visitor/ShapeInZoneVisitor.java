package ubx.archilog.model.visitor;

import java.util.ArrayList;
import java.util.List;
import ubx.archilog.model.*;

public class ShapeInZoneVisitor implements ShapeVisitor {
  private int zoneX;
  private int zoneY;
  private int zoneWidth;
  private int zoneHeight;
  private List<Shape> result;

  public ShapeInZoneVisitor(int x, int y, int width, int height) {
    this.zoneX = x;
    this.zoneY = y;
    this.zoneWidth = width;
    this.zoneHeight = height;
    this.result = new ArrayList<>();
  }

  private boolean intersects(
      int shapeX,
      int shapeY,
      int shapeWidth,
      int shapeHeight,
      int zoneX,
      int zoneY,
      int zoneWidth,
      int zoneHeight) {

    return shapeX < zoneX + zoneWidth
        && shapeX + shapeWidth > zoneX
        && shapeY < zoneY + zoneHeight
        && shapeY + shapeHeight > zoneY;
  }

  @Override
  public void visit(Circle c) {
    if (c.getZindex() > 0
        && intersects(
            c.getX(), c.getY(), c.getWidth(), c.getHeight(), zoneX, zoneY, zoneWidth, zoneHeight)) {
      result.add(c);
    }
  }

  @Override
  public void visit(Square s) {
    if (s.getZindex() > 0
        && intersects(
            s.getX(), s.getY(), s.getWidth(), s.getHeight(), zoneX, zoneY, zoneWidth, zoneHeight)) {
      result.add(s);
    }
  }

  @Override
  public void visit(Rectangle r) {
    if (r.getZindex() > 0
        && intersects(
            r.getX(), r.getY(), r.getWidth(), r.getHeight(), zoneX, zoneY, zoneWidth, zoneHeight)) {
      result.add(r);
    }
  }

  @Override
  public void visit(Group g) {
    if (g.getZindex() == 0) {
      for (Shape s : g.getShapes()) {
        if (s.getZindex() > 0
            && intersects(
                s.getX(),
                s.getY(),
                s.getWidth(),
                s.getHeight(),
                zoneX,
                zoneY,
                zoneWidth,
                zoneHeight)) {
          result.add(s);
        }
      }
    } else if (intersects(
        g.getX(), g.getY(), g.getWidth(), g.getHeight(), zoneX, zoneY, zoneWidth, zoneHeight)) {
      result.add(g);
    }
  }

  @Override
  public void visit(AbstractShape s) {
    throw new UnsupportedOperationException();
  }

  public List<Shape> getResult() {
    List<Shape> copy = List.copyOf(result);
    result.clear();
    return copy;
  }
}
