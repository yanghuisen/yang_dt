/**
 * 
 */
package com.hzmd.iwrite.web.controller;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.hzmd.iwrite.global.ITestCC;
import com.hzmd.iwrite.util.ItestUtil;

/**
 * {"f":-1,"s":0}, {"f":0,"s":0}, {"f":1,"s":100}
 * 
 * @author Yangming
 * @create  2015年8月3日
 */
@JSONType(ignores = {"score_Final"})
public class RateScore {
  
  @JSONField(name = "f")
  private int rightFlag = ITestCC.KaoshiShiCC.C_rateScoreRightFlag.UNKNOWN;

  /**
   * 分数 分数是实际分数乘以100, 这个分数是客观题评分，或者主观题老师打分，这个分数可以作为最终分数！！！
   * 
   */
  @JSONField(name = "s")
  private Integer score = null;
  
  /**
   * 是否需要调用远程批改服务
   */
  @JSONField(name = "nrr")
  private Boolean needRemoteRate = null; 
  
  /**
   * 机器评分，翻译题和作文批改 机器会打分
   */
  @JSONField(name = "ms")
  private Integer machineScore = null;
  
  /**
   * 机器打分附加信息 （例如机器评分的评语等信息）
   */
  @JSONField(name = "mri")
  private JSONObject machineRateInfo = null;
  
  public RateScore() {
  }
  
  public RateScore(int rightFlag) {
    this.rightFlag = rightFlag;
  }
  
  public RateScore(int rightFlag, Integer score) {
    this.rightFlag = rightFlag;
    this.score = score;
  }
  
  public RateScore(int rightFlag, Integer score, Integer machineScore) {
    this.rightFlag = rightFlag;
    this.score = score;
    this.machineScore = machineScore;
  }
  
  public RateScore(int rightFlag, Integer score, Integer machineScore, Boolean needRemoteRate) {
    this.rightFlag = rightFlag;
    this.score = score;
    this.machineScore = machineScore;
    this.needRemoteRate = needRemoteRate;
  }
  
