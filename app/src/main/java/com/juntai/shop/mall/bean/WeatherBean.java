package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * 天气
 * Created by Ma
 * on 2019/9/29
 */
public class WeatherBean extends BaseResult {
    /**
     * error : null
     * returnValue : {"status":"ok","lang":"zh_CN","unit":"metric","server_time":1569725010,"location":[39.917545,116.418755],"api_status":"active","tzshift":28800,"api_version":"v2.2","result":{"status":"ok","o3":109,"co":1.1,"temperature":24,"pm10":77,"skycon":"CLEAR_DAY","cloudrate":0,"precipitation":{"nearest":{"status":"ok","distance":10000,"intensity":0},"local":{"status":"ok","intensity":0,"datasource":"radar"}},"dswrf":453.3,"visibility":4,"humidity":0.59,"so2":37,"ultraviolet":{"index":7,"desc":"强"},"pres":101009,"aqi":110,"pm25":83,"no2":37,"apparent_temperature":24.4,"comfort":{"index":4,"desc":"温暖"},"wind":{"direction":82,"speed":7.56}}}
     * msg : null
     * list : null
     * type : null
     */

    private ReturnValueBean returnValue;

    public ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        /**
         * status : ok
         * lang : zh_CN
         * unit : metric
         * server_time : 1569725010
         * location : [39.917545,116.418755]
         * api_status : active
         * tzshift : 28800
         * api_version : v2.2
         * result : {"status":"ok","o3":109,"co":1.1,"temperature":24,"pm10":77,"skycon":"CLEAR_DAY","cloudrate":0,"precipitation":{"nearest":{"status":"ok","distance":10000,"intensity":0},"local":{"status":"ok","intensity":0,"datasource":"radar"}},"dswrf":453.3,"visibility":4,"humidity":0.59,"so2":37,"ultraviolet":{"index":7,"desc":"强"},"pres":101009,"aqi":110,"pm25":83,"no2":37,"apparent_temperature":24.4,"comfort":{"index":4,"desc":"温暖"},"wind":{"direction":82,"speed":7.56}}
         */

        private String status;
        private String lang;
        private String unit;
        private int server_time;
        private String api_status;
        private int tzshift;
        private String api_version;
        private ResultBean result;
        private List<Double> location;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getServer_time() {
            return server_time;
        }

        public void setServer_time(int server_time) {
            this.server_time = server_time;
        }

        public String getApi_status() {
            return api_status;
        }

        public void setApi_status(String api_status) {
            this.api_status = api_status;
        }

        public int getTzshift() {
            return tzshift;
        }

        public void setTzshift(int tzshift) {
            this.tzshift = tzshift;
        }

        public String getApi_version() {
            return api_version;
        }

        public void setApi_version(String api_version) {
            this.api_version = api_version;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
        }

        public static class ResultBean {
            /**
             * status : ok
             * o3 : 109.0
             * co : 1.1
             * temperature : 24.0
             * pm10 : 77.0
             * skycon : CLEAR_DAY
             * cloudrate : 0.0
             * precipitation : {"nearest":{"status":"ok","distance":10000,"intensity":0},"local":{"status":"ok","intensity":0,"datasource":"radar"}}
             * dswrf : 453.3
             * visibility : 4.0
             * humidity : 0.59
             * so2 : 37.0
             * ultraviolet : {"index":7,"desc":"强"}
             * pres : 101009.0
             * aqi : 110
             * pm25 : 83
             * no2 : 37.0
             * apparent_temperature : 24.4
             * comfort : {"index":4,"desc":"温暖"}
             * wind : {"direction":82,"speed":7.56}
             */

            private String status;
            private double o3;
            private double co;
            private double temperature;
            private double pm10;
            private String skycon;
            private double cloudrate;
            private PrecipitationBean precipitation;
            private double dswrf;
            private double visibility;
            private double humidity;
            private double so2;
            private UltravioletBean ultraviolet;
            private double pres;
            private int aqi;
            private int pm25;
            private double no2;
            private double apparent_temperature;
            private ComfortBean comfort;
            private WindBean wind;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public double getO3() {
                return o3;
            }

            public void setO3(double o3) {
                this.o3 = o3;
            }

            public double getCo() {
                return co;
            }

            public void setCo(double co) {
                this.co = co;
            }

            public double getTemperature() {
                return temperature;
            }

