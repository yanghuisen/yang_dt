package com.hzmd.iwrite.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hzmd.common.biz.consts.BizConstant;
import com.hzmd.common.util.CollectionUtil;
import com.hzmd.common.util.FileUtil;
import com.hzmd.common.util.StringUtil;

/**
 * ITest系统用到所有的编程常量都在这里定义！ <br />
 * 之前分散的需要重构到这里来  <br />
 * 每部分定义的注释中加上author！ <br />
 * @TODO  
 * TODO
 * 
 */
public class ITestCC {

  /**
   * 考试成绩统计相关的常量
   * @author Yangming
   * @create  2015年8月20日
   */
  public static final class KaoshiTongji {

    public static final class C_ksType {
      // 0: 学校考试  1: 班级考试
      public static final int SCHOOL = 0;
      public static final int CLS = 1;

      public static final BizConstant me = new BizConstant(new Object[][] { { "学校考试", SCHOOL }, { "班级考试", CLS } });

      public static boolean isSchoolKs(int ksType) {
        return SCHOOL == ksType;
      }

      public static boolean isClsKs(int ksType) {
        return CLS == ksType;
      }

      public static boolean isSet(int ksType) {
        return ksType == SCHOOL || ksType == CLS;
      }
    }
  }

  public static final class WYS_Owner {
    public static int WYS_SHCOOL_ID = 1;
    public static String WYS_CREATOR = "外研社";

    public static final class C_Owner {
      // 0: 还没有提交  1:提交等待审核 4:审核未通过，打回重提 10:审核通过，可以使用

      public static final int DEFAULT = 0;
      public static final int ZIJIAN = 1;
      public static final int WYS = 2;

      public static final BizConstant bankMe = new BizConstant(new Object[][] { { "全部", DEFAULT }, { "自建题库", ZIJIAN }, { "iTEST题库", WYS } });
      public static final BizConstant paperMe = new BizConstant(new Object[][] { { "全部", DEFAULT }, { "自建试卷", ZIJIAN }, { "iTEST试卷", WYS } });
      public static final BizConstant templateMe = new BizConstant(new Object[][] { { "全部", DEFAULT }, { "自建模板", ZIJIAN }, { "iTEST模板", WYS } });
    }
  }

  /**
   * 判断是否属于外研社
   * @param ownerId
   * @return
   */
  public static boolean isWYS(int ownerId) {
    return WYS_Owner.WYS_SHCOOL_ID == ownerId || ownerId == 0;
  }

  /*public static boolean isWYS() {
    //int ownerId = SchoolService.me.getMySchoolId();
    //return isWYS(ownerId);
  }
*/
  /*public static boolean canEdit(int ownerId) {
    if (isWYS(ownerId) && isWYS()) {
      return true;
    }
    if (!isWYS(ownerId))
      return true;
    return false;
  }*/

  /**
   * 考试组卷 审卷方面的常量
   * @author Yangming
   * @create  2015年7月14日
   */
  public static final class KaoshiPaperTaskCC {

    public static final class C_taskStatus {
      // 0: 还没有提交  1:提交等待审核 4:审核未通过，打回重提 10:审核通过，可以使用

      public static final int NOT_SUBMIT = 0;
      public static final int SUBMIT_WAITING_FOR_AUDIT = 1;
      public static final int AUDIT_REFUSED = 4;
      public static final int AUDIT_PASS = 10;

      public static final BizConstant me = new BizConstant(new Object[][] { { "未提交", NOT_SUBMIT }, { "等待审核", SUBMIT_WAITING_FOR_AUDIT },
          { "审核不通过", AUDIT_REFUSED }, { "审核通过，可使用", AUDIT_PASS } });
    }
  }

  /**
   * 班级测试相关的常量定义
   */
  public static final class ClsExamCC {
    public static final class C_processStep {
      public static final int PREPARE = 0;
      public static final int PREPARE_DONE = 1;
      public static final int KAOSHI = 2;
      public static final int YUEJUAN = 3;
      public static final int GUIDANG = 4;

      public static final BizConstant me = new BizConstant(new Object[][] { { "准备阶段", PREPARE }, { "准备完成", PREPARE_DONE }, { "考试阶段", KAOSHI },
          { "阅卷阶段", YUEJUAN }, { "归档阶段", GUIDANG } });
    }

    public static final class C_finish {
      public static final int FINISH = 2;
      public static final int DRAFT = 1;
      public static final int NOT_FINISH = 0;

      public static final BizConstant me = new BizConstant(new Object[][] { { "未完成", NOT_FINISH }, { "草稿", DRAFT }, { "已完成", FINISH } });

      public static final BizConstant kaoshiStatus = new BizConstant(new Object[][] { { "未考", NOT_FINISH }, { "考试中", DRAFT }, { "考完", FINISH } });
    }

