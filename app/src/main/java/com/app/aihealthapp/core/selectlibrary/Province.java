package com.app.aihealthapp.core.selectlibrary;

import java.util.List;

/**
 * Created by cool on 2017/12/26.
 */

public class Province {
    public String code;
    public String name;
    public List<City> cityList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCity() {
        return cityList;
    }

    public void setCity(List<City> city) {
        this.cityList = city;
    }

    public static class City{
        public String code;
        public String name;
        public List<Area> areaList;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Area> getArea() {
            return areaList;
        }

        public void setArea(List<Area> area) {
            this.areaList = area;
        }
    }

    public static class Area{
        public String code;
        public String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
