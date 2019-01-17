/**
 * 
 */
package alife.epimarks.agent;

/**
 * @author lifeth
 *
 */
public class GeneInfo {

	private boolean state;
	private char[] tags;
	public static boolean showstate;

	/**
	 * @param state
	 */
	public GeneInfo(int state) {
		this.state = (state == 0 ? false : true);
	}

	/**
	 * @param state
	 * @param tags
	 */
	public GeneInfo(boolean state, char[] tags) {
		this.state = state;
		this.tags = tags;
	}

	public boolean isActive() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public char[] getTags() {
		return tags;
	}

	public void setTag(char[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {

		String st = showstate ? "<" + (this.isActive() ? 1 : 0) + ">" : "";
		
		 return st + "<<" + (this.tags == null ? "" : String.valueOf(this.tags)) + ">>";
	}
}
