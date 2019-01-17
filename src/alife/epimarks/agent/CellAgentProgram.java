/**
 * 
 */
package alife.epimarks.agent;

import java.util.HashMap;
import java.util.Map;

import alife.epimarks.agent.Cellula.Type;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;

/**
 * @author lifeth
 *
 */
public class CellAgentProgram implements AgentProgram {

	private StringBuilder sb = new StringBuilder();
	private Map<String, String> actions = new HashMap<String, String>();

	/**
	 * 
	 */
	public CellAgentProgram() {
		this.init();
	}

	@Override
	public synchronized Action compute(Percept p) {

		sb.setLength(0);

		/*for (String code : actions.keySet()) {

			Object value = p.getAttribute(code);

			if (value != null) {

				if ((boolean) value) {
					if (sb.length() > 0)
						sb.append(", ");

					sb.append(actions.get(code));
				}
			}
		}*/
		
		boolean mark = (boolean) p.getAttribute("MARKING");
		
		if(mark){
			return new Action(EAction.MARK.name());
		}

		/*if (sb.length() > 0)
			return new Action(sb.toString());*/

		return new Action(EAction.NONE.name());
	}

	@Override
	public void init() {

		this.actions.put(Type.METABOLIC.name(), EAction.BUILD_ANCHORS.name());
		this.actions.put(Type.TRANSPORTER.name(), EAction.BUILD_RECEPTORS.name() + ", " + EAction.BUILD_TRANSPORTERS);
		this.actions.put(Type.CONSUMPTION.name(), EAction.BUILD_RECEPTORS.name());
		this.actions.put(Type.REPRODUCTIVE.name(), EAction.BUILD_RECEPTORS.name());
		this.actions.put(EAction.MARK.name(), EAction.MARK.name());
	}

	public enum EAction {
		BUILD_ANCHORS, BUILD_RECEPTORS, BUILD_TRANSPORTERS, MARK, NONE
	}
}
