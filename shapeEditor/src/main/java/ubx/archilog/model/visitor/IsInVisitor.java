package ubx.archilog.model.visitor;

import java.util.ArrayList;
import java.util.List;
import ubx.archilog.model.*;

public class IsInVisitor implements ShapeVisitor {
  private final int xVisit;
  private final int yVisit;
  private final List<Shape> result;

  public IsInVisitor(final int xVisit, final int yVisit) {
    this.xVisit = xVisit;
    this.yVisit = yVisit;
    result = new ArrayList<Shape>();
  }

  private boolean isInShape(final Shape s) {
    return xVisit >= s.getX()
        && yVisit >= s.getY()
        && xVisit <= s.getX() + s.getWidth()
        && yVisit <= s.getY() + s.getHeight();
  }

  @Override
  public void visit(final Circle c) {
    System.out.println("VISITING circle");
    final double dx = xVisit - c.getX();
    final double dy = yVisit - c.getY();
    if (dx * dx + dy * dy <= c.getRadius() * c.getRadius()) {
      result.add(c);
    }
  }

  @Override
  public void visit(final Square s) {
    if (isInShape(s)) {
      result.add(s);
    }
  }

  @Override
  public void visit(final Rectangle r) {
    if (isInShape(r)) {
      result.add(r);
    }
  }

  @Override
  public void visit(final Group g) {
    if (g.getZindex() == 0) {
      for (final Shape s : g.getShapes()) {
        if (isInShape(s)) {
          result.add(s);
        }
      }
    } else if (isInShape(g)) {
      result.add(g);
    }
  }

  public void visit(final Shape s) {
    if (s instanceof Circle) {
      visit((Circle) s);
    } else if (s instanceof Square) {
      visit((Square) s);
    } else if (s instanceof Rectangle) {
      visit((Rectangle) s);
    } else if (s instanceof Group) {
      visit((Group) s);
    }
  }

  @Override
  public void visit(final AbstractShape s) {
    throw new UnsupportedOperationException();
  }

  public List<Shape> getResult() {
    final List<Shape> copy = List.copyOf(result);
    result.clear();
    return copy;
  }
}
