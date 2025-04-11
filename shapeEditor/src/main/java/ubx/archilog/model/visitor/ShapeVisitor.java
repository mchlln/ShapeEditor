package ubx.archilog.model.visitor;

import ubx.archilog.model.shapes.*;

public interface ShapeVisitor {
  void visit(Circle c);

  void visit(Square s);

  void visit(Rectangle r);

  void visit(Group g);

  void visit(AbstractShape s);
}
