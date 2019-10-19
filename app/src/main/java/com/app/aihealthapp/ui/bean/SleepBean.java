package com.app.aihealthapp.ui.bean;

/**
 * @Author: chenzl
 * @ClassName: SleepBean
 * @Description: java类作用描述
 * @CreateDate: 2019/10/19 17:19
 */
public class SleepBean {


    /**
     * ret : 0
     * msg : 成功
     * data : {"id":268,"user_id":244,"info":"睡眠监测","total_time":100,"restfull_time":200,"light_time":300,"sober_time":400,"add_date":1571476682,"utime":0}
     */

    private int ret;
    private String msg;
    private DataBean data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 268
         * user_id : 244
         * info : 睡眠监测
         * total_time : 100
         * restfull_time : 200
         * light_time : 300
         * sober_time : 400
         * add_date : 1571476682
         * utime : 0
         */

        private int id;
        private int user_id;
        private String info;
        private int total_time;
        private int restfull_time;
        private int light_time;
        private int sober_time;
        private int add_date;
        private int utime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getTotal_time() {
            return total_time;
        }

        public void setTotal_time(int total_time) {
            this.total_time = total_time;
        }

        public int getRestfull_time() {
            return restfull_time;
        }

        public void setRestfull_time(int restfull_time) {
            this.restfull_time = restfull_time;
        }

        public int getLight_time() {
            return light_time;
        }

        public void setLight_time(int light_time) {
            this.light_time = light_time;
        }

        public int getSober_time() {
            return sober_time;
        }

        public void setSober_time(int sober_time) {
            this.sober_time = sober_time;
        }

        public int getAdd_date() {
            return add_date;
        }

        public void setAdd_date(int add_date) {
            this.add_date = add_date;
        }

        public int getUtime() {
            return utime;
        }

        public void setUtime(int utime) {
            this.utime = utime;
        }
    }
}