    public static final class C_rateStatus {
      public static final int NOT_RATE = 0;
      public static final int DRAFT = 1;
      public static final int DONE = 10;

      public static final BizConstant me = new BizConstant(new Object[][] { { "未阅", NOT_RATE }, { "阅卷中", DRAFT }, { "已阅", DONE } });
    }
  }

  public static final class KaoshiShiCC {

    /**
     * 
     * ITestCC.KaoshiShiCC.C_realRateType 真正的批改类型
     *  0: CHUN_ZHUGUAN  完全的主观题，只能由人工阅卷
     *  1: CHUN_KEGUAN  完全的客观题，机器判断即可
     *  2: BAN_ZHUGUAN  半主观题， 其实跟客观题一样先用程序自动判分
     * 10: KOUYU  口语题，其实也是主观题，但显示方式不一样！（调用mp3显示）
     * 11: FANYI  翻译题，机器会打机器评分，教师如果打分会覆盖机器的分数。
     * 12: ZUOWEN  作文题，需要调用远程的批改引擎打分和评语！
     * @author Yangming
     *
     */
    public static final class C_realRateType {
      
      /**
       * 完全的主观题，只能由人工阅卷
       */
      public static final int CHUN_ZHUGUAN = 0;
      
      /**
       * 完全的客观题，机器判断即可
       */
      public static final int CHUN_KEGUAN = 1;
      
      /**
       * 半主观题， 其实跟客观题一样先用程序自动判分
       */
      public static final int BAN_ZHUGUAN = 2;
      
      /**
       * 口语题，其实也是主观题，但显示方式不一样！（调用mp3显示）
       */
      public static final int KOUYU = 10;
      
      /**
       * 翻译题，机器会打机器评分，教师如果打分会覆盖机器的分数。
       */
      public static final int FANYI = 11;
      
      /**
       * 作文题，需要调用远程的批改引擎打分和评语！
       */
      public static final int ZUOWEN = 12;
      
      public static final BizConstant me = new BizConstant(new Object[][] { 
        { "纯主观题", CHUN_ZHUGUAN },
        { "纯客观题", CHUN_KEGUAN },
        { "半主观题", BAN_ZHUGUAN },
        { "口语题", KOUYU },
        { "翻译题", FANYI },
        { "作文题", ZUOWEN }
      });
      
      public static boolean isHasMachineRate(int rateType) {
        return rateType == FANYI || rateType == ZUOWEN;
      }
    }
    
    /**
     * 
     * ITestCC.KaoshiShiCC.C_invokeRemoteRateFlag 是否调用远程批改服务的标识
     * 0: NEVER_INVOKE  禁止不请求远程批改服务
     * 1: INVOKE_WHEN_NEEDED  当题目需要请求远程批改服务，并且没有请求过就按需请求
     * 2: FORCE_INVOKE  只要题目需要请求远程批改服务，不管有没有请求过，都强制请求
     * @author Yangming
     *
     */
    public static final class C_invokeRemoteRateFlag {
      
      /**
       * 打死也不请求远程批改服务
       */
      public static final int NEVER_INVOKE = 0;
      
      /**
       * 当题目需要请求远程批改服务，并且没有请求过就按需请求
       */
      public static final int INVOKE_WHEN_NEEDED = 1;
      
      /**
       * 只要题目需要请求远程批改服务，不管有没有请求过，都强制请求
       */
      public static final int FORCE_INVOKE = 2;
      
      public static final BizConstant me = new BizConstant(new Object[][] { { "决不请求", NEVER_INVOKE }, { "按需请求", INVOKE_WHEN_NEEDED },
        { "强制请求", FORCE_INVOKE } });
    }
    
    /**
     * 
     * 
     * @author Yangming
     */
    public static final class C_showKaoshiTimeType {

      public static final int CHANGCI = 0;
      public static final int RESTRICTION_TIME = 1;
      public static final int DIY = 2;

      public static final BizConstant me = new BizConstant(new Object[][] { { "显示考生场次时间", CHANGCI }, { "显示限制进入考试的起止时间", RESTRICTION_TIME }, { "手工录入 ", DIY } });
    }

    /**
     * 
     * 作弊/异常标志位 对应 it_ks_kaoshi_pici_kaochang_user 表的 cheatStatus 字段
     * @author Yangming
     */
    public static final class C_cheatStatus {

      public static final int NORMAL = 0;
      public static final int CHEAT = 1;
      public static final int EXCEPTION = 2;

      public static final BizConstant me = new BizConstant(new Object[][] { { "正常", NORMAL }, { "作弊", CHEAT }, { "异常", EXCEPTION }, });
    }

    /**
     * 阅卷是否正确的标志
     * @author Yangming
     * @create  2015年8月4日
     */
    public static final class C_rateScoreRightFlag {
      /**
       * 答案错误
       */
      public static final int WRONG = -1;

