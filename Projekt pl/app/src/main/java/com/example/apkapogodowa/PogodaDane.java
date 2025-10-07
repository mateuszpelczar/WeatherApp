package com.example.apkapogodowa;

public class PogodaDane {
    private Main main;
    private Weather[] weather;
    private String name;


    public Main getMain() {
        return main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public String getName() {
        return name;
    }



    public static class Main {
        private double temp;
        private double temp_min;
        private double temp_max;
        private int humidity;
        private double feels_like;
//        private double pop;


        public double getTemp() {
            return temp;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public double getFeels_like() {
            return feels_like;
        }




        public int getHumidity() {
            return humidity;
        }
    }



        public static class Weather {
            private String description;

            public String getDescription() {
                return description;
            }
        }
    }


