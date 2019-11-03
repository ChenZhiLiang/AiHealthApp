package com.app.aihealthapp.core.network.api;

/**
 * Created by Administrator on 2017-11-02.
 * 对外接口url链接
 */

public final class ApiUrl {

    // dev = 0 测试 dev=1 生产
    public static final int dev = 1;
    public static final String HOST =dev==0?"http://aijiankang.cacpo.com/":"https://api.jiankangkey.com/";



    public static class HomeApi{
        //首页
        public static final String Home = HOST +"api/index/dashboard";
        public static final String Upload = HOST +"api/upload";
        public static final String Buy = HOST +"api/advice/buy";
        public static final String Pay = HOST +"api/pay";

    }

    public static class UserApi{
        public static final String Verify = HOST +"api/verify";
        public static final String Register = HOST +"api/register";
        public static final String Login = HOST +"api/login";
        public static final String UserInfo = HOST +"api/user/dashboard";
        public static final String UpdateProfile = HOST+"api/update/profile";
        public static final String Authentication = HOST +"api/user/realname";
        public static final String OneAds = HOST +"api/one/ads";
        public static final String SearchRecord = HOST +"api/my/doctor/list";
        public static final String InterrogationRecord = HOST +"api/my/user/list";

        public static final String AdviceDetail = HOST + "api/advice/detail";//咨询详情
        public static final String AdviceComment = HOST +"api/advice/comment";//会员评论咨询回复问题
        public static final String ReplyList = HOST +"api/advice/reply/list";//咨询医生回复记录
        public static final String AdviceReply = HOST +"api/advice/reply";//医生回复咨询问题


    }

    public static class DeviceApi{
        public static final String DeviceInfo = HOST +"api/device/dashboard";
        public static final String BindDevice = HOST +"api/device/bind";
        public static final String UpdateDevice = HOST +"api/device/update";
        public static final String Question = HOST +"api/advice/question";
        public static final String RunSteps = HOST +"api/measure/runsteps";
        public static final String Bloodoxygen = HOST+"api/measure/bloodoxygen";
        public static final String Heartrate = HOST+"api/measure/heartrate";
        public static final String Bloodpressure = HOST+"api/measure/bloodpressure";
        public static final String UnBind = HOST +"api/un/bind";//解绑

        public static final String MeasureSleep = HOST +"api/measure/sleep";//上传睡眠监测时间
        public static final String MeasureCheckSleep = HOST +"api/measure/check/sleep";//睡眠监测时间 查询
    }

    public static class WebApi{
        public static final String FORGET_PASSWORD = HOST + "wap/find_pwd.html?type=1";
        public static final String CONTROL_CENTER = HOST + "wap/control_center.html";//健康管理中心
        public static final String COMMUNITY = HOST +"wap/community.html";//健康社区
        public static final String HeadLine = HOST +"wap/headline_list.html?cate_id=";//首页健康管理分类进入页面
        public static final String DoctorDetail = HOST+"wap/doctor_details.html?id=";//医生详情介绍
        public static final String MyKeyList = HOST + "wap/mykey_list_app.html?uid=";//我的健康秘钥
        public static final String HealthPlan = HOST + "wap/health_plan.html?uid=";//我的健康方案
        public static final String MedicalReport = HOST + "wap/medical_report.html?uid=";//我的体检报告
        public static final String MyfriendList = HOST + "wap/myfriend_list.html?uid=";//我的健康朋友圈
        public static final String About = HOST + "article/item/15.html";//关于健康
        public static final String Feedback = HOST + "wap/feedback.html";//帮助与反馈
        public static final String Healthy_Report = HOST + "wap/healthy_report.html?uid=";//我的健康指数
        public static final String MyOrder = HOST + "user/myorder?uid=";//我的订单
        public static final String User_Address = HOST + "user/user_address?uid=";//收货地址
        public static final String Self_Support = HOST + "self/support";//健康商城
        public static final String Index = HOST + "index";//健康商圈
        public static final String Community = HOST + "wap/community.html";//健康论坛
        public static final String EditPwd = HOST +"user/editpwd?uid=";
        public static final String ShareFriend = HOST + "wap/qrcode.html?uid=";//推荐给好友
        public static final String MyMembers = HOST +"wap/myagent_list.html";//我的代理商会员
        public static final String MyWallet = HOST +"user/mywallet?uid=";//现金明细

        public static final String UserProtocol = HOST +"article/item/49.html";//用户协议
        public static final String PrivacyProtocol = HOST +"article/item/53.html";//隐私协议

        public static final String headline_list = HOST + "wap/headline_list.html";//健康知识、健康论坛、养生保健

      }

    public static class DoctorApi{
        public static final String DoctorList =HOST +"api/doctor/list";
        public static final String DoctorDetail =HOST +"api/doctor/read";

    }
}