      /**
       * 主观题，或者答案正误不明确
       */
      public static final int UNKNOWN = 0;

      /**
       * 答案正确
       */
      public static final int RIGHT = 1;
      
      public static final BizConstant me = new BizConstant(new Object[][] { { "答案正确未知", UNKNOWN },  { "答案错误", WRONG }, { "答案正确", RIGHT }
      });
      
    }

    /**
     * 考试安排中的delFlag数据，跟一般的delFlag相比较有特殊含义。 <br />
     * 因为安排一个批次之后，前一个批次的安排数据全部置为  BATCH_INVALID (4) <br />
     * 如果是置为1，就会跟本身是1的值混淆了！ 所以增加一个状态 <br />
     * 涉及的表有 it_ks_kaoshi_arrange_opt, it_ks_kaoshi_kaochang, it_ks_kaoshi_pici, it_ks_kaoshi_pici_kaochang,
     * it_ks_kaoshi_pici_kaochang_user   5个表  其中 it_ks_kaoshi_pici_kaochang_user还有自己定义的一个没有安排试卷的标识！
     * 
     * @author Yangming
     * @create  2015年7月27日
     */
    public static final class C_arrangeDelFlag {

      public static final int NORMAL = 0;
      public static final int DELETED = 1;
      public static final int BATCH_INVALID = 4;
      public static final int KAOSHI_DELETED = 9;

      public static final BizConstant me = new BizConstant(new Object[][] { { "正常", NORMAL }, { "删除", DELETED }, { "批次失效", BATCH_INVALID },
          { "考试被删除", KAOSHI_DELETED }, });
    }

    public static final class C_processStep {
      public static final int PREPARE = 0;
      public static final int PREPARE_DONE = 1;
      public static final int KAOSHI = 2;
      public static final int YUEJUAN = 3;
      public static final int GUIDANG = 4;

      public static final BizConstant me = new BizConstant(new Object[][] { { "准备阶段", PREPARE }, { "准备完成", PREPARE_DONE }, { "考试阶段", KAOSHI },
          { "阅卷阶段", YUEJUAN }, { "归档阶段", GUIDANG } });
    }

    public static final class C_answerMatchType {
      public static final int VAGUE = 0;
      public static final int ACCURATE = 1;

      public static final BizConstant me = new BizConstant(new Object[][] { { "模糊匹配(忽略大小写)", VAGUE }, { "精确匹配", ACCURATE } });
    }

    public static final class C_paperRandType {
      public static final int RAND_BY_POOL = 0;
      public static final int RAND_BY_PAPER = 1;

      public static final BizConstant me = new BizConstant(new Object[][] { { "多套试卷横向打乱", RAND_BY_POOL }, { "随机试卷", RAND_BY_PAPER } });
    }

    public static final class C_checkTimeType {
      public static final int AT_ONCE = -1;
      public static final int NOT_ALLOW_CHECK = -4;

      public static final BizConstant me = new BizConstant(new Object[][] { { "交卷后立即可以查看", AT_ONCE }, { "不允许查看", NOT_ALLOW_CHECK } });
    }

    public static final class C_csStrictType {
      public static final int NO = 0;
      public static final int YES = 1;

      public static final BizConstant me = new BizConstant(new Object[][] { { "否 ", NO }, { "是", YES } });
    }

    public static final class C_checkContentType {
      public static final int SCORE_AND_PAPER = 0;
      public static final int SCORE = 1;
      //public static final int PAPER = 2;

      public static final BizConstant me = new BizConstant(new Object[][] { { "成绩+试卷", SCORE_AND_PAPER }, { "成绩", SCORE }
      //, { "试卷", PAPER } 
          });
    }

    public static final class C_rateStatus {
      public static final int NOT_RATE = 0;
      public static final int DRAFT = 1;
      public static final int DONE = 10;

      public static final BizConstant me = new BizConstant(new Object[][] { { "未阅", NOT_RATE }, { "阅卷中", DRAFT }, { "已阅", DONE } });
    }

  }

  /**
   * 将废弃不用！
   * 考试试卷组卷任务、审卷任务方面的常量
   */
  public static final class KaoshiShijuanCC {
    // auditStatus tinyint(2)    NOT NULL DEFAULT 0 COMMENT '审核状态 0：未审核  1:审核通过 2:审核不通过',

    public static final class C_auditStatus {
      public static final int NOT_AUDIT = 0;
      public static final int AUDIT_PASS = 1;
      public static final int AUDIT_DECLINE = 2;

      public static final BizConstant me = new BizConstant(new Object[][] { { "未审核", NOT_AUDIT }, { "审核通过", AUDIT_PASS }, { "审核不通过", AUDIT_DECLINE } });
    }
  }

