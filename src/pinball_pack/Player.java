package pinball_pack;

public class Player
{
	
	private int score;			
	private int lives;			

	
	public Player()
	{
        lives = 5;
		score = 0;
	}

	
	public int getScore ()
	{
		return score;
	}

	
	public int getLives ()
	{
		return lives;
	}

	
	public void addScore (int plus)
	{
		score += plus;
	}

	public void looseLife ()
	{
		lives --;
	}

	public void setLives(int life)
	{
		lives = life;
	}
}
