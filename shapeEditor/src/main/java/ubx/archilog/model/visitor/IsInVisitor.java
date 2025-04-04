package ubx.archilog.model.visitor;

import ubx.archilog.model.*;

public class IsInVisitor implements ShapeVisitor {
  private final int xVisit;
  private final int yVisit;
  private boolean result;

  public IsInVisitor(int xVisit, int yVisit) {
    this.xVisit = xVisit;
    this.yVisit = yVisit;
  }

  @Override
  public void visit(Circle c) {}

  @Override
  public void visit(Square s) {
    result =
        xVisit >= s.getX()
            && yVisit >= s.getY()
            && xVisit <= s.getX() + s.getWidth()
            && yVisit <= s.getX() + s.getWidth();
  }

  @Override
  public void visit(Rectangle r) {
    result =
        xVisit >= r.getX()
            && yVisit >= r.getY()
            && xVisit <= r.getX() + r.getWidth()
            && yVisit <= r.getX() + r.getHeight();
  }

  @Override
  public void visit(Group g) {
    result =
        xVisit >= g.getX()
            && yVisit >= g.getY()
            && xVisit <= g.getX() + g.getWidth()
            && yVisit <= g.getX() + g.getHeight();
  }

  @Override
  public void visit(AbstractShape s) {}

  public boolean getResult() {
    return result;
  }
}