  public static final class UserCC {
    /**
     * 本系统三种用户类型，因为需要根据用户类型选择不同页面，所以编程中一些地方需要知道这个字段的含义。
     * @author yangming
     *
     */
    public static final class C_userType {
      public static final int STUDENT = 1;
      public static final int TEACHER = 10;
      public static final int ADMIN = 20;

      public static final BizConstant me = new BizConstant(new Object[][] { { "学生", STUDENT }, { "教师", TEACHER }, { "管理员", ADMIN } });
    }

    public static final class C_sex {
      public static final int unkown = 0; //未填
      public static final int male = 1; //男
      public static final int female = 2; //女

      public static final BizConstant me = new BizConstant(new Object[][] { { "男", male }, { "女", female } });

      public static final BizConstant all = new BizConstant(new Object[][] { { "未填", unkown }, { "男", male }, { "女", female } });

      public static BizConstant bizConstantOff() {
        return all;
      }

    }

    public static final class C_StepFlag {
      public static final int FirstLogin = 0; //首次登录，信息密码都没修改
      public static final int AllChange = 1; //密码和信息都修改了
    }

    /**
     * 登录来源
     * @author Yangming
     * @create  2015年8月15日
     */
    // 来源， web=1, gui client=10, ios=20 android=30
    public static final class C_deviceType {

      public static final int WEB = 1;

      public static final int GUI_CLIENT = 10;

      public static final int IOS = 20;

      public static final int ANDROID = 30;

    }

  }

  /**
   * 试卷模板的常量
   * @author Yangming
   */
  public static final class ExamPaperCC {
    /**
     * 试卷共享状态
     * @author Yangming
     * @create  2015年5月21日
     */
    public static final class C_shareStatus {
      /**
       * 试卷共享状态： 私有
       */
      public static final int SS_PRIVATE = 0;

      /**
       * 试卷共享状态： 共享，公开
       */
      public static final int SS_PUBLIC = 1;

      public static final BizConstant me = new BizConstant(new Object[][] { { "公开", SS_PUBLIC }, { "私有", SS_PRIVATE } });
    }

    // 对应ExamPaperModel对应的表中status字段：
    // status     tinyint(2)    NOT NULL DEFAULT 1 COMMENT '状态 2:已经发布可以使用，1:未发布，草稿， 0:隐藏/锁定/停用',
    public static final class C_status {
      public static final int PUBLISHED = 2;
      public static final int DRAFT = 1;
      public static final int HIDDEN = 0;

      public static final BizConstant me = new BizConstant(new Object[][] {
          // {"停用", HIDDEN}, 
          { "未发布", DRAFT }, { "已发布", PUBLISHED } });
    }
  }

  public static final class TplCC {

    public static final class Node_type {

      /**
       * 节点本身类型： 目录（目录下面可以加目录或者 题和空页 ）
       */
      public static final int DIR = 1;

      /**
       * 节点本身类型： 问题
       */
      public static final int QUES = 2;

      /**
       * 节点本身类型： 空白页（指引页）
       */
      public static final int GUIDE_PAGE = 3;

      public static final BizConstant me = new BizConstant(new Object[][] { { "目录", DIR }, { "题目", QUES }, { "空白页", GUIDE_PAGE } });

    }

    public static final class Node_subType {

      /**
       * 没有或者未确定
       */
      public static final int NONE = 0;

      /**
       * 节点下子节点类型： 目录
       */
      public static final int DIR = 1;

      /**
       * 节点下子节点类型： 题目或空白页
       */
      public static final int QUES = 2;

      public static final BizConstant me = new BizConstant(new Object[][] { { "没有或未确定", NONE }, { "目录", DIR }, { "题目", QUES } });
    }

    // 对应ExamTemplateModel对应的表中status字段：
    // status     tinyint(2)    NOT NULL DEFAULT 1 COMMENT '状态 2:已经发布可以使用，1:未发布，草稿， 0:隐藏/锁定/停用',
    public static final class C_status {
      public static final int PUBLISHED = 2;
      public static final int DRAFT = 1;
      public static final int HIDDEN = 0;

      public static final BizConstant me = new BizConstant(new Object[][] { { "停用", HIDDEN }, { "未发布", DRAFT }, { "已发布", PUBLISHED } });

      public static boolean isPublished(int status) {
        return status == PUBLISHED;
      }
    }

    /**
     * 模板引擎
     * @author Yangming
     * @create  2015年5月6日
     */
    public static final class C_renderEngine {
      public static final int DEFAULT = 0;
      public static final int SILIUJI_BISHI = 1; // 四级笔试
      public static final int SIJI_WANMGKAO = 2; // 四级网考

      public static final BizConstant me = new BizConstant(new Object[][] { { "默认", DEFAULT }, { "四级笔试", SILIUJI_BISHI } });

      public static boolean isSet(int value) {
        return SILIUJI_BISHI == value || SIJI_WANMGKAO == value;
      }

