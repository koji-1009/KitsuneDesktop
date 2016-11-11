import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.io.*;
import java.util.*;



//アニメーションクラス
class Animation{
	Graphics g;
	JPanel p;
	int x,y;		//表示位置
	int interval[];	//1コマ毎の表示間隔
	Image image[];	
	int exist = 0;	//0:表示 1:非表示
	int repeat = 0;	//0:繰り返さない 1:繰り返し
	int disappear = 0;	//0:再生終了時に消えない 1:消える
	int number = 0;	//現在のコマ
	int total = 0;	//総コマ数
	int count = 0;
	
	Animation(String filename, JPanel p){
		String aniname;
		File file = new File(filename);
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			total = readParam(br,"total = (\\d+)");
			repeat = readParam(br,"repeat = (\\d+)");
			disappear = readParam(br,"disappear = (\\d+)");
			
			image = new Image[total];
			interval = new int[total];
			for(int i=0;i<total;i++){
				try{
					br.readLine();
					image[i] = Toolkit.getDefaultToolkit().getImage(br.readLine());
					interval[i] = readParam(br,"interval = (\\d+)");
				}catch(IOException e){
				}
			}
		
			
		}catch(FileNotFoundException e){
			System.out.println(e);
		}
	
		this.repeat = repeat;
		this.disappear = disappear;
		this.interval = interval;
		this.p = p;
	}	
	
	public static int readParam(BufferedReader br,String format) {
		try{
			String str = br.readLine();
			Scanner scanner = new Scanner(str);
	 
			scanner.findInLine(format);
			MatchResult result = scanner.match();
			 
			return	Integer.parseInt(result.group(1));

		}catch(IOException e){
			System.out.println( "string is not match" );
		}
		return 0;
    }
	
	public void init(int x,int y){
		exist = 1;
		number = 0;
		count = 0;
		this.x = x;
		this.y = y;
	}
	
	public void exit(){
		exist = 0;
		number = 0;
		count = 0;
	}
	
	public void idle(){
		if(exist == 1){
			if(count == interval[number]){
				number++;
				if(number == total){
					if(repeat == 0){
						number--;
					}
					else {
						number = 0;
					}
				}
				count = 0;
			}
			count ++;
		}
	}
	
	public void paint(Graphics g){
		if (image[number] != null && exist == 1){
           g.drawImage(image[number], x, y,p);
        }
	}
	
	public void paint(int width,int height,Graphics g){
		if (image[number] != null && exist == 1){
           g.drawImage(image[number], x, y,width,height,p);
        }
	}
}

class GameKitsune{
	int x=200,y=100;
	Image back;
	JPanel panel;
	int flag = 0;
	Animation nojanoja;
	Animation stay;
	Animation throwball;
	Animation mae_ga_mienee;
	Animation shadow01;
	Animation shadow02;

	Color bgcolor;
	KitsuneMode mode;
	BallParam bp;
	Batter batter;
	
	int stay_count = 0;
	int stay_wait = 90;
	int throw_flag = 0;
	
	int mae_count =0;
	int mae_wait = 90;
	int noja_count = 0;
	int noja_wait = 90;
	int makyu_count = 0;
	int makyu_wait = 15;
	int makyu_flag;

	
	GameKitsune(JPanel p){

		mode = mode.STAY;
        back = Toolkit.getDefaultToolkit().getImage("Animation/back.png");
		stay = new Animation("Animation/stay.txt",p);
		mae_ga_mienee = new Animation("Animation/mae_ga_mienee.txt",p);
		nojanoja = new Animation("Animation/nojanoja.txt",p);
		throwball = new Animation("Animation/throw.txt",p);
		shadow01 = new Animation("Animation/shadow.txt",p);
		shadow02 = new Animation("Animation/shadow.txt",p);
		
		bp = new BallParam(475,245,p);
		
		batter  = new Batter(p);
		batter.init();
		
		shadow01.init(400,300);
		shadow02.init(300,680);
		stay.init(400,100);
		
		panel = p;
	}
	
	enum BatterState{
		STAY,
		SWING,
		IMPACT
	}
	
	class Batter{
		int x,y;
		int swing_count = 0;
		int swing_wait = 20;
		int hit_second = 3;
		Animation stay;
		Animation swing;
		BatterState state;
		
		Batter(JPanel p){
			x = 200;
			y = 475;
			state = state.STAY;
			
			stay = new Animation("Animation/batter.txt",p);
			swing = new Animation("Animation/swing.txt",p);
		}
		
		public void init(){
			swing_count = 0;
			stay.init(x,y);
		}
		
