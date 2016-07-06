import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Image;

//17050


//20350

public class Dodger extends Applet implements KeyListener, Runnable
{

    boolean pressed, left, right, up, down, gameOver = false;
    int x = 500, y = 250, timeUnit = 0, score = 0;
    int stage = 3, addStage = 0;
    int numToken = 1, addToken = 0;
    int freezeTime = 0, invincible = 0;
    Random rd = new Random ();
    Color darkGreen = new Color (0, 50, 0);
    Color purple = new Color (50, 0, 50);
    Color tokenBlue = new Color (180, 200, 250);
    Color tokenGreen = new Color (100, 250, 120);
    Color yellow = new Color (254, 254, 0);
    Image FTokenImage, ITokenImage, ScoreTokenImage;

    Image backbuffer;
    Graphics bg;
    Thread t = null;
    int width = 1000, height = 500;

    // 1 - blue (normal)
    // 2 - orange (chaser)
    // 3 - green (non-square)
    // 4 - purple (speedy)

    int[] [] EnemyData = {

	///*
		//  {i, x, y,dx,dy, lx, ly, s, t},
		    {0, 0, 0, 0, 0, 50, 50, 1, 1},  //0
		    {0, 0, 0, 0, 0, 25, 25, 2, 1},  //0
		    {0, 0, 0, 0, 0, 25, 25, 3, 1},  //0
		    {0, 0, 0, 0, 0, 10, 10, 1, 2},  //1000
		    {0, 0, 0, 0, 0, 50, 50, 4, 1},  //2000
		    {0, 0, 0, 0, 0, 15, 15, 5, 4},  //3000
		    {0, 0, 0, 0, 0, 15, 15, 6, 4},  //4000
		    {0, 0, 0, 0, 0, 5, 100, 3, 3},  //5000
		    {0, 0, 0, 0, 0, 100, 5, 3, 3},  //6000
		    {0, 0, 0, 0, 0, 25, 25, 1, 2},  //7000
		    {0, 0, 0, 0, 0, 15, 15, 7, 4},  //8000
		    {0, 0, 0, 0, 0, 15, 15, 8, 4},  //9000
		    {0, 0, 0, 0, 0, 50, 50, 5, 1},  //10000
		    {0, 0, 0, 0, 0, 80, 40, 1, 3},  //11000
		    {0, 0, 0, 0, 0, 40, 80, 1, 3},  //12000
		    {0, 0, 0, 0, 0, 25, 25, 2, 2},  //13000
		    {0, 0, 0, 0, 0, 15, 15, 9, 4},  //14000
	//*/

	/*
	//  {i, x, y,dx,dy, lx, ly, s, t},
	    {0, 0, 0, 0, 0, 50, 50, 1, 1},  //0
	    {0, 0, 0, 0, 0, 50, 50, 1, 1},  //0
	    {0, 0, 0, 0, 0, 50, 50, 1, 1},  //0
	    {0, 0, 0, 0, 0, 50, 50, 1, 1},  //1000
	    {0, 0, 0, 0, 0, 50, 50, 1, 1},  //2000
	    {0, 0, 0, 0, 0, 50, 50, 2, 1},  //3000
	    {0, 0, 0, 0, 0, 50, 50, 2, 1},  //4000
	    {0, 0, 0, 0, 0, 50, 50, 2, 1},  //5000
	    {0, 0, 0, 0, 0, 50, 50, 2, 1},  //6000
	    {0, 0, 0, 0, 0, 50, 50, 2, 1},  //7000
	    {0, 0, 0, 0, 0, 50, 50, 3, 1},  //8000
	    {0, 0, 0, 0, 0, 50, 50, 3, 1},  //9000
	    {0, 0, 0, 0, 0, 50, 50, 3, 1},  //10000
	    {0, 0, 0, 0, 0, 50, 50, 3, 1},  //11000
	    {0, 0, 0, 0, 0, 50, 50, 3, 1},  //12000
	    {0, 0, 0, 0, 0, 50, 50, 4, 1},  //13000
	    {0, 0, 0, 0, 0, 50, 50, 4, 1},  //14000
	*/

	/*  {i, x, y,dx,dy, lx, ly, s, t},
	    {0, 0, 0, 0, 0, 75, 75, 1, 1},  //0
	    {0, 0, 0, 0, 0, 50, 50, 2, 1},  //0
	    {0, 0, 0, 0, 0, 25, 25, 3, 1},  //0
	    {0, 0, 0, 0, 0, 15, 15, 1, 2},  //1000
	    {0, 0, 0, 0, 0, 15, 15, 5, 4},  //2000
	    {0, 0, 0, 0, 0, 50, 50, 1, 5},  //3000
	    {0, 0, 0, 0, 0, 15, 15, 5, 4},  //4000
	    {0, 0, 0, 0, 0, 50, 50, 1, 5},  //5000
	    {0, 0, 0, 0, 0, 100, 5, 9, 3},  //6000
	    {0, 0, 0, 0, 0, 5, 100, 1, 3},  //7000
	    {0, 0, 0, 0, 0, 100, 20, 1, 5},  //8000
	    {0, 0, 0, 0, 0, 25, 25, 2, 1},  //9000
	    {0, 0, 0, 0, 0, 35, 35, 3, 1},  //10000
	    {0, 0, 0, 0, 0, 15, 15, 6, 4},  //11000
	    {0, 0, 0, 0, 0, 25, 25, 1, 2},  //12000
	    {0, 0, 0, 0, 0, 45, 45, 2, 1},  //13000
	    {0, 0, 0, 0, 0, 55, 55, 3, 1},  //14000
	*/ 
	    
	};

