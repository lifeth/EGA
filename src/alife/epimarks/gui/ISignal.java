/**
 * 
 */
package alife.epimarks.gui;

/**
 * This serves as template for individual-cell-molecules communication to send
 * signals among them.
 * 
 * @author lifeth
 *
 */
public interface ISignal {

	/**
	 * Sends a message to communicate info to perform any action.
	 * 
	 * @param command
	 *            describes what to do.
	 */
	public void sendSignal(String command);

	/**
	 * Receives a message with info to perform any action.
	 * 
	 * @param command
	 *            describes what to do.
	 */
	public void receiveSignal(String command);
}
