package evolv.io;

import java.util.List;

public class Brain {
	public static final int NORMAL_FEATURES = 18;
	private static final int BRAIN_HEIGHT = NORMAL_FEATURES + Configuration.MEMORY_COUNT + 1;
	private static final String[] INPUT_LABELS = new String[BRAIN_HEIGHT];
	private static final String[] OUTPUT_LABELS = new String[BRAIN_HEIGHT];

	private final EvolvioColor evolvioColor;
	// Brain
	private final Axon[][][] axons;
	private final double[][] neurons;

	static {
		// input
		INPUT_LABELS[0] = "0Hue";
		INPUT_LABELS[1] = "0Sat";
		INPUT_LABELS[2] = "0Bri";
		INPUT_LABELS[3] = "1Hue";
		INPUT_LABELS[4] = "1Sat";
		INPUT_LABELS[5] = "1Bri";
		INPUT_LABELS[6] = "2Hue";
		INPUT_LABELS[7] = "2Sat";
		INPUT_LABELS[8] = "2Bri";
		INPUT_LABELS[9] = "3Hue";
		INPUT_LABELS[10] = "3Sat";
		INPUT_LABELS[11] = "3Bri";
		INPUT_LABELS[12] = "Size";
		INPUT_LABELS[13] = "MHue";
		INPUT_LABELS[14] = "SoundIn";
		INPUT_LABELS[15] = "SmellIn";
		INPUT_LABELS[16] = "X";
		INPUT_LABELS[17] = "Y";


		// output
		OUTPUT_LABELS[0] = "BodyHue";
		OUTPUT_LABELS[1] = "Accel";
		OUTPUT_LABELS[2] = "Turn";
		OUTPUT_LABELS[3] = "Eat";
		OUTPUT_LABELS[4] = "Fight";
		OUTPUT_LABELS[5] = "Birth";
		OUTPUT_LABELS[6] = "Mem1";
		OUTPUT_LABELS[7] = "Mem2";
		OUTPUT_LABELS[8] = "Mem3";
		OUTPUT_LABELS[9] = "Mem4";
		OUTPUT_LABELS[10] = "MouthHue";
		OUTPUT_LABELS[11] = "SoundOut";
		OUTPUT_LABELS[12] = "Novel1Out";
		OUTPUT_LABELS[13] = "Novel2Out";
		OUTPUT_LABELS[14] = "Novel3Out";
		OUTPUT_LABELS[15] = "Novel4Out";
		OUTPUT_LABELS[16] = "Novel5Out";
		OUTPUT_LABELS[17] = "Novel6Out";

		// TODO do we need a memory and const output?

		// memory
		for (int i = 0; i < Configuration.MEMORY_COUNT; i++) {
			INPUT_LABELS[i + NORMAL_FEATURES] = "Memory";
			OUTPUT_LABELS[i + NORMAL_FEATURES] = "Memory";
		}

		// TODO is this the bias?
		// const
		INPUT_LABELS[BRAIN_HEIGHT - 1] = "Const.";
		OUTPUT_LABELS[BRAIN_HEIGHT - 1] = "Const.";
	}

	public Brain(EvolvioColor evolvioColor, Axon[][][] tbrain, double[][] tneurons) {
		this.evolvioColor = evolvioColor;
		// initialize brain
		if (tbrain == null) {
			axons = new Axon[Configuration.BRAIN_WIDTH - 1][BRAIN_HEIGHT][BRAIN_HEIGHT - 1];
			neurons = new double[Configuration.BRAIN_WIDTH][BRAIN_HEIGHT];
			for (int x = 0; x < Configuration.BRAIN_WIDTH - 1; x++) {
				for (int y = 0; y < BRAIN_HEIGHT; y++) {
					for (int z = 0; z < BRAIN_HEIGHT - 1; z++) {
						double startingWeight = (Math.random() * 2 - 1) * Configuration.STARTING_AXON_VARIABILITY;
						axons[x][y][z] = new Axon(startingWeight, Configuration.AXON_START_MUTABILITY);
					}
				}
			}
			for (int x = 0; x < Configuration.BRAIN_WIDTH; x++) {
				for (int y = 0; y < BRAIN_HEIGHT; y++) {
					if (y == BRAIN_HEIGHT - 1) {
						neurons[x][y] = 1;
					} else {
						neurons[x][y] = 0;
					}
				}
			}
		} else {
			axons = tbrain;
			neurons = tneurons;
		}
	}

