package top.sailingsan.dl4j.cookbook;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.reader.impl.transform.TransformProcessRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;

import lombok.extern.slf4j.Slf4j;

/**
 * 推理
 */
@Slf4j
public class CustomerRetentionPredictionApi {

    private static Schema generateSchema() {
        final Schema schema = new Schema.Builder()
                .addColumnString("RowNumber")
                .addColumnInteger("CustomerId")
                .addColumnString("Surname")
                .addColumnInteger("CreditScore")
                .addColumnCategorical("Geography", Arrays.asList("France", "Germany", "Spain"))
                .addColumnCategorical("Gender", Arrays.asList("Male", "Female"))
                .addColumnsInteger("Age", "Tenure")
                .addColumnDouble("Balance")
                .addColumnsInteger("NumOfProducts", "HasCrCard", "IsActiveMember")
                .addColumnDouble("EstimatedSalary")
                .build();
        return schema;

    }

    private static RecordReader applyTransform(RecordReader recordReader, Schema schema) {
        final TransformProcess transformProcess = new TransformProcess.Builder(schema)
                .removeColumns("RowNumber", "CustomerId", "Surname")
                .categoricalToInteger("Gender")
                .categoricalToOneHot("Geography")
                .removeColumns("Geography[France]")
                .build();
        final TransformProcessRecordReader transformProcessRecordReader =
                new TransformProcessRecordReader(recordReader, transformProcess);
        return transformProcessRecordReader;

    }

    private static RecordReader generateReader(File file) throws IOException, InterruptedException {
        final RecordReader recordReader = new CSVRecordReader(1, ',');
        recordReader.initialize(new FileSplit(file));
        final RecordReader transformProcessRecordReader = applyTransform(recordReader, generateSchema());
        return transformProcessRecordReader;
    }

    public static INDArray generateOutput(File inputFile, String modelFilePath)
            throws IOException, InterruptedException {
        final File modelFile = new File(modelFilePath);
        // 获取网络
        final MultiLayerNetwork network = ModelSerializer.restoreMultiLayerNetwork(modelFile);
        final RecordReader recordReader = generateReader(inputFile);
        //final INDArray array = RecordConverter.toArray(recordReader.next());
        // 获取正则化器（数据预处理）
        final NormalizerStandardize normalizerStandardize = ModelSerializer.restoreNormalizerFromFile(modelFile);
        //normalizerStandardize.transform(array);
        final DataSetIterator dataSetIterator = new RecordReaderDataSetIterator.Builder(recordReader, 1).build();
        normalizerStandardize.fit(dataSetIterator);
        dataSetIterator.setPreProcessor(normalizerStandardize);
        return network.output(dataSetIterator);

    }

    public static void main(String[] args) throws IOException, InterruptedException {

        INDArray indArray =
                CustomerRetentionPredictionApi.generateOutput(new ClassPathResource("test.csv").getFile(), "model.zip");
        String message = "";
        for (int i = 0; i < indArray.rows(); i++) {
            if (indArray.getDouble(i, 0) > indArray.getDouble(i, 1)) {
                message += "Customer " + (i + 1) + " predict： " + indArray.getRow(i) + "-> Happy Customer\n";
            } else {
                message += "Customer " + (i + 1) + " predict： " + indArray.getRow(i) + "-> Unhappy Customer\n";
            }
        }
        System.out.println(message);

    }
}