            public void setTemperature(double temperature) {
                this.temperature = temperature;
            }

            public double getPm10() {
                return pm10;
            }

            public void setPm10(double pm10) {
                this.pm10 = pm10;
            }

            public String getSkycon() {
                return skycon;
            }

            public void setSkycon(String skycon) {
                this.skycon = skycon;
            }

            public double getCloudrate() {
                return cloudrate;
            }

            public void setCloudrate(double cloudrate) {
                this.cloudrate = cloudrate;
            }

            public PrecipitationBean getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(PrecipitationBean precipitation) {
                this.precipitation = precipitation;
            }

            public double getDswrf() {
                return dswrf;
            }

            public void setDswrf(double dswrf) {
                this.dswrf = dswrf;
            }

            public double getVisibility() {
                return visibility;
            }

            public void setVisibility(double visibility) {
                this.visibility = visibility;
            }

            public double getHumidity() {
                return humidity;
            }

            public void setHumidity(double humidity) {
                this.humidity = humidity;
            }

            public double getSo2() {
                return so2;
            }

            public void setSo2(double so2) {
                this.so2 = so2;
            }

            public UltravioletBean getUltraviolet() {
                return ultraviolet;
            }

            public void setUltraviolet(UltravioletBean ultraviolet) {
                this.ultraviolet = ultraviolet;
            }

            public double getPres() {
                return pres;
            }

            public void setPres(double pres) {
                this.pres = pres;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public int getPm25() {
                return pm25;
            }

            public void setPm25(int pm25) {
                this.pm25 = pm25;
            }

            public double getNo2() {
                return no2;
            }

            public void setNo2(double no2) {
                this.no2 = no2;
            }

            public double getApparent_temperature() {
                return apparent_temperature;
            }

            public void setApparent_temperature(double apparent_temperature) {
                this.apparent_temperature = apparent_temperature;
            }

            public ComfortBean getComfort() {
                return comfort;
            }

            public void setComfort(ComfortBean comfort) {
                this.comfort = comfort;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class PrecipitationBean {
                /**
                 * nearest : {"status":"ok","distance":10000,"intensity":0}
                 * local : {"status":"ok","intensity":0,"datasource":"radar"}
                 */

                private NearestBean nearest;
                private LocalBean local;

                public NearestBean getNearest() {
                    return nearest;
                }

                public void setNearest(NearestBean nearest) {
                    this.nearest = nearest;
                }

                public LocalBean getLocal() {
                    return local;
                }

                public void setLocal(LocalBean local) {
                    this.local = local;
                }

                public static class NearestBean {
                    /**
                     * status : ok
                     * distance : 10000.0
                     * intensity : 0.0
                     */

                    private String status;
                    private double distance;
                    private double intensity;

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }

                    public double getDistance() {
                        return distance;
                    }

                    public void setDistance(double distance) {
                        this.distance = distance;
                    }

                    public double getIntensity() {
                        return intensity;
                    }

                    public void setIntensity(double intensity) {
                        this.intensity = intensity;
                    }
                }

                public static class LocalBean {
                    /**
                     * status : ok
                     * intensity : 0.0
                     * datasource : radar
                     */

                    private String status;
                    private double intensity;
                    private String datasource;

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }

                    public double getIntensity() {
                        return intensity;
                    }

                    public void setIntensity(double intensity) {
                        this.intensity = intensity;
                    }

                    public String getDatasource() {
                        return datasource;
                    }

                    public void setDatasource(String datasource) {
                        this.datasource = datasource;
                    }
                }
            }

            public static class UltravioletBean {
                /**
                 * index : 7.0
                 * desc : 强
                 */

                private double index;
                private String desc;

                public double getIndex() {
                    return index;
                }

                public void setIndex(double index) {
                    this.index = index;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }

            public static class ComfortBean {
                /**
                 * index : 4
                 * desc : 温暖
                 */

                private int index;
                private String desc;

                public int getIndex() {
                    return index;
                }

                public void setIndex(int index) {
                    this.index = index;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }

            public static class WindBean {
                /**
                 * direction : 82.0
                 * speed : 7.56
                 */

                private double direction;
                private double speed;

                public double getDirection() {
                    return direction;
                }

                public void setDirection(double direction) {
                    this.direction = direction;
                }

                public double getSpeed() {
                    return speed;
                }

                public void setSpeed(double speed) {
                    this.speed = speed;
                }
            }
        }
    }
}