      public static int calcRealValue() {
        return DEFAULT;
      }

      public static int calcRealValue(int value) {
        if (isSet(value)) {
          return value;
        } else {
          return DEFAULT;
        }
      }
    }

    /**
     * 返回重做类型
     * @author Yangming
     * @create  2015年5月6日
     */
    public static final class C_redoType {
      public static final int DEFAULT = 0;
      public static final int CAN_REDO = 1; // 能返回重做
      public static final int CANNOT_REDO = 2; // 不能返回重做

      public static final BizConstant me = new BizConstant(new Object[][] { { "默认", DEFAULT }, { "能返回重做", CAN_REDO }, { "不能返回重做", CANNOT_REDO }, });

      public static boolean isSet(int value) {
        return CAN_REDO == value || CANNOT_REDO == value;
      }
    }

    /**
     * 子题在同一个页面显示的类型
     * @author Yangming
     * @create  2015年5月6日
     */
    public static final class C_quesInOnePageShowType {
      public static final int DEFAULT = 0;
      public static final int IN_SEP_PAGES = 1; // 在不同页面显示
      public static final int IN_ONE_PAGE = 2; // 在同一个页面显示

      public static final BizConstant me = new BizConstant(new Object[][] {
          //{"默认",  DEFAULT},
          { "在不同页面", IN_SEP_PAGES }, { "在同一个页面", IN_ONE_PAGE }, });

      public static boolean isSet(int value) {
        return IN_SEP_PAGES == value || IN_ONE_PAGE == value;
      }

      public static boolean calcRealValue() {
        return false;
      }

      public static boolean calcRealValue(int value) {
        if (value == IN_ONE_PAGE) {
          return true;
        } else if (value == IN_SEP_PAGES) {
          return false;
        }
        return false;
      }
    }

    /**
     * 子题题号是否累加的类型
     * @author Yangming
     * @create  2015年5月6日
     */
    public static final class C_quesCountType {

      public static final int DEFAULT = 0;
      public static final int ADD_TOGETHER = 1; // 累加小题号
      public static final int NOT_ADD_TOGETHER = 2; // 不累加小题号

      public static final BizConstant me = new BizConstant(new Object[][] {
          //{"默认",  DEFAULT},
          { "累加小题号", ADD_TOGETHER }, { "不累加小题号", NOT_ADD_TOGETHER }, });

      public static boolean isSet(int value) {
        return ADD_TOGETHER == value || NOT_ADD_TOGETHER == value;
      }

      public static boolean calcRealValue() {
        return true;
      }

      public static boolean calcRealValue(int value) {
        if (value == NOT_ADD_TOGETHER) {
          return false;
        } else if (value == ADD_TOGETHER) {
          return true;
        }
        return true;
      }
    }

    /**
     * 题目答案顺序能否乱序颠倒的类型
     * @author Yangming
     * @create  2015年5月6日
     */
    public static final class C_quesOptionCanChangeOrder {
      public static final int DEFAULT = 0;
      public static final int CHANGE_ORDER = 1; // 答案顺序颠倒
      public static final int NOT_CHANGE_ORDER = 2; // 答案顺序不颠倒

      public static final BizConstant me = new BizConstant(new Object[][] {
          //{"默认",  DEFAULT},
          { "选项乱序", CHANGE_ORDER }, { "选项不乱序", NOT_CHANGE_ORDER }, });

      public static boolean isSet(int value) {
        return CHANGE_ORDER == value || NOT_CHANGE_ORDER == value;
      }
    }

    /**
     * 模板用途
     * @author Yangming
     * @create  2015年5月18日
     */
    public static final class C_purpose {

      /**
       * 用途类型： 未指定
       */
      public static final int PP_NOT_SPECIFIED = 0;

      /**
       * 用途类型： 期末考试
       */
      public static final int PP_FINAL_EXAM = 1;

      /**
       * 用途类型： 班级考试
       */
      public static final int PP_CLASS_EXAM = 2;

      /**
       * 用途类型： 平时练习
       */
      public static final int PP_PRACTISE = 3;

      /**
       * 用途类型： 真实模考
       */
      public static final int PP_MOCK = 4;

      public static final BizConstant me = new BizConstant(new Object[][] { { "未指定", PP_NOT_SPECIFIED }, { "期末考试", PP_FINAL_EXAM }, { "班级考试", PP_CLASS_EXAM },
          { "平时练习", PP_PRACTISE }, { "真实模考", PP_MOCK } });
    }

    /**
     * 模板共享状态
     * @author Yangming
     * @create  2015年5月21日
     */
    public static final class C_shareStatus {
      /**
       * 模板共享状态： 私有
       */
      public static final int SS_PRIVATE = 0;

      /**
       * 模板共享状态： 共享，公开
       */
      public static final int SS_PUBLIC = 1;

