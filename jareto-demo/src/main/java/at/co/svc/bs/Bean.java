package at.co.svc.bs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * JSON-B bean with various contained data types.
 */
@JsonbPropertyOrder({ "id", "echo", "stringArray", "stringList", "stringMap", "bool", "boolWrapper", "bean" })
public class Bean {

  private int _id = -1;
  private String _echo;
  private String[] _stringArray;
  private List<String> _stringList;
  private Map<String, String> _stringMap;
  private boolean _bool;
  private Boolean _boolWrapper;
  private Bean _bean;

  public Bean() {
  }
  
  public Bean(Bean copyMe) {
    _id = copyMe._id;
    _echo = copyMe._echo;
    _stringArray = copyMe._stringArray == null ? null : Arrays.copyOf(copyMe._stringArray, copyMe._stringArray.length);
    _stringList = copyMe._stringList == null ? null : new LinkedList<String>(copyMe._stringList);
    _bool = copyMe._bool;
    setBoolWrapper(copyMe.getBoolWrapper() == null ? null : Boolean.valueOf(copyMe.getBoolWrapper()));
    _bean = copyMe._bean == null ? null : new Bean(copyMe._bean);
  }
  
  public int getId() {
    return _id;
  }

  public Bean setId(int id) {
    _id = id;
    return this;
  }

  public String getEcho() {
    return _echo;
  }

  public Bean setEcho(String echo) {
    _echo = echo;
    return this;
  }

  public String[] getStringArray() {
    return _stringArray;
  }

  public Bean setStringArray(String[] stringArray) {
    _stringArray = stringArray;
    return this;
  }

  public List<String> getStringList() {
    return _stringList;
  }

  public Bean setStringList(List<String> stringList) {
    _stringList = stringList;
    return this;
  }

  public Map<String, String> getStringMap() {
    return _stringMap;
  }

  public Bean setStringMap(Map<String, String> stringMap) {
    _stringMap = stringMap;
    return this;
  }

  public boolean getBool() {
    return _bool;
  }

  public Bean setBool(boolean bool) {
    _bool = bool;
    return this;
  }

  public Boolean getBoolWrapper() {
    return _boolWrapper;
  }

  public Bean setBoolWrapper(Boolean boolWrapper) {
    _boolWrapper = boolWrapper;
    return this;
  }

  public Bean getBean() {
    return _bean;
  }

  public Bean setBean(Bean bean) {
    _bean = bean;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Bean [_id=");
    builder.append(_id);
    builder.append(", _echo=");
    builder.append(_echo);
    builder.append(", _stringArray=");
    builder.append(Arrays.toString(_stringArray));
    builder.append(", _stringList=");
    builder.append(_stringList);
    builder.append(", _stringMap=");
    builder.append(_stringMap);
    builder.append(", _bool=");
    builder.append(_bool);
    builder.append(", _boolWrapper=");
    builder.append(_boolWrapper);
    builder.append(", _bean=");
    builder.append(_bean);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_bean == null) ? 0 : _bean.hashCode());
    result = prime * result + (_bool ? 1231 : 1237);
    result = prime * result + ((_boolWrapper == null) ? 0 : _boolWrapper.hashCode());
    result = prime * result + ((_echo == null) ? 0 : _echo.hashCode());
    result = prime * result + _id;
    result = prime * result + Arrays.hashCode(_stringArray);
    result = prime * result + ((_stringList == null) ? 0 : _stringList.hashCode());
    result = prime * result + ((_stringMap == null) ? 0 : _stringMap.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Bean other = (Bean) obj;
    if (_bean == null) {
      if (other._bean != null)
        return false;
    } else if (!_bean.equals(other._bean))
      return false;
    if (_bool != other._bool)
      return false;
    if (_boolWrapper == null) {
      if (other._boolWrapper != null)
        return false;
    } else if (!_boolWrapper.equals(other._boolWrapper))
      return false;
    if (_echo == null) {
      if (other._echo != null)
        return false;
    } else if (!_echo.equals(other._echo))
      return false;
    if (_id != other._id)
      return false;
    if (!Arrays.equals(_stringArray, other._stringArray))
      return false;
    if (_stringList == null) {
      if (other._stringList != null)
        return false;
    } else if (!_stringList.equals(other._stringList))
      return false;
    if (_stringMap == null) {
      if (other._stringMap != null)
        return false;
    } else if (!_stringMap.equals(other._stringMap))
      return false;
    return true;
  }

}
