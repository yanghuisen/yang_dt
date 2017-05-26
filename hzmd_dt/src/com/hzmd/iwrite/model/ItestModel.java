/**
 * 
 */
package com.hzmd.iwrite.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 所有Itest Model的
 * @author Yangming
 * @param <M>
 * @create  2015年6月4日
 */
@SuppressWarnings("rawtypes")
public abstract class ItestModel<M extends Model> extends Model<M> {

  /**
   * 
   */
  private static final long serialVersionUID = -5383967592401804279L;
  
  @Override
  public boolean _save() {
    return super._save();
  }
  //修改
  public boolean delete() {
    _evictMeFromCacheById();
    return _delete();
  }
  
  public boolean fakeDel() {
    return Db.update("UPDATE " + _tableName() + " SET delFlag=1 WHERE " + _getIdName() + "=?", _getIdValue()) == 1;
  }
  
  public boolean fakeDel(M model) {
    if(model != null) {
      return model.set("delFlag", 1).update();  
    } else {
      return false;
    }
  }

}
