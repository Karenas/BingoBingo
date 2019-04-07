package com.gmself.studio.mg.basemodule.entity.port_entity;

import com.gmself.studio.mg.basemodule.entity.LocationBasic;

import java.util.List;

/**
 * Created by guomeng on 4/6.
 */

public class Get_HFLocation {
    private List<HeWeather6> HeWeather6;

    public List<Get_HFLocation.HeWeather6> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<Get_HFLocation.HeWeather6> heWeather6) {
        HeWeather6 = heWeather6;
    }

    public static class HeWeather6{
        private List<LocationBasic> basic;
        private String status;

        public List<LocationBasic> getBasic() {
            return basic;
        }

        public void setBasic(List<LocationBasic> basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
