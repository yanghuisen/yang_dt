/**
 * 
 */
package com.hzmd.common.validator;

/**
 * @author yangming
 *
 */
public class ValidateError {

  private String key;
  
  private String msg;
  
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public ValidateError() {
  }
  
  public ValidateError(String key, String msg) {
    this.key = key;
    this.msg = msg;
  }

}
