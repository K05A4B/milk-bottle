package cn.kzhik.milkbottle.utils;

public class Mod {
    public Mod() {

    }

    public static String getModId() {
        return "milk-bottle";
    }

    public static int getComplementaryColor(int color) {
        // 获取互补色
        // 互补色算法为 0xff分别减去被反色的RGB的结果
        int R = (255 - (color >> 16 & 0xff)) << 8;
        int G = (255 - ((color >> 8) & 0xff)) << 8;
        int B = (255 - (color & 0xff));

        return R + G + B;
    }

    public static String makeDurationString(int duration) {
        int secondDuration = duration / 20;
        int seconds = secondDuration % 60;
        int minutes = (secondDuration - seconds) / 60;
        String res = "";

        if (minutes < 10) {
            res += "0" + minutes;
        } else {
            res += String.valueOf(minutes);
        }

        res += ":";

        if (seconds < 10) {
            res += "0" + seconds;
        } else {
            res += String.valueOf(seconds);
        }

        return res;
    }
}
