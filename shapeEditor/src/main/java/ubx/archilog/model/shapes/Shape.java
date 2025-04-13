package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;
import ubx.archilog.model.Position;
import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public interface Shape extends Cloneable {
  int x();

  int y();

  void x(int x);

  void y(int y);

  Color color();

  void color(Color color);

  int zIndex();

  void zIndex(int z);

  int width();

  void width(int width);

  int height();

  void height(int height);

  void moveTo(Position pos);

  void draw(Render render);

  void scale(float factor);

  void rotate();

  void translate(int xDiff, int yDiff);

  void accept(ShapeVisitor visitor);

  Shape clone();

  Memento save();
}