  public RateScore(int rightFlag, Integer score, Integer machineScore, Boolean needRemoteRate, JSONObject machineRateInfo) {
    this.rightFlag = rightFlag;
    this.score = score;
    this.machineScore = machineScore;
    this.needRemoteRate = needRemoteRate;
    this.machineRateInfo = machineRateInfo;
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{").append((needRemoteRate != null && needRemoteRate.booleanValue()) ? "需要调用远程批改;" : "")
      .append(ITestCC.KaoshiShiCC.C_rateScoreRightFlag.me.getName(this.rightFlag)).append(";")
      .append(machineScore != null ? "机器批改分数:" + machineScore + ";" : "")
      .append("score=").append(score).append(";")
      .append(machineRateInfo != null ? "机器批改信息:" + machineRateInfo.toJSONString() + ";" : "")
      .append("}")
      ;
    return sb.toString();
  }
  
  public String _getScoreShow() {
    return ItestUtil.scoreToShow(getScore());
  }
  
  public String _getMachineScoreShow() {
    return ItestUtil.scoreToShow(getMachineScore());
  }
  
  public String _getScore_FinalShow() {
    return ItestUtil.scoreToShow(getScore_Final());
  }
  
  /**
   * 是否已经打过分！ (客观题机器评分，或)
   * @return
   */
  public boolean _isHasScore() {
    return score != null;
  }
  
  /**
   * 机器是否打分
   * @return
   */
  public boolean _isHasMachineScore() {
    return machineScore != null;
  }
  
  /**
   * 这个题目是否需要远程批改（例如调用iwrite作文引擎评分）
   * @return
   */
  public boolean _isNeedRemoteRate() {
    return this.needRemoteRate != null ? this.needRemoteRate.booleanValue() : false;
  }
  
  /**
   * 这个题目的远程批改是否已经结束？（当不需要远程批改时返回true）
   * @return
   */
  public boolean _isRemoteRateDone() {
    return !_isNeedRemoteRate() || (this.machineScore != null && this.machineRateInfo != null);
  }
  
//  /**
//   * TODO 貌似这个方法没有必要！ 
//   * 判断这个题目是不是远程判分结果有了 <br />
//   * 跟 {@link #_isRemoteRateDone()} 方法的区别是，这个方法只判断远程结果部分， <br />
//   * {@link #_isRemoteRateDone()} 方法 对于 _isNeedRemoteRate()返回false的也判断为true <br />
//   * @return
//   */
//  public boolean _isRemoteRateDoneTrue() {
//    return this.machineScore != null && this.machineRateInfo != null;
//  }
  
  public boolean _isRight() {
    return this.rightFlag == ITestCC.KaoshiShiCC.C_rateScoreRightFlag.RIGHT; 
  }
  
  public boolean _isWrong() {
    return this.rightFlag == ITestCC.KaoshiShiCC.C_rateScoreRightFlag.WRONG;
  }
  
  public boolean _isUnknown() {
    return this.rightFlag == ITestCC.KaoshiShiCC.C_rateScoreRightFlag.UNKNOWN;
  }
  
  public RateScore _setRight() {
    this.rightFlag = ITestCC.KaoshiShiCC.C_rateScoreRightFlag.RIGHT;
    return this;
  }
  
  public RateScore _setUnknown() {
    this.rightFlag = ITestCC.KaoshiShiCC.C_rateScoreRightFlag.UNKNOWN;
    return this;
  }
  
  public RateScore _setWrong() {
    this.rightFlag = ITestCC.KaoshiShiCC.C_rateScoreRightFlag.WRONG;
    return this;
  }
  
  public RateScore _setNeedRemoteRate() {
    this.needRemoteRate = true;
    return this;
  }

  public int getRightFlag() {
    return rightFlag;
  }

  public RateScore setRightFlag(int rightFlag) {
    this.rightFlag = rightFlag;
    return this;
  }

  /**
   * 原来的 getScore 方法继续保留。
   */
  public Integer getScore() {
    return score;
  }
  
  /**
   * 原来的 getScore 方法， 改成结合机器评分
   */
  public Integer getScore_Final() {
    if(score != null) {
      return score;
    }
    return machineScore;
  }
  
  /**
   * 是不是只有机评分数，没有老师打分
   * @return
   */
  public boolean _isOnlyHasMachineScore() {
    return _isHasMachineScore() && score == null;
  }
  
  public int _getScoreVal() {
    return score != null ? score.intValue() : 0;
  }
  
  public JSONObject _getMachineRateInfo_IWriteScore_Safe(String itemKey) {
    JSONObject ret = null;
    if(this.machineRateInfo != null) {
      try {
        // {"total":{"score":1050,"grade":3.5,"name":"total","comment":"语法结构多样且准确率高；能够使用较为复杂的词汇及有限的句式结构，复杂句欠缺准确性；文章内容基本切题，上下文偶尔会出现不连贯；文章分段较为清晰，段落间缺乏必要的衔接手段；能够掌握英语写作规范，使用正确且恰当的拼写、标点。","value":70},"organization":{"score":150,"grade":2,"name":"organization","comment":"文章分段较为清晰，段落间缺乏必要的衔接手段","value":10},"language":{"score":375,"grade":4,"name":"language","comment":"语法结构多样且准确率高；能够使用较为复杂的词汇及有限的句式结构，复杂句欠缺准确性","value":25},"mechanics":{"score":75,"grade":5,"name":"mechanics","comment":"能够掌握英语写作规范，使用正确且恰当的拼写、标点","value":5},"content":{"score":450,"grade":3.5,"name":"content","comment":"文章内容基本切题，上下文偶尔会出现不连贯","value":30}}
        JSONObject scores = machineRateInfo.getJSONObject("scores");
        if(scores != null) {
          ret = scores.getJSONObject(itemKey);
        }
      } catch(Exception e) {        
      }
    }
    if(ret == null) {
      ret = new JSONObject();
      ret.put("grade", 0);
      ret.put("score", 0);
      ret.put("name", itemKey);
      ret.put("comment", "");
      ret.put("value", 0);
    }
    if(ret.get("grade") == null) {
      ret.put("grade", 0);
    }
    return ret;
  }
  
  //<<<---add by yangsheng 2016-07-13 增加口语机器评分解析
  public String _getMachineRateInfo_ChivoxScore_Safe(String itemKey) {
	    String temp = null;
	    if(this.machineRateInfo != null) {
	      try {
	    	  if(itemKey.equals("fluency")){
	    		  temp = machineRateInfo.getJSONObject(itemKey).getString("overall");
	    	  }else{
	    		  if(itemKey.equals("fluency_paragraph"))itemKey = "fluency";
	    		  temp = machineRateInfo.getString(itemKey);
	    	  }
	    	  if(itemKey.equals("wavetime")){
	    		  temp = (Integer.parseInt(temp) / 1000) + "";
	    	  }
	      } catch(Exception e) {        
	      }
	      
	    }
	    if(temp == null)
	    	  temp = "0";
	    return temp;
  }
  
  /**
   * 转换分数
   * @param score
   * @param bScore
   * @return
   */
  public int covertScore(Object score,Object bScore){
	  int reScore = 0 ;
	  int sc =  Integer.parseInt(score.toString());
	  int bsc = Integer.parseInt(bScore.toString());
	  double s = bsc * (sc/100.0);
	  reScore = (new BigDecimal(s).setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();
	  return reScore;
  }
  
  /**
   * 将音素转换为对应的音标
   * @param phone
   * @return
   */
  
  public JSONArray _getMachineRateInfo_ChivoxScore_Obj_Safe(String itemKey) {
	    JSONArray temp = null;
	    if(this.machineRateInfo != null) {
	      try {
	    	  if(itemKey.equals("phone")){
	    		  temp = machineRateInfo.getJSONArray("details").getJSONObject(0).getJSONArray(itemKey);
	    	  }else{
	    		  temp = machineRateInfo.getJSONArray(itemKey);
	    	  }
	    	 
	      } catch(Exception e) {        
	      }
	    }
	    if(temp == null)
	    	  temp = new JSONArray();
	    return temp;
  }
 //--->>>add by yangsheng 增加口语机器评分解析
  
  public String _getMachineRateInfo_Uuid() {
    String uuid = null;
    if(this.machineRateInfo != null) {
      uuid =  this.machineRateInfo.getString("uuid");
    }
    if(uuid == null) {
      uuid = "";
    }
    return uuid;
  }
  
/*
                        "scores": {
                            "total": {
                                "score": 1050, 
                                "grade": 3.5, 
                                "name": "total", 
                                "comment": "语法结构多样且准确率高；能够使用较为复杂的词汇及有限的句式结构，复杂句欠缺准确性；文章内容基本切题，上下文偶尔会出现不连贯；文章分段较为清晰，段落间缺乏必要的衔接手段；能够掌握英语写作规范，使用正确且恰当的拼写、标点。", 
                                "value": 70
                            }, 
                            "organization": {
                                "score": 150, 
                                "grade": 2, 
                                "name": "organization", 
                                "comment": "文章分段较为清晰，段落间缺乏必要的衔接手段", 
                                "value": 10
                            }, 
                            "language": {
                                "score": 375, 
                                "grade": 4, 
                                "name": "language", 
                                "comment": "语法结构多样且准确率高；能够使用较为复杂的词汇及有限的句式结构，复杂句欠缺准确性", 
                                "value": 25
                            }, 
                            "mechanics": {
                                "score": 75, 
                                "grade": 5, 
                                "name": "mechanics", 
                                "comment": "能够掌握英语写作规范，使用正确且恰当的拼写、标点", 
                                "value": 5
                            }, 
                            "content": {
                                "score": 450, 
                                "grade": 3.5, 
                                "name": "content", 
                                "comment": "文章内容基本切题，上下文偶尔会出现不连贯", 
                                "value": 30
                            }
                        }  
 */

  public RateScore setScore(Integer score) {
    this.score = score;
    return this;
  }

  public Integer getMachineScore() {
    return machineScore;
  }

  public void setMachineScore(Integer machineScore) {
    this.machineScore = machineScore;
  }

  public JSONObject getMachineRateInfo() {
    return machineRateInfo;
  }

  public void setMachineRateInfo(JSONObject machineRateInfo) {
    this.machineRateInfo = machineRateInfo;
  }

  public Boolean getNeedRemoteRate() {
    return needRemoteRate;
  }

  public void setNeedRemoteRate(Boolean needRemoteRate) {
    this.needRemoteRate = needRemoteRate;
  }
  
  

  
}
