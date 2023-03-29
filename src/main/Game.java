package main;

public class Game {
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;

	public Game() {
		gamePanel = new GamePanel();
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus(); // 해당 컴포넌트에 포커스를 얻도록 한다(Game 클래스가 생성되면 gamePanel에 포커스를 줌)
	}
}
