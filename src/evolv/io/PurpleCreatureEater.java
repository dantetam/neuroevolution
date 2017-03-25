package evolv.io;

public class PurpleCreatureEater extends Creature {

	public PurpleCreatureEater(EvolvioColor evolvioColor, Board board) {
		this(evolvioColor, board, evolvioColor.random(0, Configuration.BOARD_WIDTH),
				evolvioColor.random(0, board.getBoardHeight()), 0, 0,
				evolvioColor.random(Configuration.MINIMUM_CREATURE_ENERGY, Configuration.MAXIMUM_CREATURE_ENERGY), 1,
				evolvioColor.random(0, 1), 1, 1, evolvioColor.random(0, 2 * EvolvioColor.PI), 0, "", "[PRIMORDIAL]",
				true, null, 1, evolvioColor.random(0, 1));
	}

	public PurpleCreatureEater(EvolvioColor evolvioColor, Board board, double tpx, double tpy, double tvx, double tvy,
			double tenergy, double tdensity, double thue, double tsaturation, double tbrightness, double rot,
			double tvr, String tname, String tparents, boolean mutateName, Brain brain, int tgen, double tmouthHue) {
		super( evolvioColor,  board,  tpx,  tpy,  tvx,  tvy,
			 tenergy,  tdensity,  thue,  tsaturation,  tbrightness,  rot,
			 tvr,  tname,  tparents,  mutateName,  brain,  tgen,  tmouthHue);
	}
	
	 
	
}
