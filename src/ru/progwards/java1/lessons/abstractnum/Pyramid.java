package ru.progwards.java1.lessons.abstractnum;

public class Pyramid extends Figure3D {
    public Pyramid(Number segment) {
        super(segment);
    }

    @Override
    public Number volume() {
        IntNumber i3 = new IntNumber(3);
        if (segment.getClass() == i3.getClass()) {
            return segment.mul(segment.mul(segment.div(i3)));
        }
        return segment.mul(segment.mul(segment.div(new DoubleNumber(3.0))));
    }
}
