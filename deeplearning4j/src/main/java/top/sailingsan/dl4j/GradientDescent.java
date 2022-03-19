package top.sailingsan.dl4j;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * created by wangsan on 2020/3/16 in project of example .
 * <p>
 * <p>
 * 代价函数 cost y=ax+b, cost=(y-yi)^2=(axi+b-yi)^2
 * 梯度 da=cost对a求偏导  2*(axi+b-yi)*xi
 * 梯度 da=cost对y求偏导  2*(axi+b-yi)*1
 *
 * @author wangsan
 * @date 2020/3/16
 * @see <a href="https://blog.csdn.net/qq_33612918/article/details/88117304">js</a>
 */
public class GradientDescent {
    double a;
    double b;
    double[] xs;
    double[] ys;
    double step;
    double inaccuracy;
    int count;

    // inspect
    double[] as;
    double[] bs;
    int sample;

    public static void main(String[] args) {
        GradientDescent gd = new GradientDescent();
        gd.a = 1;
        gd.b = 1;
        gd.xs = new double[] {1.0, 2.3, 3.6, 4.3, 5, 7, 6.2, 9};
        gd.ys = new double[] {7.0, 9.3, 15.2, 17, 20, 26, 22.2, 30};
        gd.step = 0.001;
        gd.inaccuracy = 1e-3;
        gd.count = 30;
        gd.sample = 10;
        gd.as = new double[gd.count / gd.sample + 1];
        gd.bs = new double[gd.count / gd.sample + 1];

        // normalize
        gd.train();
        System.out.println(String.format("y=%sx+%s", gd.a, gd.b));

        // draw
        gd.draw();
    }

    public void train() {
        int j = 0;
        for (int i = 0; i < count; i++) {
            double da = da(a, b, xs, ys);
            double db = db(a, b, xs, ys);
            a = a - step * da;
            b = b - step * db;
            double cost = cost(a, b, xs, ys);

            // inspect
            if (i % sample == 0 || i == count - 1) {
                as[j] = a;
                bs[j] = b;
                System.out.println("第 " + (i + 1) + " 次 da: " + da);
                System.out.println("第 " + (i + 1) + " 次 db: " + da);
                System.out.println("第 " + (i + 1) + " 次 cost: " + cost + " a: " + a + " b: " + b);
                j++;
            }

            if (Math.abs(da) < inaccuracy && Math.abs(db) < inaccuracy) {
                System.out.println("count: " + i);
                return;
            }

            if (Math.abs(cost) < inaccuracy) {
                System.out.println("count: " + i);
                return;
            }
        }
    }

    public double cost(double a, double b, double[] xs, double[] ys) {
        double sum = 0.0;
        for (int i = 0; i < xs.length; i++) {
            sum += (a * xs[i] + b - ys[i]) * (a * xs[i] + b - ys[i]);
        }
        return sum;
    }

    public double da(double a, double b, double[] xs, double[] ys) {
        double sum = 0.0;
        for (int i = 0; i < xs.length; i++) {
            sum += (a * xs[i] + b - ys[i]) * xs[i];
        }
        return sum;
    }

    public double db(double a, double b, double[] xs, double[] ys) {
        double sum = 0.0;
        for (int i = 0; i < xs.length; i++) {
            sum += (a * xs[i] + b - ys[i]);
        }
        return sum;
    }

    public void draw() {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);
        // 创建及设置窗口
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // 添加 "Hello World" 标签
        frame.getContentPane().add(new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // point
                g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.setColor(Color.RED);
                for (int i = 0; i < xs.length; i++) {
                    g2d.drawLine((int) (xs[i] * 10), (int) (ys[i] * 10), (int) (xs[i] * 10), (int) (ys[i] * 10));
                }

                // line
                for (int i = 0; i < as.length; i++) {
                    g2d.setColor(new Color(0, 0, i * 50 % 256));
                    g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2d.drawLine(0 * 10, (int) (0 * 10 + bs[i]) * 10, 10 * 10, (int) (as[i] * 10 + bs[i]) * 10);
                }
            }
        });

        // 显示窗口
        frame.setVisible(true);
        // 窗体居中显示
        frame.setLocationRelativeTo(null);
    }
}
