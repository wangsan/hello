/*******************************************************************************
 * Copyright (c) 2020 Konduit K.K.
 * Copyright (c) 2015-2019 Skymind, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ******************************************************************************/

package top.sailingsan.dl4j.cookbook.face.reid;

import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.inputs.InvalidInputTypeException;
import org.deeplearning4j.nn.conf.layers.samediff.SameDiffLambdaVertex;
import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class CosineLambdaVertex extends SameDiffLambdaVertex {

	@Override
	public SDVariable defineVertex(SameDiff sameDiff, VertexInputs inputs) {
		SDVariable input1 = inputs.getInput(0);
		SDVariable input2 = inputs.getInput(1);
		return sameDiff.expandDims(sameDiff.math.cosineSimilarity(input1, input2, 1, 2, 3), 1);
	}

	@Override
	public InputType getOutputType(int layerIndex, InputType... vertexInputs) throws InvalidInputTypeException {
		return InputType.feedForward(1);
	}

	public static void main(String[] args) {

		SameDiff samediff = SameDiff.create();
		INDArray input1 = Nd4j.rand(2, 3, 4, 5);
		SDVariable variable1 = samediff.var("input1", input1);
		INDArray input2 = Nd4j.rand(2, 3, 4, 5);
		SDVariable variable2 = samediff.var("input2", input2);


		samediff.expandDims(samediff.math.cosineSimilarity(variable1, variable2, 1, 2, 3),1);
		System.out.println(samediff.outputSingle(null, "expand_dims"));

		samediff.math.cosineSimilarity(variable1,variable2);
		System.out.println(samediff.outputSingle(null,"cosinesimilarity"));
	}
}