		public void  idle(){
			switch(state){
				case STAY:
					stay.idle();
					break;
				case SWING:
					swing.idle();
					swing_count++;
					if(swing_count == hit_second){
						state = state.IMPACT;
					}
					if(swing_count == swing_wait){
						swing_count = 0;
						state = state.STAY;
					}
					break;
				case IMPACT:
					if(bp.CheckHit()==1){
						throw_flag = 0;
						mode = mode.HIT;
						bp.hit();
					}
					swing_count++;
					if(swing_count == hit_second+3){
						state = state.SWING;
					}
			}
		}
		
		public void swingStart(){
			stay.exit();
			swing.init(x,y);
		}
		
		public void paint(Graphics g){
			stay.paint(g);
			swing.paint(g);
			
		}
	}
	
	public enum BallMode{
		NORMAL,
		FAST,
		MAKYU,
		HIT
		;
		
		public BallMode randomMode(){
			int mode_num = (int)(3*Math.random());
			if(mode_num == 0)return NORMAL;
			if(mode_num == 1)return FAST;
			if(mode_num == 2)return MAKYU;
			return this;
		}
	}
	
	class BallParam{
		int x,y;
		int width,height;
		int hit_y;
		int hitbox_height;
		int v[];
		BallMode bmode;
		int makyu_count = 0;
		int makyu_wait = 30;
		Animation ball;
		Animation spin;
		
		BallParam(int x,int y,JPanel p){	
			this.x = x;
			this.y = y;
			
			width = 50;
			height = 50;
			bmode = bmode.NORMAL;
			bmode = bmode.randomMode();
			
			v = new int[3];
			v[0] = 15;
			v[1] = 30;
			v[2] = -40;
			hit_y = 600;
			hitbox_height = 30;
			ball = new Animation("Animation/ball.txt",p);
			spin = new Animation("Animation/spin.txt",p);
		}
		
		public void init(int x,int y){
			this.x = x;
			this.y = y;
			
			width = 50;
			height = 50;
			bmode = bmode.NORMAL;
			bmode = bmode.randomMode();
			makyu_count =0;
		}
		
		public void paintInit(){
			switch(bmode){
				case NORMAL:
					ball.init(x,y);
					break;
				case FAST:
					spin.init(x,y);
					break;
				case MAKYU:
					ball.init(x,y);
					break;
				default:
					break;
			}
		}
		
		public void idle(){
			switch(bmode){
				case NORMAL:
					this.y+=v[0];
					break;
				case FAST:
					this.y+=v[1];
					break;
				case MAKYU:
					if(y<400){
						this.y+=v[0];
					}
					else if(y >=400){
						spin.idle();
						if(spin.exist == 0){
							spin.init(x,y);
						}
						if(makyu_count==makyu_wait){
							this.y+=v[1];
						}
						else{
							makyu_count++;
							if(ball.exist == 1){
								ball.exit();
							}
						}
					}
					
					break;
				case HIT:
					this.y+=v[2];
					break;
			}
			ball.x = x;
			ball.y = y;
			spin.x = x;
			spin.y = y;
		}
		
		public void paint(Graphics g){
			ball.paint(g);
			switch(bmode){
				case NORMAL:
					ball.paint(g);
					break;
				case FAST:
					spin.paint(g);
					break;
				case MAKYU:
					ball.paint(g);
					spin.paint(g);
					break;
				case HIT:
					ball.paint(g);
					spin.paint(g);
					break;
				default:
					ball.paint(g);
					break;	
			}
			
		}
		
		public void exit(){
			ball.exit();
			spin.exit();
			x = 0;
			y = 0;
		}
		
		public int CheckHit(){
			if(0.5*Math.abs(hit_y-y)<hitbox_height){
				bmode = bmode.HIT;
				return 1;
			}
			return 0;
		}
		
		public int CheckMiss(){
			if(y>800){
				return 1;
			}
			return 0;
		}
		public void hit(){
			bmode = bmode.HIT;
		}
	}
	
	private enum KitsuneMode{
		STAY,
		THROW,
		HIT,
		MAE_GA_MIENEE,
		NOJANOJA
		;
	}
	
	
	public void idle(){
		
		switch(mode){
			case STAY:
				stay.idle();
				stay_count++;
				if(stay_count == stay_wait){
					stay.exit();
					stay_count = 0;
					bp.init(475,245);
					throwball.init(400,100);
					mode = mode.THROW;
				}
				break;
				
			case THROW:
				if(throw_flag == 0){
					if(throwball.number == 1){
						bp.paintInit();
						throw_flag = 1;
					}
				}
				else if(throw_flag == 1){
					bp.idle();
				}
				throwball.idle();
				
				if(bp.CheckMiss()==1){
					throw_flag = 0;
					bp.exit();
					throwball.exit();
					nojanoja.init(400,100);
					mode = mode.NOJANOJA;
				}
				
				break;
				
			case HIT:
				bp.idle();
				if(bp.y < 200){
					bp.exit();
					throwball.exit();
					mae_ga_mienee.init(400,100);
					mode = mode.MAE_GA_MIENEE;
				}
				break;
				
			case MAE_GA_MIENEE:
			
				mae_ga_mienee.idle();
				mae_count++;
				if(mae_count == mae_wait){
					mae_count = 0;
					mae_ga_mienee.exit();
					stay.init(400,100);	
					bp.init(475,245);					
					mode = mode.STAY;
				}
				break;
				
			case NOJANOJA:
			
				nojanoja.idle();
				noja_count++;
				if(noja_count == noja_wait){
					nojanoja.exit();
					noja_count = 0;
					stay.init(400,100);
					bp.init(475,245);
					mode = mode.STAY;
				}
				break;
				
			default:
			break;
		}
		batter.idle();
	}
	
