package main;

public class Game implements Runnable {
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;

	public Game() {
		gamePanel = new GamePanel();
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus(); // 해당 컴포넌트에 포커스를 얻도록 한다(Game 클래스가 생성되면 gamePanel에 포커스를 줌)
		startGameLoop();
	}
	
	public void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
		
		while(true) {
			
			now = System.nanoTime();
			if(now - lastFrame >= timePerFrame) {
				
				gamePanel.repaint();
				lastFrame = now;
			}
			
		}
		
	}
}
