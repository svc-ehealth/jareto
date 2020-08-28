package at.co.svc.bs;

import java.util.List;
import java.util.Map;

public class BeanFactory {

  public static Bean createBean() {
    Bean b = new Bean()
        .setId(42)
        .setEcho("echo")
        .setStringArray(new String[] { "sa1", "sa2"})
        .setStringList(List.of("sl1", "sl2"))
        .setStringMap(Map.of("smk1", "smv1", "smk2", "smv2"))
        .setBool(true)
        .setBoolWrapper(true);
    return b.setBean(new Bean(b));
  }

}
