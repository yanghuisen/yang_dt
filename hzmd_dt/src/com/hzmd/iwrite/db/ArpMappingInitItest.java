/**
 * 
 */
package com.hzmd.iwrite.db;

import com.hzmd.iwrite.model.DictionaryDefinfoModel;
import com.hzmd.iwrite.model.DictionaryWordsModel;
import com.hzmd.iwrite.model.ErrorSumModel;
import com.hzmd.iwrite.model.InformactionModel;
import com.hzmd.iwrite.model.QueryInformationModel;
import com.hzmd.iwrite.model.WordModel;
import com.jfinal.ext.arp.ArpMappingInit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * @author yangming
 *
 */
public class ArpMappingInitItest implements ArpMappingInit {

  /**
   * 
   */
  private ArpMappingInitItest() {
  }

  public static final ArpMappingInitItest me = new ArpMappingInitItest();

  @Override
  public void initArpMapping(ActiveRecordPlugin me) {
	 // me.addMapping("dt_group", "id",GroupModel.class);
	  me.addMapping("basic_information", InformactionModel.class);
	  me.addMapping("error_sum", ErrorSumModel.class);
	  me.addMapping("query_information", QueryInformationModel.class);
	  me.addMapping("word", WordModel.class);
  }

}
