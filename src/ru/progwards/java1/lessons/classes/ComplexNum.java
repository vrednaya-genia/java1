package ru.progwards.java1.lessons.classes;

public class ComplexNum {
    int a;
    int b;

    public ComplexNum(int a, int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        if (this.b==0) return Integer.toString(this.a);
        else if (this.b<0) return this.a + "-" + Math.abs(this.b) + "i";
        else return this.a + "+" + this.b + "i";
    }

    public ComplexNum add(ComplexNum num) { // (a + bi) + (c + di) = (a + c) + (b + d)i
        this.a = this.a + num.a;
        this.b = this.b + num.b;
        return this;
    }

    public ComplexNum sub(ComplexNum num) { //(a + bi) - (c + di) = (a - c) + (b - d)i
        this.a = this.a - num.a;
        this.b = this.b - num.b;
        return this;
    }

    public ComplexNum mul(ComplexNum num) { //(a + bi) * (c + di) = (a*c - b*d) + (b*c + a*d)i
        this.a = this.a*num.a - this.b*num.b;
        this.b = this.b*num.a + this.a*num.b;
        return this;
    }

    public ComplexNum div(ComplexNum num) { //(a + bi) / (c + di) = (a*c + b*d)/(c*c+d*d) + ((b*c - a*d)/(c*c+d*d))i
        this.a = (this.a*num.a + this.b*num.b)/(num.a*num.a + num.b*num.b);
        this.b = (this.b*num.a - this.a*num.b)/(num.a*num.a + num.b*num.b);
        return this;
    }

    public static void main(String[] args) {
        ComplexNum cn = new ComplexNum(1,2);
        System.out.println(cn.toString());
        System.out.println(cn.add(cn));
        System.out.println(cn.mul(cn));
        System.out.println(cn.div(cn));
        System.out.println(cn.sub(cn));
    }
}
