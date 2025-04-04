package ubx.archilog.model;

import ubx.archilog.view.Render;

public interface Shape {
  void draw(Render render);

  void scale();

  void rotate();

  void translate();
}