	public Brain evolve(List<Creature> parents) {
		int parentsTotal = parents.size();
		Axon[][][] newBrain = new Axon[Configuration.BRAIN_WIDTH - 1][BRAIN_HEIGHT][BRAIN_HEIGHT - 1];
		double[][] newNeurons = new double[Configuration.BRAIN_WIDTH][BRAIN_HEIGHT];
		float randomParentRotation = this.evolvioColor.random(0, 1);
		for (int x = 0; x < Configuration.BRAIN_WIDTH - 1; x++) {
			for (int y = 0; y < BRAIN_HEIGHT; y++) {
				for (int z = 0; z < BRAIN_HEIGHT - 1; z++) {
					float axonAngle = EvolvioColor.atan2((y + z) / 2.0f - BRAIN_HEIGHT / 2.0f,
							x - Configuration.BRAIN_WIDTH / 2) / (2 * EvolvioColor.PI) + EvolvioColor.PI;
					Brain parentForAxon = parents
							.get((int) (((axonAngle + randomParentRotation) % 1.0f) * parentsTotal)).getBrain();
					newBrain[x][y][z] = parentForAxon.axons[x][y][z].mutateAxon();
				}
			}
		}
		for (int x = 0; x < Configuration.BRAIN_WIDTH; x++) {
			for (int y = 0; y < BRAIN_HEIGHT; y++) {
				float axonAngle = EvolvioColor.atan2(y - BRAIN_HEIGHT / 2.0f, x - Configuration.BRAIN_WIDTH / 2)
						/ (2 * EvolvioColor.PI) + EvolvioColor.PI;
				Brain parentForAxon = parents.get((int) (((axonAngle + randomParentRotation) % 1.0f) * parentsTotal))
						.getBrain();
				newNeurons[x][y] = parentForAxon.neurons[x][y];
			}
		}
		return new Brain(this.evolvioColor, newBrain, newNeurons);
	}
	
	//Ideally a string that encodes brain structure (decide which features to choose from global set of features)
	//as well as strength and existence of connections (axons vs dendroids?)
	public String[] getBrainRepresentation() {
		int axonDataLen = axons.length * axons[0].length * axons[0][0].length;
		int neuronDataLen = neurons.length * neurons[0].length;
		String[] result = new String[axonDataLen + neuronDataLen + 2];
		
		result[0] = axons.length + " " + axons[0].length + " " + axons[0][0].length;
		int index = 2;
		for (int x = 0; x < axons.length; x++) {
			for (int y = 0; y < axons[0].length; y++) {
				for (int z = 0; z < axons[0][0].length; z++) {
					Axon axon = axons[x][y][z];
					result[index] = x + " " + y + " " + z + " " + axon.getWeight() + " " + axon.getMutationRate();
					index++;
				}
			}
		}
		
		result[1] = neurons.length + " " + neurons[0].length;
		for (int x = 0; x < neurons.length; x++) {
			for (int y = 0; y < neurons[0].length; y++) {
				result[index] = x + " " + y + " " + neurons[x][y];
				index++;
			}
		}
		
		return result;
	}
	
	//TODO: 
	public static Brain loadBrainFromString(EvolvioColor evolvioColor, String[] data) {
		Axon[][][] axons = new Axon[Configuration.BRAIN_WIDTH - 1][BRAIN_HEIGHT][BRAIN_HEIGHT - 1];
		double[][] neurons = new double[Configuration.BRAIN_WIDTH][BRAIN_HEIGHT];
		for (int x = 0; x < Configuration.BRAIN_WIDTH - 1; x++) {
			for (int y = 0; y < BRAIN_HEIGHT; y++) {
				for (int z = 0; z < BRAIN_HEIGHT - 1; z++) {
					double startingWeight = (Math.random() * 2 - 1) * Configuration.STARTING_AXON_VARIABILITY;
					axons[x][y][z] = new Axon(startingWeight, Configuration.AXON_START_MUTABILITY);
				}
			}
		}
		for (int x = 0; x < Configuration.BRAIN_WIDTH; x++) {
			for (int y = 0; y < BRAIN_HEIGHT; y++) {
				if (y == BRAIN_HEIGHT - 1) {
					neurons[x][y] = 1;
				} else {
					neurons[x][y] = 0;
				}
			}
		}
		return new Brain(evolvioColor, axons, neurons);
	}

