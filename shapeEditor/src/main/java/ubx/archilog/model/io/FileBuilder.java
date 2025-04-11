package ubx.archilog.model.io;

import ubx.archilog.model.*;
import ubx.archilog.model.Polygon;
import ubx.archilog.model.Rectangle;

public interface FileBuilder {
  void beginDocument();

  void endDocument();

  void beginGroup(Group group);

  void endGroup();

  void beginToolBar();

  void endToolBar();

  void beginCanvas();

  void endCanvas();

  void buildPolygon(Polygon polygon);

  void buildEllipsoid(Ellipsoid ellipsoid);

  void buildCircle(Circle circle);

  void buildRectangle(Rectangle rectangle);

  void buildSquare(Square square);
}
