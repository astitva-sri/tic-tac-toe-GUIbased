import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MyGame extends JFrame implements ActionListener {

    JLabel heading, clockLabel;
    Font font = new Font("Sans Sheriff",Font.BOLD,20);
    
    JPanel mainPanel;

    JButton[] btns = new JButton[9];

    // Game Instance variables
     int gameChances[] = {2,2,2,2,2,2,2,2,2};
     int activePlayer = 0;

    //  winning sequence variables
     int wps[][] = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int winner = 2;
    boolean gameOver = false;

    MyGame(){             // Constructor

        System.out.println("Game run ");
        setTitle("Tic Tac Toe");
        setSize(500,500);
        ImageIcon icon = new ImageIcon("static\\title.png");
        setIconImage(icon.getImage());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGui();
        setVisible(true);
    }
    private void createGui(){

        // Top and Bottom Border //
        this.getContentPane().setBackground(Color.BLUE);
        this.setLayout(new BorderLayout());

        // Heading Border Controls (Header)//
        heading = new JLabel("Tic Tac toe");    
        heading.setFont(font);
        heading.setForeground(Color.WHITE);
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(heading, BorderLayout.NORTH);

        // Clock COntrol (Footer) //
        clockLabel = new JLabel("Clock");
        clockLabel.setFont(font);
        clockLabel.setForeground(Color.WHITE);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(clockLabel, BorderLayout.SOUTH);

        Thread t = new Thread(){   // Show Current Date and Time
            public void run() {
                try{
                    while(true){
                        String datetime = new Date().toLocaleString ();
                        clockLabel.setText(datetime);
                        Thread.sleep(1000);   
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();

        // Center Panel and Button Controls//
        mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(3,3));
         for (int i =0; i<9; i++){
            JButton btn = new JButton();

            btn.setIcon(new ImageIcon());
            btn.setFont(font);
            btn.setBackground(Color.WHITE);
            mainPanel.add(btn);
            btns[i] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i));
         }
         this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
      JButton currentButton = (JButton)e.getSource();
      String nameStr = currentButton.getName();
      System.out.println(nameStr);
        int name = Integer.parseInt(nameStr.trim());
        
        if(gameOver){
            JOptionPane.showMessageDialog(this, "Game Over!!!");
            return;
        }

        if(gameChances[name] == 2){
            if(activePlayer == 1){
                currentButton.setIcon(new ImageIcon("static/cross.png"));
                gameChances[name] =activePlayer;
                activePlayer = 0;
            }else{
                currentButton.setIcon(new ImageIcon("static/zero.png"));
                gameChances[name] = activePlayer;
                activePlayer = 1;
            }

            // Finding Winner
            for(int []temp : wps){
                if(gameChances[temp[0]] == gameChances[temp[1]] && gameChances[temp[1]] == gameChances[temp[2]] && gameChances[temp[2]] != 2){
                    winner = gameChances[temp[0]];

                    gameOver = true;

                    JOptionPane.showMessageDialog(null, "Player " + winner + " has won the game");
                    int i = JOptionPane.showConfirmDialog(this, "Do you want to play again???");

                    if(i ==0){
                        this.setVisible(false);
                        new MyGame();
                    }else if(i == 1){
                        System.exit(name);
                    }else{

                    }
                    System.out.println(i);
                    break;
                }

                // Draw Condition //
                int c =0;
                for(int x:gameChances){
                    if(x == 2){
                        c++;
                        break;
                    }
                }
                if(c == 0 && gameOver == false){
                    JOptionPane.showMessageDialog(null, "Its A Draw ;) ");
                    
                    int i = JOptionPane.showConfirmDialog(this, "Play More??");
                    if(i==0){
                       this.setVisible(false);
                       new MyGame(); 
                    }else if(i==1){
                        System.exit(004);
                    }else{

                    }
                    gameOver = true;
                }
            }   
        }else{
            JOptionPane.showMessageDialog(this,"Postion already occupied");
        }
    }
}