    // 1 - blue
    // 2 - green (time freeze)
    // 3 - green (invincible)

    int[] [] TokenData = {
	//  {x, y, v, t},
	    {0, 0, 0, 1},  //0
	    {0, 0, 0, 1},  //750
	    {0, 0, 0, 1},  //1500
	    {0, 0, 0, 2},  //2250
	    {0, 0, 0, 1},  //3000
	    {0, 0, 0, 1},  //3750
	    {0, 0, 0, 1},  //4500
	    {0, 0, 0, 3},  //5250
	    {0, 0, 0, 1},  //6000
	    {0, 0, 0, 1},  //6750
	    {0, 0, 0, 1},  //7500
	    {0, 0, 0, 2},  //8250
	    {0, 0, 0, 1},  //9000
	    {0, 0, 0, 1},  //9750
	    {0, 0, 0, 1},  //10500
	    {0, 0, 0, 3},  //11250
	    {0, 0, 0, 1},  //12000
	    {0, 0, 0, 1},  //12750
	    {0, 0, 0, 1},  //13500
	    {0, 0, 0, 2},  //14250
	};

    Enemy[] ENEMY = new Enemy [EnemyData.length];
    Token[] TOKEN = new Token [TokenData.length];

    public void init ()
    {
	setSize (width, height);
	addKeyListener (this);
	backbuffer = createImage (width, height);
	bg = backbuffer.getGraphics ();

	for (int i = 0 ; i < EnemyData.length ; i++)
	{
	    Enemy E = new Enemy (EnemyData [i] [0], EnemyData [i] [1], EnemyData [i] [2], EnemyData [i] [3], EnemyData [i] [4], EnemyData [i] [5], EnemyData [i] [6], EnemyData [i] [7], EnemyData [i] [8]);
	    ENEMY [i] = E;
	}
	for (int i = 0 ; i < TokenData.length ; i++)
	{
	    Token T = new Token (TokenData [i] [0], TokenData [i] [1], TokenData [i] [2], TokenData [i] [3]);
	    TOKEN [i] = T;
	}

	FTokenImage = getImage (getCodeBase (), "Graphics/FTokenImage.png");
	ITokenImage = getImage (getCodeBase (), "Graphics/ITokenImage.png");
	ScoreTokenImage = getImage (getCodeBase (), "Graphics/ScoreTokenImage.png");
    }


    public void start ()
    {
	new Thread (this).start ();
    }


    public void run ()
    {
	try
	{

	    while (true)
	    {
		bg.setColor (Color.white);
		bg.fillRect (0, 0, width, height);

		TokenBehaviour ();
		PlayerMovement ();
		EnemyBehaviour ();

		timeUnit++;
		if (timeUnit == 35)
		{
		    score += 50;
		    addStage += 50;
		    addToken += 50;
		    timeUnit = 0;
		}
		if (addStage == 1000)
		{
		    stage++;
		    addStage = 0;
		}
		if (addToken == 750)
		{
		    numToken++;
		    addToken = 0;
		}

		bg.setColor (Color.red);
		bg.drawString ("Stage: " + (stage - 2), 800, 430);
		bg.drawString ("Score: " + score, 800, 450);

		if (freezeTime > 0)
		{
		    freezeTime--;
		    bg.drawString ("Frozen Time Remaining: " + freezeTime, 800, 470);
		}
		if (invincible > 0)
		{
		    invincible--;
		    bg.drawString ("Invincible Time Remaining: " + invincible, 800, 490);
		}

		if (gameOver)
		{
		    repaint ();

		    while (gameOver)
		    {
		    }
		}

		repaint ();
		Thread.sleep (10);//5
	    }
	}
	catch (InterruptedException ie)
	{
	}
    }


    public void TokenBehaviour ()
    {
	for (int i = 0 ; i < numToken ; i++)
	{
	    if (TOKEN [i].i == 0)
	    {
		TOKEN [i].x = rd.nextInt (985);
		TOKEN [i].y = rd.nextInt (485);
		TOKEN [i].i = 1;
	    }
	    if ((x + 20) >= TOKEN [i].x && x <= (TOKEN [i].x + 15) && (y + 20) >= TOKEN [i].y && y <= (TOKEN [i].y + 15) && TOKEN [i].v == true)
	    {
		TOKEN [i].v = false;

		if (TOKEN [i].t == 1)
		    score += 500;
		else if (TOKEN [i].t == 2)
		    freezeTime = 500;
		else if (TOKEN [i].t == 3)
		    invincible = 500;

	    }
	    else if (TOKEN [i].v == true)
	    {
		if (TOKEN [i].t == 1)
		    bg.drawImage (ScoreTokenImage, TOKEN [i].x, TOKEN [i].y, 20, 20, this);
		if (TOKEN [i].t == 2)
		    bg.drawImage (FTokenImage, TOKEN [i].x, TOKEN [i].y, 20, 20, this);
		if (TOKEN [i].t == 3)
		    bg.drawImage (ITokenImage, TOKEN [i].x, TOKEN [i].y, 20, 20, this);
	    }
	}
    }


