package ubx.archilog.model.visitor;

import ubx.archilog.model.*;

public class DrawVisitor implements ShapeVisitor {
  @Override
  public void visit(Circle c) {}

  @Override
  public void visit(Square s) {}

  @Override
  public void visit(Rectangle r) {}

  @Override
  public void visit(Group g) {}

  @Override
  public void visit(AbstractShape s) {}
}
