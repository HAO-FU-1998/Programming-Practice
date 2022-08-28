package Tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;

//���B     3016218083


public class Tetris extends JFrame
{
	StageJPanel jPanel = new StageJPanel();
	
	public Tetris()
	{		
		add (jPanel);		
		//����
		setTitle ("����˹����(Tetris)");
		//��С
		setSize (500, 600);
		//���ӻ�
		setVisible (true);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo (null);
		jPanel.requestFocus();//��ý��� ���̻�ý���
		
	}
	public static void main (String[]args)
	{
		Tetris gameView = new Tetris();
	}

}
//�Զ�����̨��
class StageJPanel extends JPanel
{
	static final int MAX_WIDTH = 20;//��
	static final int MAX_HEIGHT = 30;
	static final int SQUARE = 20;//20px
	Shape shape;//���ڵ����ͼ��
	
	ImageIcon icon = new ImageIcon ("sb.jpg");//����ͼ
	//����һ����ʱ��
	Timer timer = new Timer(1000, new ShapeFallTask());
	int container [][] = new int [MAX_HEIGHT][MAX_WIDTH];
	//�ѵ����ͼ��
	
	public StageJPanel()
	{//���ͼ��
		this.shape = ShapeFactory.randomShape();
		
       //���̼����¼�
		KeyListener keyListner = new ShapeKeyListener();
		this.addKeyListener (keyListner);
		//������ʱ��
		timer.start();
	}
//ͼ���Զ�����
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
			//����
			fill();
			//��ͼ
			shape = ShapeFactory.randomShape();
			repaint();
		    }
			else
			{
				timer.stop();
				JOptionPane.showMessageDialog (StageJPanel.this, "����(Death)");
			}
		}
	}
		
}

//����ͼ�ε�����
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

	// �ڲ���
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
			repaint();//�ػ�
		}

		public void keyReleased (KeyEvent e) 
		{
			// TODO �Զ����ɷ������
		}

		public void keyTyped (KeyEvent e) 
		{
			// TODO �Զ����ɷ������
		}			
	}
	
	
	
	protected void paintComponent (Graphics g) 
	{
		super.paintComponent (g);//����
		g.setColor (Color.BLACK);
		g.drawImage (icon.getImage(), 0, 0, null);
		
		for (int i = 0; i <= MAX_HEIGHT; i++)
		{//����
	        g.drawLine (0, i * SQUARE, MAX_WIDTH * SQUARE, i * SQUARE);
		}
		for (int i = 0; i <= MAX_WIDTH; i++)
		{//����
			g.drawLine (i * SQUARE, 0, i * SQUARE, MAX_HEIGHT * SQUARE);
		}
        if (shape != null)
        {//��ͼ��
    	   shape.paint (g);
    	   g.setColor (Color.GREEN);
        }
        //����ԭ�е���ͼ��
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
	//�Ƿ�����ƶ�
	public Boolean isFall()
	{
		for (int i = 0; i < shape.values.length; i++)
		{
			for (int j = 0; j < shape.values[i].length; j++)
			{
				if (shape.values[i][j] == 1)
				{
					//������׷���false
				    if ((shape.y + i) == MAX_HEIGHT - 1)
				    {
					    return false;
				    }
				    //�����Ƿ���������ͼ��
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

//ͼ����
class Shape
{
	int [][] values;
	int x;
	int y;
	
	public Shape (int [][] values)
	{
		this.values = values;
	}		
    //ͼ�λ����Լ�
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
	//����
	public void fall()
	{
		y++;
	}
	
	//����
	public void left()
	{
		x--;
	}
	
	//����
    public void right()
    {
		x++;
	}
    
    //��ת
    public void rotate()
    {
    	int [][]_values = ShapeUtils.rotate (this.values);
    	this.values = _values;
   
	}
}

//ͼ�ι���
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
	  
	  //���ͼ��
     public static Shape randomShape ()
     {
		  int i = random.nextInt (shapes.length);
		  Shape shape = new Shape (shapes[i]);
		  return shape;
	  }
	  
  }
/**
 * ������
 * @author k59
 */
class ShapeUtils
{
	//��ת����
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