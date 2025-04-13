package ubx.archilog.model.visitor;

import java.util.ArrayList;
import java.util.List;
import ubx.archilog.model.shapes.*;

public class ShapeInZoneVisitor implements ShapeVisitor {
  private final int zoneX;
  private final int zoneY;
  private final int zoneWidth;
  private final int zoneHeight;
  private final List<Shape> result;

  public ShapeInZoneVisitor(final int x, final int y, final int width, final int height) {
    this.zoneX = x;
    this.zoneY = y;
    this.zoneWidth = width;
    this.zoneHeight = height;
    this.result = new ArrayList<>();
  }

  private boolean intersects(
      final int shapeX,
      final int shapeY,
      final int shapeWidth,
      final int shapeHeight,
      final int zoneX,
      final int zoneY,
      final int zoneWidth,
      final int zoneHeight) {

    return shapeX < zoneX + zoneWidth
        && shapeX + shapeWidth > zoneX
        && shapeY < zoneY + zoneHeight
        && shapeY + shapeHeight > zoneY;
  }

  @Override
  public void visit(final Circle circle) {
    if (circle.zIndex() > 0
        && intersects(
            circle.x(),
            circle.y(),
            circle.width(),
            circle.height(),
            zoneX,
            zoneY,
            zoneWidth,
            zoneHeight)) {
      result.add(circle);
    }
  }

  @Override
  public void visit(final Square square) {
    if (square.zIndex() > 0
        && intersects(
            square.x(),
            square.y(),
            square.width(),
            square.height(),
            zoneX,
            zoneY,
            zoneWidth,
            zoneHeight)) {
      result.add(square);
    }
  }

  @Override
  public void visit(final Rectangle rectangle) {
    if (rectangle.zIndex() > 0
        && intersects(
            rectangle.x(),
            rectangle.y(),
            rectangle.width(),
            rectangle.height(),
            zoneX,
            zoneY,
            zoneWidth,
            zoneHeight)) {
      result.add(rectangle);
    }
  }

  @Override
  public void visit(final Group group) {
    if (group.zIndex() == 0) {
      for (final Shape shape : group.shapes()) {
        if (shape.zIndex() > 0
            && intersects(
                shape.x(),
                shape.y(),
                shape.width(),
                shape.height(),
                zoneX,
                zoneY,
                zoneWidth,
                zoneHeight)) {
          result.add(shape);
        }
      }
    } else if (intersects(
        group.x(), group.y(), group.width(), group.height(), zoneX, zoneY, zoneWidth, zoneHeight)) {
      result.add(group);
    }
  }

  @Override
  public void visit(final AbstractShape shape) {
    throw new UnsupportedOperationException();
  }

  public List<Shape> getResult() {
    final List<Shape> copy = List.copyOf(result);
    result.clear();
    return copy;
  }
}
