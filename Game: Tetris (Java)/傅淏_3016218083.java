package Tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;

//傅淏     3016218083


public class Tetris extends JFrame
{
	StageJPanel jPanel = new StageJPanel();
	
	public Tetris()
	{		
		add (jPanel);		
		//标题
		setTitle ("俄罗斯方块(Tetris)");
		//大小
		setSize (500, 600);
		//可视化
		setVisible (true);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo (null);
		jPanel.requestFocus();//获得焦点 键盘获得焦点
		
	}
	public static void main (String[]args)
	{
		Tetris gameView = new Tetris();
	}

}
//自定义舞台类
class StageJPanel extends JPanel
{
	static final int MAX_WIDTH = 20;//份
	static final int MAX_HEIGHT = 30;
	static final int SQUARE = 20;//20px
	Shape shape;//正在掉落的图形
	
	ImageIcon icon = new ImageIcon ("sb.jpg");//背景图
	//创建一个定时器
	Timer timer = new Timer(1000, new ShapeFallTask());
	int container [][] = new int [MAX_HEIGHT][MAX_WIDTH];
	//已掉落的图形
	
	public StageJPanel()
	{//随机图形
		this.shape = ShapeFactory.randomShape();
		
       //键盘监听事件
		KeyListener keyListner = new ShapeKeyListener();
		this.addKeyListener (keyListner);
		//启动定时器
		timer.start();
	}
//图形自动掉落
class ShapeFallTask implements ActionListener
{
	public void actionPerformed (ActionEvent e)
	{
		if (isFall())
		{
			shape.fall();
			repaint();
		}
		else
		{
			if (isAlive())
			{
			//复制
			fill();
			//换图
			shape = ShapeFactory.randomShape();
			repaint();
		    }
			else
			{
				timer.stop();
				JOptionPane.showMessageDialog (StageJPanel.this, "死亡(Death)");
			}
		}
	}
		
}

//复制图形到数组
 void fill()
 {
		for (int i = 0; i < shape.values.length; i++)
		{
			for (int j = 0; j < shape.values[i].length; j++)
			{
				if (shape.values[i][j] == 1)
				{
				container[shape.y + i][shape.x + j] = shape.values[i][j];
				}
			}
		}
  }

	// 内部类
	class ShapeKeyListener implements KeyListener
	{

		public void keyPressed (KeyEvent e) 
		{
			switch (e.getKeyCode())
			{
			    case KeyEvent.VK_LEFT:
				    shape.left();
				    break;
			    case KeyEvent.VK_RIGHT:
				    shape.right();
				    break;
			    case KeyEvent.VK_UP:
				    shape.rotate();
				    break;
			    case KeyEvent.VK_DOWN:
				    shape.fall();
				    break;
			
			}
			repaint();//重绘
		}

		public void keyReleased (KeyEvent e) 
		{
			// TODO 自动生成方法存根
		}

		public void keyTyped (KeyEvent e) 
		{
			// TODO 自动生成方法存根
		}			
	}
	
	
	
	protected void paintComponent (Graphics g) 
	{
		super.paintComponent (g);//擦除
		g.setColor (Color.BLACK);
		g.drawImage (icon.getImage(), 0, 0, null);
		
		for (int i = 0; i <= MAX_HEIGHT; i++)
		{//横线
	        g.drawLine (0, i * SQUARE, MAX_WIDTH * SQUARE, i * SQUARE);
		}
		for (int i = 0; i <= MAX_WIDTH; i++)
		{//竖线
			g.drawLine (i * SQUARE, 0, i * SQUARE, MAX_HEIGHT * SQUARE);
		}
        if (shape != null)
        {//画图形
    	   shape.paint (g);
    	   g.setColor (Color.GREEN);
        }
        //绘制原有掉落图形
        for (int i = 0; i < container.length; i++)
        {
    	    for (int j = 0; j < container[i].length; j++)
    	    {
    		    if (container[i][j] == 1)
    		    {
    			    g.fillRect ((j) * StageJPanel.SQUARE, 
    					   (i) * StageJPanel.SQUARE, 
    					   StageJPanel.SQUARE, StageJPanel.SQUARE);
    			   
    		   }
    	   }
       }
				
	}
	//是否可以移动
	public Boolean isFall()
	{
		for (int i = 0; i < shape.values.length; i++)
		{
			for (int j = 0; j < shape.values[i].length; j++)
			{
				if (shape.values[i][j] == 1)
				{
					//如果到底返回false
				    if ((shape.y + i) == MAX_HEIGHT - 1)
				    {
					    return false;
				    }
				    //检验是否碰到其他图形
				    if (container[shape.y + i + 1][shape.x + j] == 1)
				    {
					    return false;
					}
			    }
			}
		}
	return true;
	}
	
	public Boolean isLeft()
	{
		return false;
	}
	
	public Boolean isRight()
	{
		return false;
	}
	
	public boolean isAlive()
	{
		return true;
	}
	
}

//图形类
class Shape
{
	int [][] values;
	int x;
	int y;
	
	public Shape (int [][] values)
	{
		this.values = values;
	}		
    //图形绘制自己
	public void paint (Graphics g)
	{
		for (int i = 0; i < values.length; i++)
		{
			for (int j = 0; j < values[i].length; j++)
			{
				if (values[i][j] == 1)
				{
					g.fillRect ((x + j) * StageJPanel.SQUARE, (y + i) * StageJPanel.SQUARE, StageJPanel.SQUARE, StageJPanel.SQUARE);
				}		
			}			
		}		
	}	
	//下落
	public void fall()
	{
		y++;
	}
	
	//左移
	public void left()
	{
		x--;
	}
	
	//右移
    public void right()
    {
		x++;
	}
    
    //旋转
    public void rotate()
    {
    	int [][]_values = ShapeUtils.rotate (this.values);
    	this.values = _values;
   
	}
}

//图形工厂
class ShapeFactory
{
	 static Random random = new Random ();
	 static int [][][] shapes = 
	  {
			  {
				  {0,1,0,},
				  {0,1,0,},
				  {0,1,0,},				
			  },
			  {
				  {0,1,0},
				  {0,1,0},
				  {0,1,1},				
			  },
			  {
				  {0,0,0},
				  {1,1,0},
				  {1,1,0},
	
			  },
			  {
				  {1,1,1},
				  {0,1,0},
				  {0,0,0},
			  },
			  {
				  {0,0,0},
				  {1,1,0},
				  {0,1,1},
			  },
	  };
	  
	  //随机图形
     public static Shape randomShape ()
     {
		  int i = random.nextInt (shapes.length);
		  Shape shape = new Shape (shapes[i]);
		  return shape;
	  }
	  
  }
/**
 * 工具类
 * @author k59
 */
class ShapeUtils
{
	//旋转方法
	public static int [][] rotate (int [][] old)
	{
		int [][] temp = new int [old[0].length][old.length];
		int dst = old.length - 1;
		for (int i = 0; i < old.length; i++, dst--)
		{
			for (int j = 0; j < old[0].length; j++)
			{
				temp[j][dst] = old[i][j];
			}
		}
		return temp;
	}
}