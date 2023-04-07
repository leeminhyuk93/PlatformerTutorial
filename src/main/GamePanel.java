package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.*;

import static Utilz.Constants.PlayerConstant.*; // static 선언을 앞에 두면 클래스를 import하는 순간에 데이터를 로드한다.
import static Utilz.Constants.Directions.*; 

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private int playerDir = -1;
	private boolean moving = false;
	
	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		
		importImg();
		loadAnimations();
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	
	}
	
	private void loadAnimations() {
		animations = new BufferedImage[9][6];
		
		for(int j=0; j<animations.length; j++)
			for(int i=0; i<animations[j].length; i++)
				animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
			
	}

	private void importImg() {
		InputStream is = this.getClass().getResourceAsStream("/res/player_sprites.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280, 800);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
	}

	public void setDirection(int direction) {
		this.playerDir = direction;
		this.moving = true;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	private void updateAnimationTick() {
		/*
		 * aniSpeed가 30이므로 120프레임/4=30 이니까
		 * 1초에 4장의 스프라이트가 바뀌게 된다.
		 */
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction))
				aniIndex = 0;
		}
	}
	
	private void setAnimation() {
		if(moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
	}
	
	private void updatePos() {
		if(moving) {
			switch(playerDir) {
			case LEFT:
				xDelta -= 5;
				break;
			case UP:
				yDelta -= 5;
				break;
			case RIGHT:
				xDelta += 5;
				break;
			case DOWN:
				yDelta += 5;
				break;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//img = 원본, subImg = 원본에서 가져온 각각의 이미지
		updateAnimationTick();
		
		setAnimation();
		updatePos();
		
		g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, 256, 160, null);
	}

	

	

	
	
}