      public static final BizConstant me = new BizConstant(new Object[][] { { "公开", SS_PUBLIC }, { "私有", SS_PRIVATE } });

      public static final BizConstant yesNo = new BizConstant(new Object[][] { { "是", SS_PUBLIC }, { "否", SS_PRIVATE } });
    }

    public static final class Pos {
      /**
       * 添加节点位置：前
       */
      public static final String before = "b";

      /**
       * 添加节点位置：后
       */
      public static final String after = "a";

      public static final BizConstant me = new BizConstant(new Object[][] { { "后", after }, { "前", before } });

      public static boolean isBefore(String pos) {
        return before.equals(pos);
      }
    }

    public static final class ChildOrSibling {
      /**
       * 子节点还是兄弟节点： 子节点
       */
      public static final String child = "c";

      /**
       * 子节点还是兄弟节点： 兄弟节点
       */
      public static final String sibling = "s";

      public static final BizConstant me = new BizConstant(new Object[][] { { "子节点", child }, { "兄弟节点", sibling } });

      public static boolean isSibling(String cs) {
        return sibling.equals(cs);
      }
    }
  }

  /**
   * web模块需要用到的常量
   * @author Yangming
   */
  public static class WebCC {

    /**
     * 定义controller中render json的code值
     * @author yangming
     *
     */
    public static class JSON_CODE {
      public static final int SUCCESS = 1;
      public static final int ERROR = 0;
      public static final int NOT_LOGIN = 400;
      public static final int NO_PERMISSION = 403;
    }

  }

  /**
   * 题目有关的常量
   * @author Yangming
   */
  public static class QuesCC {

    /**
     * 科目
     * @author Yangming
     * @create  2015年5月18日
     */
    public static final class C_course {
      public static final int ENGLISH = 1; // 英语
      public static final int RUSSIAN = 2; // 俄语
      public static final int JAPANESE = 3; // 日语
      public static final int FRENCH = 4; // 法语
      public static final int VIETNAMESE = 5; // 越南语
      public static final int MANDARIN = 6; // 汉语普通话
      public static final int GERMEM = 7; // 德语
      public static final int KOREAN = 8; // 韩语
      
      public static final int UNKNOW = 0; //unknow的课程
      

      public static final BizConstant me = new BizConstant(new Object[][] { { "英语", ENGLISH }, { "俄语", RUSSIAN }, 
        { "日语", JAPANESE }, { "法语", FRENCH }, { "德语", GERMEM }, { " 韩语", KOREAN }, 
        { "越南语", VIETNAMESE }, { "汉语普通话", MANDARIN } });

      public static final List<Object[]> getCourseList() {
        Object[][] str = { { ENGLISH, "英语" }, { RUSSIAN, "俄语" }, { JAPANESE, "日语" }, { FRENCH, "法语" }, 
            { GERMEM, "德语" }, { KOREAN, "韩语" }, { VIETNAMESE, "越南语" }, { MANDARIN, "汉语普通话" } };
        List<Object[]> courseList = new ArrayList<Object[]>();
        for (Object[] obj : str) {
          courseList.add(obj);
        }
        return courseList;
      }
    }
  }

