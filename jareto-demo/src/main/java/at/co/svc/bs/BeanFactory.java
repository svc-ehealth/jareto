package at.co.svc.bs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BeanFactory {

  public static Bean createBean() {
    
    List<String> stringList = new LinkedList<>();
    stringList.add("sl1");
    stringList.add("sl2");
    
    Map<String, String> stringMap = new HashMap<>();
    stringMap.put("smk1", "smv1");
    stringMap.put("smk2", "smv2");
    
    Bean b = new Bean()
        .setId(42)
        .setEcho("echo")
        .setStringArray(new String[] { "sa1", "sa2"})
        .setStringList(stringList)
        .setStringMap(stringMap)
        .setBool(true)
        .setBoolWrapper(true);
    
    return b.setBean(new Bean(b));
    
  }

}
