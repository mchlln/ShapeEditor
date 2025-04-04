package ubx.archilog.model;

import ubx.archilog.view.Render;

public abstract class AbstractShape implements Shape {
  private Render render;

  @Override
  public void draw(Render render) {}

  @Override
  public void scale() {}

  @Override
  public void rotate() {}

  @Override
  public void translate() {}
}