  /**
   * @author Yeyayun
   */
  public static String[] LETTER_ARRAY = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
      "X", "Y", "Z" };

  /**
   * @author Yeyayun
   * 1:听力，2:阅读，3:写作，4:翻译，5:完形填空
   */
  public static class QuesCatCC {
    public static final class C_typeCat {
      public static int Listening = 1;
      public static int Reading = 2;
      public static int Writing = 3;
      public static int Translation = 4;
      public static int Cloze = 5;
      public static int Base = 6;
      public static int Error = 7;
      public static int Speaking = 8;

      public static boolean isListening(int typeCat) {
        return typeCat == Listening;
      }

      public static boolean isSpeaking(int typeCat) {
        return typeCat == Speaking;
      }

      public static final BizConstant me = new BizConstant(new Object[][] { { "听力", Listening }, { "阅读", Reading }, { "写作", Writing }, { "翻译", Translation },
          { "完型", Cloze }, { "改错", Error }, { "口语", Speaking }, { "基础题", Base } });

      //      /**
      //       * 是否客观题
      //       * @return
      //       */
      //      public static boolean isAutoRate(int typeCat) {
      //        return typeCat != Writing && typeCat != Translation;
      //      }

    }

  }

  /**
   * 上传文件相关常量
   * @author lx
   *
   */
  public static class ImportCC {
    /**
     * 上传文件的方式的常量
        IMPORT_TYPE_TEXTAREA----1:文本框导入 
        IMPORT_TYPE_FILE----2:excel上传
     * @author lx
     */
    public static final class C_IMPORT_TYPE {
      public static final int IMPORT_TYPE_TEXTAREA = 1; //文本框导入
      public static final int IMPORT_TYPE_FILE = 2; //上传文件
    }

    /**
     * 账户类型的常量
     * @author lx
     *  STUDENT:学生
     *  TEACHER:教师
     *  ADMIN：管理员
     */
    public static final class C_ACCOUNT_TYPE {
      public static final int STUDENT = 1; //学生
      public static final int TEACHER = 2; //教师
      public static final int ADMIN = 3; //管理员
    }

    /**
     * excel后缀,判断excel版本--
     * xls:2003 
     * xlsx:2007
     */
    public static final class C_EXCEL_SUFFIX {
      public static final String SUFFIX_2003 = "xls";
      public static final String SUFFIX_2007 = "xlsx";
    }

  }

  /**
   * 资源管理相关常量
   */
  public static class ZiyuanFileCC {
    /**
     * 文件类型  1音频---2视频---3富文本 ---4其他
     * @author lx
     *
     */
    public static final class C_FILEYPE {
      public static final int YINPIN = 1;
      public static final int SHIPIN = 2;
      public static final int TEXT = 3;
      public static final int OTHER = 4;
    }

    //---------------  Yangming add begin
    public static final class C_Exts {
      public static final Set<String> YINPIN = CollectionUtil.asLinkedHashSet(".mp3", ".wma");
      public static final Set<String> SHIPIN = CollectionUtil.asLinkedHashSet(".avi", ".rm", ".rmvb", ".flv", ".mpg", ".mpeg", ".mov", ".mkv", ".mp4");
      public static final Set<String> TEXT = CollectionUtil.asLinkedHashSet(".txt", ".doc", ".docx");

      public static final Set<String> ALL_LEGAL = new LinkedHashSet<String>();
      static {
        ALL_LEGAL.addAll(YINPIN);
        ALL_LEGAL.addAll(SHIPIN);
        ALL_LEGAL.addAll(TEXT);
      }
    }

    public static int calcFileType(String fileName) {
      String fileExt = FileUtil.getFileExtWithDot(fileName);
      if (StringUtil.isNotEmpty(fileExt)) {
        fileExt = fileExt.toLowerCase();
        if (C_Exts.YINPIN.contains(fileExt)) {
          return C_FILEYPE.YINPIN;
        } else if (C_Exts.SHIPIN.contains(fileExt)) {
          return C_FILEYPE.SHIPIN;
        } else if (C_Exts.TEXT.contains(fileExt)) {
          return C_FILEYPE.TEXT;
        }
      }
      return C_FILEYPE.OTHER;
    }

    /**
     * 上传文件是否合法 通过文件名判断
     * @return
     */
    public static boolean isFileNameLegal(String fileName) {
      String fileExt = FileUtil.getFileExtWithDot(fileName);
      if (StringUtil.isNotEmpty(fileExt)) {
        fileExt = fileExt.toLowerCase();
        return C_Exts.ALL_LEGAL.contains(fileExt);
      }
      return false;
    }

    //---------------  Yangming add end

    /**
     * 文件后缀 extension
     */
    public static final class C_EXTENSION {
      public static final String YINPIN = ".mp3 .wma";
      public static final String SHIPIN = ".avi .rm .rmvb .flv .mpg  .mov .mkv  .mp4";
      public static final String TEXT = ".text .doc";
    }

    /**
     * 是否是文件  1是----0不是
     * @author lx
     *
     */
    public static final class C_ISFILE {
      public static final int YES = 1;
      public static final int NO = 0;
    }

    /**
     * 文件保存路径常量
     */
    public static final class C_SAVE_PREF {
      public static final String PREF_ZY = "_zy";
      public static final String PREF_LOGO = "_logo";
    }
  }

  /**
   * 本系统大部分表都带有status字段， 这个字段可以用于标记假删除 <br />
  * 所有status字段含义都一样！
   * @author yangming
   *
   */
  public static final class C_status {

    /**
     * 被隐藏/锁定等
     */
    public static final int HIDDEN = 0;

    /**
     * 正常状态
     */
    public static final int NORMAL = 1;

    private static final BizConstant bc_Off = new BizConstant(new Object[][] { { "启用", NORMAL }, { "停用", HIDDEN }, });

    private static final BizConstant bc_Hidden = new BizConstant(new Object[][] { { "正常", NORMAL }, { "隐藏", HIDDEN }, });

    private static final BizConstant bc_Lock = new BizConstant(new Object[][] { { "正常", NORMAL }, { "锁定", HIDDEN }, });

    public static BizConstant bizConstantHidden() {
      return bc_Hidden;
    }

    public static BizConstant bizConstantLock() {
      return bc_Lock;
    }

    public static BizConstant bizConstantOff() {
      return bc_Off;
    }

  }

  /**
   * 文件导出相关常量
   * @author lx
   *
   */
  public static final class exportCC {
    public static final String ADMIN = "管理员表";
    public static final String TEACHER = "教师表";
    public static final String STUDENT = "学生表";
    public static final String CAMPUS = "校区表";
    public static final String FACULTY = "院系表";
    public static final String GRADE = "年级表";
    public static final String CLASS = "班级表";
    public static final String KAOSHI = "考试表";
    public static final String KAOCHANG = "考场表";

    public static final BizConstant bc_all = new BizConstant(new Object[][] { { "admin", ADMIN }, { "teacher", TEACHER }, { "student", STUDENT },
        { "campus", CAMPUS }, { "faculty", FACULTY }, { "grade", GRADE }, { "class", CLASS }, { "kaoshi", KAOSHI }, { "kaochang", KAOCHANG } });

  }

  /**
   * 学校信息相关常量
   * @author lx
   *
   */
  public static final class schoolCC {
    public static final class C_PERMIT_READY {
      public static final int NOEXSIT = 0; //不存在
      public static final int MSG_ERROR = 1;//信息错误
      public static final int DATE_EXPIRE = 2;//日期过期
      public static final int SUCCESS = 3; //成功
      public static final int OTHER = 4; //其他情况

      public static Map<String, Object> tf(int index) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        String msg = "";
        boolean flag = false;
        if (index == NOEXSIT) {
          msg = "许可证未激活";
        } else if (index == MSG_ERROR) {
          msg = "许可证信息校验出错";
        } else if (index == DATE_EXPIRE) {
          msg = "系统使用期限已到";
        } else if (index == SUCCESS) {
          msg = "许可证信息校验成功";
          flag = true;
        } else if (index == OTHER) {
          msg = "系统出错";
        }
        newMap.put("index", index);
        newMap.put("msg", msg);
        newMap.put("flag", flag);
        return newMap;
      }
    }
  }

  /**
   * 当前和历史-常量
   * @author lx
   *
   */
  public static final class NowOrHistoryCC {
    public static final String NOW = "now"; //当前
    public static final String HISTORY = "his"; //历史
  }

  /**
   * 学生和教师角色的常量
   * @author lx
   *
   */
  public static final class StudentOrTeacherCC {
    public static final String STUDENT = "student"; //当前
    public static final String TEACHER = "teacher"; //历史
  }
  
  /**
   * 每天请求一次别的服务器的相关参数
   * sign:根据请求返回的状态，请求成功则为true，请求失败则为false；
   * PERIOD： 请求间隔时间，24小时，即每天请求一次。
   */
  
  public static class TimerArgs {
    public static Boolean SIGN = false; //成功为true或者失败为false的标识
    public static String ERRORMSG = ""; //错误信息
    public static boolean JUADGEFLAG = true; //默认为true，表示默认校验
    public static String PERMITURL = "http://118.186.211.153:8888/license-proxy/1.0/license/check_license"; //默认校验地址
    
    public static final class C_Signs{
      public static int SUCCESS = 1;        //校验成功
      public static int SERVICE_ERROR = -1; //服务器出错
      
      public static final BizConstant CN_MSG = new BizConstant(new Object[][] { 
          { "成功返回请求信息", SUCCESS }, { "校验服务器出错", SERVICE_ERROR }
      });
    }
    
    public static final class C_Times {
      public static long one_hour = 1000 * 60 * 60; //one hour
      public static long one_minute = 1000 * 60 ; //one_minute
      public static long one_second = 1000 ; //one_second
    }
    //验证请求是携带的参数之一：授权产品名称
    public static String product = "iTest"; 
    
    //校验返回的状态码
    public static int PERMIT_CODE = 0;
    
  }
  
  /**
   * 学生登录时判断是否存在考试的标识
   * NORMAL:正常的状态 
   * FIRST_STEP:首次跳转
   */
  public static final class redirectFlagCC {
    public static final int NORMAL = 1; 
    public static final int FIRST_STEP = 0; 
  }
  
  public static final class Mail_CC{
    /**
     * mail.smtp.host=smtp.unipus.cn
mail.user=iwrite@unipus.cn
mail.password=fltrp8881
     */
    public static final String mail_smtp_host = "smtp.unipus.cn"; 
    public static final String mail_user = "itest@unipus.cn"; 
    public static final String mail_password = "iTEST_admin"; 
    public static final String mail_url = "http://localhost:8080/itest/itest/"; 
  }
  
  
  /**
   *验证码相关常量
   * @author lx
   *
   */
  public static class PermitCC {
  	//学校网络类型
  	public static final class C_Type {
  		public static final int TYPE_PUBLIC = 1; //公网
  		public static final int TYPE_SCHOOL = 2; //学校内网非SAAS
  		public static final int TYPE_THREE = 3; //短信验证6位
  	}
  	//public static final int XUEXIAOID = 1; 			 //学校id
  	
  }
  
}
