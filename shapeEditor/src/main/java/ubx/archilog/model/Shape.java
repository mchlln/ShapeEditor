package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public interface Shape {
  int getX();

  int getY();

  void draw(Render render);

  void scale();

  void rotate();

  void translate(int xDiff, int yDiff);

  void accept(ShapeVisitor visitor);

  Shape clone();
}
