package top.sailingsan.dl4j.lesson1.nd4j;

import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Nd4jGetAndSet {
    public static void main(String[] args) {
        INDArray nd = Nd4j.create(new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, new int[] {2, 6});
        log.info("2*6 nd is \n {}", nd);

        log.info("0,3 value is  {}", nd.getDouble(0, 3));
        nd.putScalar(0, 3, 100);
        log.info("0,3 value is  {}", nd.getDouble(0, 3));

        NdIndexIterator iter = new NdIndexIterator(2, 6);
        while (iter.hasNext()) {
            long[] index = iter.next();
            log.info("{} value is {}", index, nd.getDouble(index));
        }

        log.info("row 0 values is {}", nd.getRow(0));
        log.info("row 0,1 values is {}", nd.getRows(0, 1), nd.getRows(0, 1));

        INDArray array2 = Nd4j.create(new float[] {1, 3, 5, 7, 9, 11});
        nd.putRow(0, array2);
        log.info("nd is \n {}", nd);

        nd.getRow(0).addi(1.0);
        log.info("nd is \n {}", nd);

        array2.putScalar(0, 3, 33);
        log.info("0,3 value is  {}", nd.getDouble(0, 3));

        log.info("hstack is \n {}", Nd4j.hstack(nd, nd));
        log.info("vstack is \n {}", Nd4j.vstack(nd, nd));
        log.info("concat is \n {}", Nd4j.concat(0, nd, nd));
        log.info("concat is \n {}", Nd4j.concat(1, nd, nd));

        INDArray ones = Nd4j.ones(2, 2);
        INDArray padded = Nd4j.pad(ones, new int[] {1, 1});
        log.info("ones\n {} \npadded\n {}",ones,padded);

    }

}