	public void paint(Graphics g){
		if (back != null){
           g.drawImage(back, 0, 0, panel);
        }
		Graphics2D g2 = (Graphics2D)g;
		
		shadow01.paint(g);
		shadow02.paint(g);
		throwball.paint(g);
		nojanoja.paint(g);
		bp.paint(g);
		batter.paint(g);
		stay.paint(g);
		
		mae_ga_mienee.paint(g);
	}
	
	public void KeyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
			switch(batter.state){
				case STAY:
					batter.state = batter.state.SWING;
					batter.swingStart();
					break;
				default:
					break;
			}
		}        
    }
}

class TitleKitsune{
	Animation mainTitle;
	JPanel panel;
	TitleKitsune(JPanel p){
		mainTitle = new Animation("Animation/title.txt",p);
		mainTitle.init(0,0);
		panel = p;
	}
	
	public void idle(){
		
	}
	
	public void paint(Graphics g){
		mainTitle.paint(g);
	}
	
	public void KeyPressed(KeyEvent e){
	}
}

public class Kitsune extends JFrame{
    public static void main(String args[]){
        Kitsune frame = new Kitsune("ロリのバーさんのホームランダービー");
        frame.setVisible(true);
    }

    Kitsune(String title){
        setTitle(title);
        setSize(1000,800);
        setLocationRelativeTo(null);//初期画面表示位置を中央に
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//CLOSEでプログラム終了

        Container CP = getContentPane();//getContentPane()はJFrameクラスに定義されている
        //CP.setLayout(null);//レイアウトマネージャを停止

        //上部の背景色を橙に設定する
        JPanel panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        CP.add(panel, BorderLayout.NORTH);

        //Mainパネルの作成、フレームへのセット
        MainPanel MP = new MainPanel();
        CP.add(MP);
        //CP.remove(MP);//フレームを外す
        addKeyListener(MP);//KeyListenerをフレームにセット
        //CP.removeKeyListener(MP);//KeyListenerを外す
    }
}

enum GameScene{
	TITLE,
	GAME
	;
}

class MainPanel extends JPanel implements Runnable, KeyListener, MouseListener{
	GameScene gamescene;
    int x = 100, y = 100;
    boolean state = true;
    Thread t;
	long stepTime,startTime,endTime;
	GameKitsune kitsune;
	TitleKitsune title;
	
    MainPanel(){
        //setLayout(null);
		stepTime = 33;
        setBackground(Color.blue);//背景色を青に
        t = new Thread(this);//Thread instance
		kitsune = new GameKitsune(this);
		title = new TitleKitsune(this);
		gamescene = gamescene.TITLE;
        t.start();//Thread Start
    }

    public void run(){
        while(true){
			startTime = System.currentTimeMillis();
			
			switch(gamescene){
				case TITLE:
					title.idle();
					break;
				case GAME:
					kitsune.idle();
					break;
				default:
				break;
			}
            repaint();
			endTime = System.currentTimeMillis();
			
			try{
				t.sleep(stepTime - (endTime - startTime));
			}catch(InterruptedException e){
				
			}
        }
    }

    //JComponentによるpaintComponent method
    //JPanel は JComponent を継承している
    public void paintComponent(Graphics g){
        switch(gamescene){
			case TITLE:
				title.paint(g);
				break;
			case GAME:
				kitsune.paint(g);
				break;
			default:
			break;
		}
	
    }

    /**********************
        KeyEvent
    **********************/
    //Keyが押された場合
    public void keyPressed(KeyEvent e){
		 switch(gamescene){
			case TITLE:
				title.KeyPressed(e);
				gamescene = gamescene.GAME;
				break;
			case GAME:
				kitsune.KeyPressed(e);
				break;
			default:
			break;
		}
    }

    public void keyReleased(KeyEvent e){
	}

    public void keyTyped(KeyEvent e){
	}
	
	public void mouseClicked(MouseEvent e){
	}

	public void mouseEntered(MouseEvent e){
	}

	public void mouseExited(MouseEvent e){
	}

	public void mousePressed(MouseEvent e){
	}

	public void mouseReleased(MouseEvent e){
	}
}