package ubx.archilog.model.buttonActions;

import java.util.List;
import ubx.archilog.model.Model;
import ubx.archilog.model.Shape;
import ubx.archilog.model.visitor.IsInVisitor;

public class DeleteAction implements ButtonAction {
  @Override
  public void onDrag(Shape shape) {
    IsInVisitor isInVisitor = new IsInVisitor(shape.getX(), shape.getY());
    // check for shape in model
    Model.getInstance().getCanvas().accept(isInVisitor);
    List<Shape> isIn = isInVisitor.getResult();
    if (isIn != null) {
      Model.getInstance().getCanvas().remove(shape);
      return;
    }
    // check for shape in toolbar
    Model.getInstance().getToolBar().accept(isInVisitor);
    isIn = isInVisitor.getResult();
    if (isIn != null) {
      Model.getInstance().getToolBar().removeShapeFromToolBar(shape);
    }
  }

  @Override
  public void onClick() {
    throw new UnsupportedOperationException();
  }
}
