/**
 * 
 */
package com.hzmd.common.validator;

/**
 * @author yangming
 *
 */
public class ValidateException extends RuntimeException {

  private static final long serialVersionUID = -8704920861912678059L;

  public ValidateException() {
  }

  public ValidateException(String message) {
    super(message);
  }

  public ValidateException(Throwable cause) {
    super(cause);
  }

  public ValidateException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
