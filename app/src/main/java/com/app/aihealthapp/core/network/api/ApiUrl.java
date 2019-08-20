package com.app.aihealthapp.core.network.api;

/**
 * Created by Administrator on 2017-11-02.
 * 对外接口url链接
 */

public final class ApiUrl {

    // dev = 0 测试 dev=1 生产
    public static final int dev = 0;
    public static final String HOST =dev==0?"http://aijiankang.cacpo.com/":"http://aijiankang.cacpo.com/";



    public static class HomeApi{
        //首页
        public static final String Home = HOST +"api/index/dashboard";
        public static final String Upload = HOST +"api/upload";
    }

    public static class UserApi{
        public static final String Verify = HOST +"api/verify";
        public static final String Register = HOST +"api/register";
        public static final String Login = HOST +"api/login";
        public static final String UserInfo = HOST +"api/user/dashboard";
        public static final String Authentication = HOST +"api/user/realname";

        public static final String OneAds = HOST +"api/one/ads";

        public static final String SearchRecord = HOST +"api/my/doctor/list";
        public static final String InterrogationRecord = HOST +"api/my/user/list";

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



    }

    public static class WebApi{
        public static final String CONTROL_CENTER = HOST + "wap/control_center.html";//健康管理中心
        public static final String COMMUNITY = HOST +"wap/community.html";//健康社区

        public static final String HeadLine = HOST +"wap/headline_list.html?cate_id=";//首页健康管理分类进入页面

        public static final String DoctorDetail = HOST+"wap/doctor_details.html?id=";//医生详情介绍

        public static final String MyKeyList = HOST + "wap/mykey_list_app.html";//我的健康秘钥

        public static final String HealthPlan = HOST + "wap/health_plan.html";//我的健康方案

        public static final String MedicalReport = HOST + "wap/medical_report.html";//我的体检报告

        public static final String MyfriendList = HOST + "wap/myfriend_list.html";//我的健康朋友圈
        public static final String About = HOST + "article/item/15.html";//关于健康

      }

    public static class DoctorApi{
        public static final String DoctorList =HOST +"api/doctor/list";
        public static final String DoctorDetail =HOST +"api/doctor/read";

    }
}
