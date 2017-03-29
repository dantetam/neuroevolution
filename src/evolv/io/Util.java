package evolv.io;

public class Util {

	public static int editDistance(String s, String t) {
		int[][] d = new int[s.length() + 1][t.length() + 1];
		for (int i = 1; i <= s.length(); i++) {
			d[i][0] = i;
		}
		for (int j = 1; j <= t.length(); j++) {
			d[0][j] = j;
		}
		
		for (int j = 1; j <= t.length(); j++) {
			for (int i = 1; i <= s.length(); i++) {
				int equals = s.charAt(i-1) == t.charAt(j-1) ? 0 : 1;
				d[i][j] = Math.min(d[i - 1][j - 1] + equals, Math.min(d[i - 1][j], d[i][j - 1]));
			}
		}
		return d[s.length()][t.length()];
	}
	
}
