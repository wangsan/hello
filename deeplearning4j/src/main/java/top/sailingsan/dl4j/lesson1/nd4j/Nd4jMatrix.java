package top.sailingsan.dl4j.lesson1.nd4j;

import java.util.Arrays;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class Nd4jMatrix {
    public static void main(String[] args) {
        // 1x2的行向量
        INDArray nd = Nd4j.create(new float[] {1, 2}, new int[] {1, 2});
        // 2x1的列向量
        INDArray nd2 = Nd4j.create(new float[] {3, 4}, new int[] {2, 1}); //vector as column
        // 创造两个2x2的矩阵
        INDArray nd3 = Nd4j.create(new float[] {1, 3, 2, 4}, new int[] {2, 2}); //elements arranged column major
        INDArray nd4 = Nd4j.create(new float[] {3, 4, 5, 6}, new int[] {2, 2});

        //1x2 and 2x1 -> 1x1
        INDArray ndv = nd.mmul(nd2);
        System.out.println(ndv + ", shape = " + Arrays.toString(ndv.shape()));

        //1x2 and 2x2 -> 1x2
        ndv = nd.mmul(nd4);
        log.info("{} , shape = {} ", ndv, ndv.shape());

        //2x2 and 2x2 -> 2x2
        ndv = nd3.mmul(nd4);
        log.info("{} , shape = {} ", ndv, ndv.shape());
    }
}
