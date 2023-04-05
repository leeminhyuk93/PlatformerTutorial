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
		
		// 120 Frame Per Second : 초당 120번의 프레임 업데이트
		double timePerFrame = 1000000000.0 / FPS_SET; // 1초를 120으로 나누는 꼴이니 초당 120 프레임의 수치를 만들어 낼 수 있다.
		long lastFrame = System.nanoTime(); 
		long now = System.nanoTime();
		
		int frames = 0;
		long lastCheck = System.currentTimeMillis();
		
		while(true) {
			
			now = System.nanoTime(); // 현재 시스템 시간을 매번 갱신한다.
			if(now - lastFrame >= timePerFrame) { // 만약 (현재 시간 - 이전  시간)이 정해진 시간 범위보다 커질 경우 업데이트(repaint)하고 
												  // 뺄셈을 하는 '이전 시간' 값에 현재 시간을 넣어서 시간 비교 값을 '0'으로 만든다.
												  // 그럼 now에 들어 있는 값은 계속 증가하고 있으므로 원하는 타이밍에 업데이트(repaint) 해줄 수 있다.
				
				gamePanel.repaint();
				lastFrame = now;
				frames++;
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
				
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
