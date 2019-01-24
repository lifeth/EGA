/**
 * 
 */
package alife.epimarks.gui;

/**
 * This serves as template for individual-cell-molecules communication to send/receive
 * signals among them.
 * 
 * @author lifeth
 *
 */
public interface ISignal {

	/**
	 * Sends/Receives a message with info to perform any action.
	 * 
	 * @param command
	 *            describes what to do.
	 * @param param
	 */
	public void processSignal(int command, Integer param);

}
