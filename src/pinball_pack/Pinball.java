package pinball_pack;
import jgame.*;
import jgame.platform.*;

@SuppressWarnings("serial")
public class Pinball extends JGEngine {
	
	Player player;
	Stick stick;
	Ball ball;
	int gameStarted = 0;
	int level = 1;

	public static void main(String [] args) {
		new Pinball(new JGPoint(Integer.parseInt(args[0]),Integer.parseInt(args[1])));
	}

	/** Application constructor. */
	public Pinball(JGPoint size)
	{
		initEngine(size.x,size.y);
	}

	/** Applet constructor. */
	public Pinball()
	{
		initEngineApplet();
	}

	public void initCanvas()
	{
		setCanvasSettings(20,15,18,18,null,null,null);
	}
	
	

	public void initGame()
	{
		defineImage(
				"wall", // graphic name
				"-", 5, // tile name and tile cid (in case we use it as a tile)
				"wall.jpg", // file
			"-" // graphical operation (may be one or two out of
				    //"x","y", "l","r","u")
			);
		setBGImage("wall");
		setFrameRate(60,2);
		//create a new game
		newGame();
	}
	
	public void doFrame()
	{
		if(gameStarted == 1)
		{
			// Move all objects.
			moveObjects(
				null,// object name prefix of objects to move (null means any)
				0    // object collision ID of objects to move (0 means any)
			);
			checkCollision(
					2, // cids of objects that our objects should collide with
					1  // cids of the objects whose hit() should be called
				);
			if(getKey(KeyEnter))
			{
				clearKey(KeyEnter);
				removeObject(ball);
				removeObject(stick);
				newGame();
			}
			if(getKey(KeyEsc))
			{
				stop();
				System.exit(0);
			}
		}
		else
		{
			if(getKey(KeyEnter))
			{
				clearKey(KeyEnter);
				gameStarted = 1;
			}
			if(getKey(KeyEsc))
			{
				stop();
				System.exit(0);
			}
		}
	}

	public void paintFrame()
	{
		if(gameStarted == 0)
		{
			drawString("Press ENTER to Start The Game",pfWidth()/2,pfHeight()/2,0);
			drawString("Press ESC to Exit",pfWidth()/2,pfHeight()/2+20,0);
		}
		drawString("Lives : "+player.getLives(),5,5,-1);
		drawString("Score : "+player.getScore(),5,15,-1);
		drawString("Level : "+level,5,25,-1);
		if(player.getLives()==0)
		{
			drawString("Game over",pfWidth()/2,pfHeight()/2,0);
			if(level == -1)
			{
				drawString("You are a real Champion , Congratulations ..!!",pfWidth()/2,pfHeight()/2+10,0);
			}
			else if(level == 5)
			{
				drawString("You were really great..!!",pfWidth()/2,pfHeight()/2+10,0);
			}
			else if(level == 4)
			{
				drawString("You were good .. !!",pfWidth()/2,pfHeight()/2+10,0);
			}
			else if(level == 3)
			{
				drawString("You were Okay .. !!",pfWidth()/2,pfHeight()/2+10,0);
			}
			else
			{
				drawString("Not Up to the mark ... Better Luck Next Time .. !!",pfWidth()/2,pfHeight()/2+10,0);
			}
			drawString("Press Enter To start a new Game ... !!!",pfWidth()/2,pfHeight()/2+20,0);
			drawString("Press Esc to Exit ... !!!",pfWidth()/2,pfHeight()/2+30,0);
		}
		if(player.getScore()>=100 && player.getScore()<=110)
		{
			level = 2;
			drawString("!!! ...... Welcome to Level 2 ...... !!!",pfWidth()/2,pfHeight()/2,0);
		}
		if(player.getScore()>=200 && player.getScore()<=210)
		{
			level = 3;
			drawString("!!! ...... Welcome to Level 3 ...... !!!",pfWidth()/2,pfHeight()/2,0);
		}
		if(player.getScore()>=300 && player.getScore()<=310)
		{
			level = 4;
			drawString("!!! ...... Welcome to Level 4 ...... !!!",pfWidth()/2,pfHeight()/2,0);
		}
		if(player.getScore()>=400 && player.getScore()<=410)
		{
			level = 5;
			drawString("!!! ...... Welcome to Level 5 ...... !!!",pfWidth()/2,pfHeight()/2,0);
		}
		if(player.getScore() >=500)
		{
			level = -1;
			player.setLives(0);
		}
	}
	
	public void newGame()
	{
		stick = new Stick();
		ball = new Ball();
		player = new Player();
		level = 1;
	}

	/** Our user-defined object. */
	class Ball extends JGObject {

		/** Constructor. */
		Ball () {
			// Initialise game object by calling an appropriate constructor
			// in the JGObject class.
			super("Ball",true,0,0,1,null);
			
			xspeed = 1;
			yspeed = 1;
		}

		/** Update the object. This method is called by moveObjects. */
		public void move() {
			// A very "classic" behaviour:
			// bounce off the borders of the screen.
			if (x >  pfWidth()-8 && xspeed>0) xspeed = -xspeed;
			if (x <            8 && xspeed<0) xspeed = -xspeed;
			if (y > pfHeight()-8 && yspeed>0)
				{
					player.looseLife();
					x=random(0,random(0,pfWidth()));
					y=0;
				}
			if (y < 8 && yspeed<0) yspeed = -yspeed;
			if(player.getLives()==0)
			{
				if (x >  pfWidth()-8 && xspeed>0) xspeed = -xspeed;
				y=16;
				yspeed=0;
			}			
		}

		/** Draw the object. */
		public void paint() {
			// Draw a Red ball
			setColor(JGColor.red);
			drawOval(x,y,16,16,true,true);
			setBBox((int)(x-8),(int)(y-8),32,32);
		}
		public void hit(JGObject obj)
		{
			xspeed=random(-2,2);
			player.addScore(5);
			if(level == 1)
			{
				yspeed = -1;
			}
			if(level == 2)
			{
				yspeed = -1.2;
			}
			if(level == 3)
			{
				yspeed = -1.4;
			}
			if(level == 4)
			{
				yspeed = -1.6;
			}
			if(level == 5)
			{
				yspeed = -1.8;
			}
		}

	} /* end class Ball */
	
	
	
	class Stick extends JGObject
	{
		double stickWidth=50;
		double stickHeight=7;
			Stick()
			{
				super("stick",true,0,0,2,null);
				x=pfWidth()/2;
				y=pfHeight()-7;
				xspeed=0;
				yspeed=0;
			}
			public void move()
			{
				if(x>(pfWidth()-((int)stickWidth/2)))
				{
					x=pfWidth()-((int)stickWidth/2);
					xspeed=0;
				}
				if(x<((int)stickWidth/2))
				{
					x=(int)stickWidth/2;
					xspeed=0;
				}
				if(getKey(KeyLeft))
				{
					clearKey(KeyLeft);
					x+= xspeed= -6;
					xspeed=0;
				}
				if(getKey(KeyRight))
				{
					clearKey(KeyRight);
					x += xspeed= +6;
					xspeed=0;
				}
				
			}
			public void paint()
			{
				setColor(JGColor.green);
				drawRect(x,y,stickWidth,stickHeight,true,true);
				setBBox((int)(x-stickWidth/2),(int)(y-stickHeight/2),(int)(stickWidth+stickWidth/2),(int)(stickHeight+stickHeight/2));
			}
			
		}

}