    public void EnemyBehaviour ()
    {
	for (int i = 0 ; i < stage ; i++)
	{

	    if (ENEMY [i].i == 0)
	    {
		ENEMY [i].x = rd.nextInt ((1000 - ENEMY [i].lx) / ENEMY [i].s) * ENEMY [i].s;
		ENEMY [i].y = rd.nextInt ((500 - ENEMY [i].ly) / ENEMY [i].s) * ENEMY [i].s;
		ENEMY [i].dx = rd.nextInt (2) + 1;
		ENEMY [i].dy = rd.nextInt (2) + 1;
	    }
	    if (ENEMY [i].i < 70)
		ENEMY [i].i++;
	    else
	    {

		if (ENEMY [i].t != 5)
		{

		    if (ENEMY [i].t == 2)
		    {
			if (ENEMY [i].x > x)
			    ENEMY [i].dx = 1;
			else if (ENEMY [i].x < x)
			    ENEMY [i].dx = 2;
			if (ENEMY [i].y > y)
			    ENEMY [i].dy = 1;
			else if (ENEMY [i].y < y)
			    ENEMY [i].dy = 2;
		    }

		    if (freezeTime == 0)
		    {
			if (ENEMY [i].dx == 2 && ENEMY [i].x < (1000 - ENEMY [i].lx))
			    ENEMY [i].x += ENEMY [i].s;
			else if (ENEMY [i].x > 0)
			    ENEMY [i].x -= ENEMY [i].s;
			if (ENEMY [i].dy == 2 && ENEMY [i].y < (500 - ENEMY [i].ly))
			    ENEMY [i].y += ENEMY [i].s;
			else if (ENEMY [i].y > 0)
			    ENEMY [i].y -= ENEMY [i].s;
		    }

		    if (ENEMY [i].x >= (1000 - ENEMY [i].lx))
			ENEMY [i].dx = 1;
		    else if (ENEMY [i].x <= 0)
			ENEMY [i].dx = 2;
		    if (ENEMY [i].y >= (500 - ENEMY [i].ly))
			ENEMY [i].dy = 1;
		    else if (ENEMY [i].y <= 0)
			ENEMY [i].dy = 2;

		}

		if ((x + 20) >= ENEMY [i].x && x <= (ENEMY [i].x + ENEMY [i].lx) && (y + 20) >= ENEMY [i].y && y <= (ENEMY [i].y + ENEMY [i].ly) && invincible == 0)
		{
		    gameOver = true;
		}
	    }

	    if (ENEMY [i].t == 1)
		bg.setColor (Color.blue);
	    else if (ENEMY [i].t == 2)
		bg.setColor (Color.orange);
	    else if (ENEMY [i].t == 3)
		bg.setColor (darkGreen);
	    else if (ENEMY [i].t == 4)
		bg.setColor (purple);
	    else if (ENEMY [i].t == 5)
		bg.setColor (Color.black);
	    bg.fillRect (ENEMY [i].x, ENEMY [i].y, ENEMY [i].lx, ENEMY [i].ly);
	}
    }


    public void PlayerMovement ()
    {
	if (left && x > 0)
	    x -= 4;
	else if (right && x < 980)
	    x += 4;
	if (up && y > 0)
	    y -= 4;
	else if (down && y < 480)
	    y += 4;

	if (invincible > 0)
	    bg.setColor (yellow);
	else
	    bg.setColor (Color.red);
	bg.fillRect (x, y, 20, 20);
    }


    public void keyPressed (KeyEvent e)
    {
	int key = e.getKeyCode ();
	if (key == 37)
	{
	    pressed = true;
	    left = true;
	}
	if (key == 39)
	{
	    pressed = true;
	    right = true;
	}
	if (key == 38)
	{
	    pressed = true;
	    up = true;
	}
	if (key == 40)
	{
	    pressed = true;
	    down = true;
	}
    }


    public void keyReleased (KeyEvent e)
    {
	int key = e.getKeyCode ();
	if (key == 37)
	{
	    pressed = false;
	    left = false;
	}
	if (key == 39)
	{
	    pressed = true;
	    right = false;
	}
	if (key == 38)
	{
	    pressed = true;
	    up = false;
	}
	if (key == 40)
	{
	    pressed = true;
	    down = false;
	}
    }


    public void keyTyped (KeyEvent e)
    {
    }


    public void update (Graphics g)
    {
	g.drawImage (backbuffer, 0, 0, this);
    }


    public void paint (Graphics g)
    {
	update (g);
    }
}


