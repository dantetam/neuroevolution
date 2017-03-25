package evolv.io;

public class Configuration {
	// EvolvioColor
	public static final float NOISE_STEP_SIZE = 0.1f;
	public static final int BOARD_WIDTH = 30;
	public static final int BOARD_HEIGHT = 30;
	public static final float SCALE_TO_FIXBUG = 100;
	public static final float TIME_STEP = 0.001f;
	public static final float MINIMUM_TEMPERATURE = -0.5f;
	public static final float MAXIMUM_TEMPERATURE = 1.0f;
	public static final int ROCKS_TO_ADD = 0;
	public static final int CREATURE_MINIMUM = 50;
	public static final String INITIAL_FILE_NAME = "PIC";

	// Board
	public static final float MINIMUM_CREATURE_ENERGY = 1.2f;
	public static final float MAXIMUM_CREATURE_ENERGY = 2.0f;
	public static final float MINIMUM_SURVIVABLE_SIZE = 0.06f;
	public static final float CREATURE_STROKE_WEIGHT = 0.6f;
	public static final int CREATURE_MINIMUM_INCREMENT = 5;
	public static final double MANUAL_BIRTH_SIZE = 1.2;
	public static final int TIMESTEPS_PER_YEAR = 100;
	public static final int POPULATION_HISTORY_LENGTH = 200;
	public static final double RECORD_POPULATION_EVERY = 0.02;
	public static final float THERMOMETER_MINIMUM = -2f;
	public static final float THERMOMETER_MAXIMUM = 2f;
	public static final float MINIMUM_ROCK_ENERGY_BASE = 0.8f;
	public static final float MAXIMUM_ROCK_ENERGY_BASE = 1.6f;
	public static final float ROCK_DENSITY = 5f;
	public static final double MAX_DETAILED_ZOOM = 3.5;
	public static final int LIST_SLOTS = 6;
	public static final double FLASH_SPEED = 80;
	
	public static final float DEATH_ENERGY_MULTIPLIER = 0.4f;
	public static final int DEATH_PIECES = 10;
	public static final float KILL_ENERGY_MULTIPLIER = 5f;
	
	public static final float SIZE_FIGHT_MULTIPLIER = 0.02f; //2% for every unit of yum above 100

	// Brain
	public static final int MEMORY_COUNT = 1;
	public static final int BRAIN_WIDTH = 3;
	/*
	 * Would like to add Brain Height. Will need to rework Brain to allow for
	 * configuration
	 */
	/*
	 * TODO can we consolidate the mutability, variability and mutate power into
	 * fewer?
	 */
	public static final float AXON_START_MUTABILITY = 0.0005f; //0.0005f
	public static final double STARTING_AXON_VARIABILITY = 1.0;

	// Axon
	public static final double MUTABILITY_MUTABILITY = 0.7;
	public static final int MUTATE_POWER = 9; //9

	// Creature
	public static final double ACCELERATION_ENERGY = 0.08;
	public static final double ACCELERATION_BACKWARDS_ENERGY = 0.1;
	public static final double SWIM_ENERGY = 0.008;
	public static final double TURN_ENERGY = 0.12; //0.12
	public static final double EAT_ENERGY = 0.07; //0.05
	public static final double EAT_SPEED = 0.5;
	public static final double EAT_WHILE_MOVING_INEFFICIENCY_MULTIPLIER = 1.0;
	public static final int ENERGY_HISTORY_LENGTH = 6;
	public static final double FIGHT_ENERGY = 0.02;
	public static final double INJURED_ENERGY = 0.25;
	public static final double METABOLISM_ENERGY = 0.004;
	public static final double MAX_VISION_DISTANCE = 10;
	public static final double FOOD_SENSITIVITY = 0.3;
	public static final float BRIGHTNESS_THRESHOLD = 0.7f;
	public static final double SAFE_SIZE = 1.25;
	public static final double MATURE_AGE = 0.01;
	public static final int HEARING_RANGE = 2;

	// NameGenerator
	public static final int MINIMUM_NAME_LENGTH = 3;
	public static final int MAXIMUM_NAME_LENGTH = 10;

	// SoftBody
	public static final float FRICTION = 0.004f;
	public static final float COLLISION_FORCE = 0.01f;
	public static final float FIGHT_RANGE = 2.0f;

	// Tile
	public static final float FOOD_GROWTH_RATE = 1.25f;
	public static final float MAX_GROWTH_LEVEL = 3.0f;
}
