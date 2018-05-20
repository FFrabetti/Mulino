package game.mind;

import game.general.GameAction;
import game.general.GameState;

public abstract class Strategy {

	private GameAction selectedAction;
//	private ThinkingStatus thinkingStatus;
	
	public GameAction getSelectedAction() {
		return selectedAction;
	}
	
//  FUNZIONE VECCHIA
	public void setSelectedAction(GameAction selectedAction) {
		this.selectedAction = selectedAction;
	}
	
//	public ThinkingStatus getThinkingStatus() {
//		return thinkingStatus;
//	}
//
//	public void setThinkingStatus(ThinkingStatus thinkingStatus) {
//		this.thinkingStatus = thinkingStatus;
//	}
	
	public abstract void chooseAction(GameState state);
	/*chooseAction:
		start think about the action
		set a thinkingStatus periodically (setThinkingStatus)
			even if the function hasn't finished yet
	*/
}
