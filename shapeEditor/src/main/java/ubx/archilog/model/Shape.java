package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public interface Shape extends Cloneable {
  int getX();

  int getY();

  void setX(int x);

  void setY(int y);

  void setColor(Color color);

  int getZindex();

  void setZindex(int z);

  int getWidth();

  void setWidth(int width);

  int getHeight();

  void setHeight(int height);

  void moveTo(Position pos);

  void draw(Render render);

  void scale(float factor);

  void rotate();

  void translate(int xDiff, int yDiff);

  void accept(ShapeVisitor visitor);

  Shape clone();

  Memento save();
}
