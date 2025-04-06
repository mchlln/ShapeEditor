package ubx.archilog.model.io;

import ubx.archilog.model.*;

public interface FileBuilder {
  void beginDocument();

  void endDocument();

  void beginGroup();

  void endGroup();

  void beginToolBar();

  void endToolBar();

  void buildPolygon(Polygon polygon);

  void buildEllipsoid(Ellipsoid ellipsoid);

  void buildCircle(Circle circle);

  void buildRectangle(Rectangle rectangle);
}
