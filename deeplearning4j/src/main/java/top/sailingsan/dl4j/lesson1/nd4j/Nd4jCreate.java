package top.sailingsan.dl4j.lesson1.nd4j;

import org.nd4j.linalg.factory.Nd4j;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Nd4jCreate {
    public static void main(String[] args) {
        log.info("nd4j zeros shape 1 is : \n {}", Nd4j.zeros(1));
        log.info("nd4j zeros shape 1,2 is : \n {}", Nd4j.zeros(1, 2));
        log.info("nd4j zeros shape 2,1 is : \n {}", Nd4j.zeros(2, 1));
        log.info("nd4j zeros shape 2,2 is : \n {}", Nd4j.zeros(2, 2));
        log.info("nd4j zeros shape 2,3,4 is : \n {}", Nd4j.zeros(2, 3, 4));

        log.info("nd4j zeros shape 3,5 is : \n {}", Nd4j.ones(3, 5));
        log.info("nd4j ones shape 3,5 is : \n {}", Nd4j.ones(3, 5));
        log.info("nd4j rands shape 3,5 is : \n {}", Nd4j.rand(3, 5));
        log.info("nd4j 高斯分布（平均值为0，标准差为1） shape 3,5 is : \n {}", Nd4j.randn(3, 5));

        log.info("nd4j is : \n {}", Nd4j.create(new float[] {2, 2, 2, 2}, new int[] {1, 4}));
        log.info("nd4j is : \n {}", Nd4j.create(new float[] {2, 2, 2, 2}, new int[] {4, 1}));
        log.info("nd4j is : \n {}", Nd4j.create(new float[] {2, 2, 2, 2}, new int[] {2, 2}));

        log.info("nd4j is : \n {}", Nd4j.linspace(1, 9, 9).reshape(3, 3));

    }
}
