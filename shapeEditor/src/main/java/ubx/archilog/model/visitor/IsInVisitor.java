package ubx.archilog.model.visitor;

import java.util.ArrayList;
import java.util.List;
import ubx.archilog.model.*;

public class IsInVisitor implements ShapeVisitor {
  private final int xVisit;
  private final int yVisit;
  private final List<Shape> result;

  public IsInVisitor(int xVisit, int yVisit) {
    this.xVisit = xVisit;
    this.yVisit = yVisit;
    result = new ArrayList<Shape>();
  }

  @Override
  public void visit(Circle c) {
    double dx = xVisit - c.getX();
    double dy = yVisit - c.getY();
    if (dx * dx + dy * dy <= c.getRadius() * c.getRadius()) {
      result.add(c);
    }
  }

  @Override
  public void visit(Square s) {
    if (xVisit >= s.getX()
        && yVisit >= s.getY()
        && xVisit <= s.getX() + s.getWidth()
        && yVisit <= s.getX() + s.getWidth()) {
      result.add(s);
    }
  }

  @Override
  public void visit(Rectangle r) {
    if (xVisit >= r.getX()
        && yVisit >= r.getY()
        && xVisit <= r.getX() + r.getWidth()
        && yVisit <= r.getX() + r.getHeight()) {
      result.add(r);
    }
  }

  @Override
  public void visit(Group g) {
    for (Shape s : g.getShapes()) {
      visit(s);
      if (result.contains(s) && !result.contains(g)) {
        result.add(g);
      }
    }
  }

  public void visit(Shape s) {
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
  public void visit(AbstractShape s) {
    throw new UnsupportedOperationException();
  }

  public List<Shape> getResult() {
    List<Shape> copy = List.copyOf(result);
    result.clear();
    return copy;
  }
}
