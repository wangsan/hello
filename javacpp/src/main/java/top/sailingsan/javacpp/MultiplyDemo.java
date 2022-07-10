package top.sailingsan.javacpp;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.Platform;

@Platform(include="top/sailingsan/javacpp//MultiplyDemo.h")
public class MultiplyDemo {

    static {
        Loader.load();
    }

    static native int multiply(int a, int b);

    public static void main(String[] args) {
        System.out.println("multiply(123,100): " + multiply(123,100));
    }

}
