/**
 * 
 */
package com.hzmd.common.validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hzmd.common.util.CollectionUtil;
import com.hzmd.common.util.StringUtil;

/**
 * @author yangming
 *
 */
public class ValidateResult {
  
  
  private boolean shortCircuit = false;
  
  private List<ValidateError> errorList;
  
  public ValidateResult() {
  }
  
  public ValidateResult(boolean shortCircuit) {
    this.shortCircuit = shortCircuit;
  }
  
  public String toErrorMsg_Ul(String clsName) {
    if(!hasError()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    if(StringUtil.isNotEmpty(clsName)) {
      sb.append("<ul class=\"").append(clsName).append("\">");
    } else {
      sb.append("<ul>");
    }
    for(ValidateError err : errorList) {
      sb.append("<li>").append(err.getMsg()).append("</li>");
    }
    sb.append("</ul>");
    return sb.toString();
  }
  
  public String toErrorMsg_P(String clsName) {
    if(!hasError()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for(ValidateError err : errorList) {
      sb.append("<p>").append(err.getMsg()).append("</p>");
    }
    return sb.toString();
  }
  
  public String toErrorMsg_Div(String clsName) {
    if(!hasError()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for(ValidateError err : errorList) {
      sb.append("<div>").append(err.getMsg()).append("</div>");
    }
    return sb.toString();
  }
  
  public String toErrorMsg_Br(String clsName) {
    if(!hasError()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for(ValidateError err : errorList) {
      sb.append(err.getMsg()).append("<br />");
    }
    return sb.toString();
  }
  
  public String toErrorMsg_CR() {
    if(!hasError()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for(ValidateError err : errorList) {
      sb.append(err.getMsg()).append("\n");
    }
    return sb.toString();
  }
  
  public String toErrorMsg_Ul() {
    return toErrorMsg_Ul(null);
  }
  
  public Map<String, Object> toMap_Ul(String clsName) {
    if(!hasError()) {
      return null;
    }
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    
    map.put("200", errorList.get(0).getKey());
    map.put("errorMsg", toErrorMsg_Ul(clsName));
    return map;
  }
  
  public Map<String, Object> toMap_Ul() {
    return toMap_Ul(null);
  }
  
  public Map<String, Object> toMap_CR() {
    if(!hasError()) {
      return null;
    }
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    map.put("firstKey", errorList.get(0).getKey());
    map.put("errorMsg", toErrorMsg_CR());
    return map; 
  }
  
  
  
  public void addError(String key, String msg) {
    if(errorList == null) {
      errorList = new ArrayList<ValidateError>();
    }
    errorList.add(new ValidateError(key, msg));
    if(shortCircuit) {
      throw new ValidateException();
    }
  }
  
  public boolean hasError() {
    return CollectionUtil.isNotEmpty(errorList);
  }

  public List<ValidateError> getErrorList() {
    return errorList;
  }

  public void setErrorList(List<ValidateError> errorList) {
    this.errorList = errorList;
  }
  
  public ValidateResult validateRequired(String value, String key, String msg) {
    if(StringUtil.isEmpty(value) || ("null").equals(value)) {
      addError(key, msg);
    }
    return this; 
  }
  
  public ValidateResult validateNotNull(Object value, String key, String msg) {
    if(value == null) {
      addError(key, msg);
    }
    return this;
  }
  
  /**
   * 字符长度在区间 [minLength, maxLength]
   * @param value
   * @param minLength >= 这个值
   * @param maxLength  <= 这个值
   * @param key
   * @param msg
   * @return
   */
  public ValidateResult validateLength(String value, int minLength, int maxLength, String key, String msg) {
    int length = value != null ? value.length() : 0;
    if(length < minLength || length > maxLength) {
      addError(key, msg);
    }
    return this;
  }
  
//  public ValidateResult validateInt(int value, int min, int max, String key, String msg) {
//    if(StringUtil.isEmpty(value)) {
//      addError(key, msg);
//    }
//    return this;
//  }

  public boolean isShortCircuit() {
    return shortCircuit;
  }

  public void setShortCircuit(boolean shortCircuit) {
    this.shortCircuit = shortCircuit;
  }
  
  
  
  

}
