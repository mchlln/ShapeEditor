package ubx.archilog;

import ubx.archilog.model.*;
import ubx.archilog.model.io.FileBuilder;
import ubx.archilog.model.io.XmlBuilder;
import ubx.archilog.view.View;

public class Main {
  public static void main(String[] args) {
    View view = new View();
    Model.getInstance();
    FileBuilder builder = Model.getInstance().save(new XmlBuilder());
    if (builder instanceof XmlBuilder) {
      System.out.println(((XmlBuilder) builder).getResult());
    }

    /*

    Shape shape = new Circle(2, 4, 6);
    System.out.println(shape);
    shape.translate(0, 6);
    System.out.println(shape);
    shape.translate(1, 6);
    System.out.println(shape);
    Shape shape2 = new Square(6, 6, 6);
    Group group = new Group();
    group.add(shape);
    group.add(shape2);
    Shape group2 = group.clone();
    if (group2 instanceof Group) {
      ((Group) group2).remove(shape2);
    }
    System.out.println(group2.equals(group));
    System.out.println(group);
    System.out.println(group2);*/
  }
}
