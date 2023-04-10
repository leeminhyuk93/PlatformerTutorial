package main;

public class Game implements Runnable {
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 100;

	public Game() {
		gamePanel = new GamePanel();
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true); // 해당 컴포넌트가 포커스를 얻을 수 있도록 한다.
		gamePanel.requestFocus(); // 해당 컴포넌트에 포커스를 얻도록 한다(Game 클래스가 생성되면 gamePanel에 포커스를 줌)
		startGameLoop();
	}
	
	public void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		gamePanel.updateGame();
	}

	@Override
	public void run() {
		
		// 120 Frame Per Second : 초당 120번의 프레임 업데이트
		double timePerFrame = 1000000000.0 / FPS_SET; // 1초를 120으로 나누는 꼴이니 초당 120 프레임의 수치를 만들어 낼 수 있다.
		double timePerUpdate = 1000000000.0 / UPS_SET; 
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;		
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
				
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
				// Game 클래스에서 repaint를 통해 업데이트 할 때마다 frames 값이 1씩 증가한다.
				/* 
				 * paintComponent는 repaint에 의해서 반복적으로 호출된다. 원하는 타이밍마다 update 할 수 있다.(time per frame)
				 * 아래에서 조건부에 currentTimeMillis() 메소드를 활용하고 있다. 이는 협정세계시와 현재시간을 비교한 값으로 실제 시간이 얼마나 경과했는지를 검토할 때 활용한다.
				 * Millis Second 단위에서 1초는 1/1000 이다. currentTimeMillis()와 lastCheck의 차이가 1000(1초)가 넘어가면 
				 * 다시 그 차이를 0으로 만들고 화면에 frames의 변수 값을 출력하는 것이다.
				 */
			}
			
		}
		
	}
}
