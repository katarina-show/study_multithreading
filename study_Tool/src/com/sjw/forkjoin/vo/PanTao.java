package com.sjw.forkjoin.vo;

/**
 * 蟠桃的实体类
 */
public class PanTao {

    //颜色
    private final Color color;
    //大小
    private final Size size;
    //年份，3000年1熟，6000年1熟，9000年1熟
    private final int Year;

    public PanTao(Color color, Size size, int year) {
        this.color = color;
        this.size = size;
        Year = year;
    }

    public Color getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public int getYear() {
        return Year;
    }

    @Override
    public String toString() {
        return "PanTao{" +
                "color=" + color +
                ", size=" + size +
                ", Year=" + Year +
                '}';
    }
}
