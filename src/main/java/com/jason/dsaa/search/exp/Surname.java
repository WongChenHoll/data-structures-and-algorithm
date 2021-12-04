package com.jason.dsaa.search.exp;

/**
 * 姓氏
 *
 * @author WangChenHol
 * @date 2021-8-17 16:09
 **/
public class Surname implements Comparable<Surname> {

    private int number; // 编号
    private String famliyName; // 姓

    public Surname() {
    }

    public Surname(int number) {
        this.number = number;
    }

    public Surname(int number, String famliyName) {
        this.number = number;
        this.famliyName = famliyName;
    }

    @Override
    public int compareTo(Surname o) {
        return Integer.compare(this.number, o.number);
    }

    public int getNumber() {
        return number;
    }

    public String getFamliyName() {
        return famliyName;
    }

    @Override
    public String toString() {
        return "【编码：" + number + "，姓：" + famliyName + "】\r\n";
    }
}
