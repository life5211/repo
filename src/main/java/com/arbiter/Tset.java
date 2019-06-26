package com.arbiter;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
/**
 * 主方法
 */
public class Tset{
	public static void main(String args[])
	{
		MyFrame app = new MyFrame();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
 
/**
 * 主框架类
 */
class MyFrame extends JFrame{
	
	private final static int PRIMARY_ROW = 10;//初级行数
	private final static int PRIMARY_COL = 10;//初级列数
	private final static int PRIMARY_BOMB = 10;//初级雷数
	
	private final static int MEDIUM_ROW = 15;//中级行数
	private final static int MEDIUM_COL = 20;//中级列数
	private final static int MEDIUM_BOMB = 40;//中级雷数
	
	private final static int SENIOR_ROW = 20;//高级行数
	private final static int SENIOR_COL = 35;//高级列数
	private final static int SENIOR_BOMB = 120;//高级雷数
	
	private final static int SUPER_ROW = 30;//超难行数
	private final static int SUPER_COL = 50;//超难列数
	private final static int SUPER_BOMB = 350;//超难雷数
	
	private static int row = PRIMARY_ROW;//行数
	private static int col = PRIMARY_COL;//列数
	private static int bombnum = PRIMARY_BOMB;//雷数
	private static int blocknum = row * col;//雷区方格数
	private static int leftblocknum = blocknum - bombnum;//剩余方格数
	private static int weight = row * 20 + 70;//高度
	private static int width = col * 20;//宽度
	
	JMenuBar mBar;//菜单栏
	JMenu gameMenu,gradeMenu;
	JMenuItem startItem,exitItem;
	JMenuItem primary,medium,senior,ssuper;//四个级别
	
	JPanel MenuPanel;//状态面板
	JLabel noflagbombnum;//未标记雷数标签
	private static int leftbombnum = bombnum;//未标记雷数
	
	JPanel BombPanel;//雷区面板
	Bomb [][]bomb;//雷区方格数组
	
	ImageIcon iconbomb = new ImageIcon("Image/bomb.jpg");
	ImageIcon iconbomb0 = new ImageIcon("Image/bomb0.jpg");
	ImageIcon iconflag = new ImageIcon("Image/flag.jpg");
	ImageIcon iconflag2 = new ImageIcon("Image/flag2.jpg");
	ImageIcon icon1 = new ImageIcon("Image/1.jpg");
	ImageIcon icon2 = new ImageIcon("Image/2.jpg");
	ImageIcon icon3 = new ImageIcon("Image/3.jpg");
	ImageIcon icon4 = new ImageIcon("Image/4.jpg");
	ImageIcon icon5 = new ImageIcon("Image/5.jpg");
	ImageIcon icon6 = new ImageIcon("Image/6.jpg");
	ImageIcon icon7 = new ImageIcon("Image/7.jpg");
	ImageIcon icon8 = new ImageIcon("Image/8.jpg");
	ImageIcon icon0 = new ImageIcon("Image/0.jpg");
	ImageIcon icons = new ImageIcon("Image/s.jpg");
	
	public MyFrame()//构造方法
	{
		super("扫雷");
		
		//添加菜单
		mBar = new JMenuBar(); 
        gameMenu = new JMenu("游戏");
        startItem = new JMenuItem("新游戏");
        gradeMenu = new JMenu("级别");
        exitItem = new JMenuItem("退出");
        primary = new JMenuItem("初级");
        medium = new JMenuItem("中级");
        senior = new JMenuItem("高级");
        ssuper = new JMenuItem("超难");
        mBar.add(gameMenu); 
        gameMenu.add(startItem);
        gameMenu.add(gradeMenu);
        gameMenu.add(exitItem);
        gradeMenu.add(primary);
        gradeMenu.add(medium);
        gradeMenu.add(senior);
        gradeMenu.add(ssuper);
        setJMenuBar(mBar);
		
        //添加菜单栏监听器
        startItem.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		setBomb();
        	}
        });
        primary.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		row = PRIMARY_ROW;
        		col = PRIMARY_COL;
        		bombnum = PRIMARY_BOMB;
        		setBomb();
        	}
        });
        medium.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		row = MEDIUM_ROW;
        		col = MEDIUM_COL;
        		bombnum = MEDIUM_BOMB;
        		setBomb();
        	}
        });
        senior.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		row = SENIOR_ROW;
        		col = SENIOR_COL;
        		bombnum = SENIOR_BOMB;
        		setBomb();
        	}
        });
        ssuper.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		row = SUPER_ROW;
        		col = SUPER_COL;
        		bombnum = SUPER_BOMB;
        		setBomb();
        	}
        });
        exitItem.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		System.exit(0);
        	}
        });
        
        Container c = getContentPane();
		//添加状态面板
		MenuPanel = new JPanel();
		noflagbombnum = new JLabel();
		MenuPanel.add(noflagbombnum);
		c.add(MenuPanel,BorderLayout.NORTH);
				
		//添加雷区面板
		BombPanel = new JPanel();
		c.add(BombPanel,BorderLayout.CENTER);
		
		setBomb();
		
	}
	
	public void setBomb()//随机布雷方法
	{
		//初始化雷区
		BombPanel.removeAll();//移除雷区所有组件
		bomb = new Bomb[row][col];
		BombPanel.setLayout(new GridLayout(row,col));
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
			{
				bomb[i][j] = new Bomb(i,j);
				bomb[i][j].addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						Bomb ebomb = (Bomb)e.getSource();
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							if(!ebomb.isClicked&&!ebomb.isRight)
							{
								if(!ebomb.isBomb)
								{
									open(ebomb);//打开方块
									isWin();//判断是否结束
								}
								else
								{
									for(int i=0;i<row;i++)
										for(int j=0;j<col;j++)
											if(bomb[i][j].isBomb)
												bomb[i][j].setIcon(iconbomb);
									ebomb.setIcon(icons);
									ebomb.setIcon(iconbomb0);
									isLose();
									setBomb();
								}
							}
						}
						else if(e.getButton() == MouseEvent.BUTTON3)
						{
							if (!ebomb.isClicked) 
						    {
						    	ebomb.Bombflag = (ebomb.Bombflag + 1) % 3;
						    	if (ebomb.Bombflag == 1) 
						    	{
						    		if (leftbombnum > 0) 
						    		{
						    			ebomb.setIcon(iconflag);
						    			ebomb.isRight = true;
						    			leftbombnum--;
						    		}
						    		else 
						    			ebomb.Bombflag = 0;
						    	}
						    	else if (ebomb.Bombflag == 2)
						    	{
						    		leftbombnum++;
						    		ebomb.setIcon(iconflag2);
						    		ebomb.isRight = false;
						    	}
						    	else 
						    		ebomb.setIcon(icons);
						    	noflagbombnum.setText("未标记雷数 ："+leftbombnum);
						    	isWin();
						    }
						}
					}
				});
				BombPanel.add(bomb[i][j]);
			}
		blocknum = row * col;//方格数
		leftbombnum = bombnum;//未标记地雷数
		leftblocknum = blocknum - bombnum;//未打开方格数
		noflagbombnum.setText("未标记雷数 ："+leftbombnum);
		weight = row * 20 + 70;//窗口高度
		width = col * 20;//窗口宽度
		setSize(width,weight);//设定窗口大小
		setResizable(false);//设定不可改变窗口大小
		//初始化方格
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
			{
				bomb[i][j].BombRoundCount = 9;
				bomb[i][j].Bombflag = 0;
				bomb[i][j].isBomb = false;
				bomb[i][j].isClicked = false;
				bomb[i][j].isRight = false;
				bomb[i][j].setIcon(icons);
			}
 
		//开始随机布雷
		Random rand = new Random();
		for(int i=0;i<bombnum;)
		{
			int x = rand.nextInt(row);
			int y = rand.nextInt(col);
			if(!bomb[x][y].isBomb)
			{
				bomb[x][y].isBomb = true;
				i++;
			}
		}
		calculateRoundBomb();
	}
	
	public void calculateRoundBomb()//计算周围雷数方法
	{
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
			{
				int count = 0;
				if(!bomb[i][j].isBomb)
					for(int x=i-1;x<=i+1;x++)
						for(int y=j-1;y<=j+1;y++)
							if(x>=0&&y>=0&&x<row&&y<col&&bomb[x][y].isBomb)
								count++;
				bomb[i][j].BombRoundCount = count;
			}
	}
	
	public void isWin()//判断是否挖完了所有雷
	{
		if(leftblocknum == 0)
		{
			JOptionPane.showMessageDialog(this,"恭喜你取得胜利!","胜利!",JOptionPane.INFORMATION_MESSAGE);
			setBomb();
		}
	}
	
	public void isLose()
	{
		noflagbombnum.setText("未标记雷数 ："+0);
		JOptionPane.showMessageDialog(this,"你踩到地雷了，点确定重新开始!","失败!",2);
	}
	
	public void isNull(Bomb clickbomb)//点击方格为空，翻开周围方格
	{
		int x = clickbomb.bx;
		int y = clickbomb.by;
		for(int i=x-1;i<=x+1;i++)
			for(int j=y-1;j<=y+1;j++)
				if(i>=0&&j>=0&&i<row&&j<col)
					if(!bomb[i][j].isBomb&&!bomb[i][j].isClicked&&!bomb[i][j].isRight)
						open(bomb[i][j]);
	}
	
	public void open(Bomb clickbomb)//点击打开方格
	{
		clickbomb.isClicked = true;
		leftblocknum--;
		if(clickbomb.BombRoundCount > 0)
		{
			if(clickbomb.BombRoundCount == 1)
				clickbomb.setIcon(icon1);
			else if(clickbomb.BombRoundCount == 2)
				clickbomb.setIcon(icon2);
			else if(clickbomb.BombRoundCount == 3)
				clickbomb.setIcon(icon3);
			else if(clickbomb.BombRoundCount == 4)
				clickbomb.setIcon(icon4);
			else if(clickbomb.BombRoundCount == 5)
				clickbomb.setIcon(icon5);
			else if(clickbomb.BombRoundCount == 6)
				clickbomb.setIcon(icon6);
			else if(clickbomb.BombRoundCount == 7)
				clickbomb.setIcon(icon7);
			else if(clickbomb.BombRoundCount == 8)
				clickbomb.setIcon(icon8);
		}
		else
		{
			clickbomb.setIcon(icon0);
			isNull(clickbomb);
		}
	}
}
 
/**
 * 雷区方格类
 */
final class Bomb extends JButton{
	
	int bx,by;//方块所在位置
	int BombRoundCount;//周围雷数
	int Bombflag;//探雷标记
	boolean isBomb;//是否是雷
	boolean isClicked;//是否点击左键
	boolean isRight;//是否点击右键
	public static int blockopennum = 0;//打开方格数
	
	public Bomb(int x,int y)//构造方法
	{
		bx = x;
		by = y;
		BombRoundCount = 9;
		Bombflag = 0;
		isBomb = false;
		isClicked = false;
		isRight = false;
	}
}