	public void draw(float scaleUp, int mX, int mY) {
		final float neuronSize = 0.4f;
		this.evolvioColor.noStroke();
		this.evolvioColor.fill(0, 0, 0.4f);
		this.evolvioColor.rect((-1.7f - neuronSize) * scaleUp, -neuronSize * scaleUp,
				(2.4f + Configuration.BRAIN_WIDTH + neuronSize * 2) * scaleUp,
				(BRAIN_HEIGHT + neuronSize * 2) * scaleUp);

		this.evolvioColor.ellipseMode(EvolvioColor.RADIUS);
		this.evolvioColor.strokeWeight(2);
		this.evolvioColor.textSize(0.58f * scaleUp);
		this.evolvioColor.fill(0, 0, 1);
		for (int y = 0; y < BRAIN_HEIGHT; y++) {
			this.evolvioColor.textAlign(EvolvioColor.RIGHT);
			this.evolvioColor.text(INPUT_LABELS[y], (-neuronSize - 0.1f) * scaleUp,
					(y + (neuronSize * 0.6f)) * scaleUp);
			this.evolvioColor.textAlign(EvolvioColor.LEFT);
			this.evolvioColor.text(OUTPUT_LABELS[y], (Configuration.BRAIN_WIDTH - 1 + neuronSize + 0.1f) * scaleUp,
					(y + (neuronSize * 0.6f)) * scaleUp);
		}
		this.evolvioColor.textAlign(EvolvioColor.CENTER);
		for (int x = 0; x < Configuration.BRAIN_WIDTH; x++) {
			for (int y = 0; y < BRAIN_HEIGHT; y++) {
				this.evolvioColor.noStroke();
				double val = neurons[x][y];
				this.evolvioColor.fill(neuronFillColor(val));
				this.evolvioColor.ellipse(x * scaleUp, y * scaleUp, neuronSize * scaleUp, neuronSize * scaleUp);
				this.evolvioColor.fill(neuronTextColor(val));
				this.evolvioColor.text(EvolvioColor.nf((float) val, 0, 1), x * scaleUp,
						(y + (neuronSize * 0.6f)) * scaleUp);
			}
		}
		if (mX >= 0 && mX < Configuration.BRAIN_WIDTH && mY >= 0 && mY < BRAIN_HEIGHT) {
			for (int y = 0; y < BRAIN_HEIGHT; y++) {
				if (mX >= 1 && mY < BRAIN_HEIGHT - 1) {
					drawAxon(mX - 1, y, mX, mY, scaleUp);
				}
				if (mX < Configuration.BRAIN_WIDTH - 1 && y < BRAIN_HEIGHT - 1) {
					drawAxon(mX, mY, mX + 1, y, scaleUp);
				}
			}
		}
	}

	public void input(double[] inputs) {
		int end = Configuration.BRAIN_WIDTH - 1;
		for (int i = 0; i < NORMAL_FEATURES; i++) {
			neurons[0][i] = inputs[i];
		}
		for (int i = 0; i < Configuration.MEMORY_COUNT; i++) {
			neurons[0][NORMAL_FEATURES + i] = neurons[end][NORMAL_FEATURES + i];
		}
		neurons[0][BRAIN_HEIGHT - 1] = 1;
		for (int x = 1; x < Configuration.BRAIN_WIDTH; x++) {
			for (int y = 0; y < BRAIN_HEIGHT - 1; y++) {
				double total = 0;
				for (int input = 0; input < BRAIN_HEIGHT; input++) {
					total += neurons[x - 1][input] * axons[x - 1][input][y].getWeight();
				}
				if (x == Configuration.BRAIN_WIDTH - 1) {
					if (y < NORMAL_FEATURES) {
						if (Math.abs(total) > 10) {
							total = Math.signum(total) * 10;
						}
					}
					neurons[x][y] = total;
				} else {
					neurons[x][y] = sigmoid(total);
				}
			}
		}
	}

	public double[] outputs() {
		int end = Configuration.BRAIN_WIDTH - 1;
		double[] output = new double[NORMAL_FEATURES];
		for (int i = 0; i < NORMAL_FEATURES; i++) {
			if (Math.abs(neurons[end][i]) > 10) {
				output[i] = Math.signum(neurons[end][i]) * 10;
			}
			else {
				output[i] = neurons[end][i];
			}
		}
		output[11] = Math.max(0, output[11]);
		return output;
	}

	private void drawAxon(int x1, int y1, int x2, int y2, float scaleUp) {
		this.evolvioColor.stroke(neuronFillColor(axons[x1][y1][y2].getWeight() * neurons[x1][y1]));
		this.evolvioColor.line(x1 * scaleUp, y1 * scaleUp, x2 * scaleUp, y2 * scaleUp);
	}

	private double sigmoid(double input) {
		return 1.0f / (1.0f + Math.exp(-input));
	}

	private int neuronFillColor(double d) {
		if (d >= 0) {
			return this.evolvioColor.color(0, 0, 1, (float) (d));
		} else {
			return this.evolvioColor.color(0, 0, 0, (float) (-d));
		}
	}

	private int neuronTextColor(double d) {
		if (d >= 0) {
			return this.evolvioColor.color(0, 0, 0);
		} else {
			return this.evolvioColor.color(0, 0, 1);
		}
	}
}