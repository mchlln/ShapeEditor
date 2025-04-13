package ubx.archilog.model.visitor;

import java.util.ArrayList;
import java.util.List;
import ubx.archilog.model.shapes.*;

public class IsInVisitor implements ShapeVisitor {
  private final int xVisit;
  private final int yVisit;
  private final List<Shape> result;

  public IsInVisitor(final int xVisit, final int yVisit) {
    this.xVisit = xVisit;
    this.yVisit = yVisit;
    result = new ArrayList<Shape>();
  }

  private boolean isInShape(final Shape shape) {
    return xVisit >= shape.x()
        && yVisit >= shape.y()
        && xVisit <= shape.x() + shape.width()
        && yVisit <= shape.y() + shape.height();
  }

  @Override
  public void visit(final Circle circle) {
    System.out.println("VISITING circle");
    final double dx = xVisit - circle.x();
    final double dy = yVisit - circle.y();
    if (dx * dx + dy * dy <= circle.radius() * circle.radius()) {
      result.add(circle);
    }
  }

  @Override
  public void visit(final Rectangle rectangle) {
    if (isInShape(rectangle)) {
      result.add(rectangle);
    }
  }

  @Override
  public void visit(final Group group) {
    if (group.zIndex() == 0) {
      for (final Shape shape : group.shapes()) {
        if (isInShape(shape)) {
          result.add(shape);
        }
      }
    } else if (isInShape(group)) {
      result.add(group);
    }
  }

  public void visit(final Shape shape) {
    if (shape instanceof Circle) {
      visit((Circle) shape);
    } else if (shape instanceof Rectangle) {
      visit((Rectangle) shape);
    } else if (shape instanceof Group) {
      visit((Group) shape);
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
