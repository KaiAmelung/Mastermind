import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MasterPanel extends JPanel
{
   private JButton[][] board;
   private Color[][] colors;
   private String[][] color;
   private JButton[] answer;
   private Color[] theanswer;
   private JPanel bord;
   private JPanel answers;
   private JPanel south;
   private JLabel label;
   private JButton reset;
   private int currentRow;
   public MasterPanel()
   {
      //Color[] dacolor = {Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.RED, Color.BLUE, Color.CYAN};
      setLayout(new BorderLayout());
      label = new JLabel("");
      label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 12));
      label.setForeground(Color.BLACK);
      reset = new JButton("Reset");
      reset.setEnabled(false);
      reset.addActionListener(new Reset());
      south = new JPanel();
      south.setLayout(new FlowLayout());
      south.add(label);
      south.add(reset);
      add(south, BorderLayout.SOUTH);
      bord = new JPanel();
      bord.setLayout(new GridLayout(7, 4));
      answers = new JPanel();
      answers.setLayout(new GridLayout(7, 1));
      add(answers, BorderLayout.WEST);
      add(bord, BorderLayout.CENTER);
      board = new JButton[7][4];
      colors = new Color[7][4];
      for(int x = 6; x >= 0; x--)
         for(int y = 3; y >= 0; y--)
         {
            board[x][y] = new JButton("");
      		board[x][y].setBackground(Color.GRAY);
            board[x][y].setBorderPainted(false);
            board[x][y].setOpaque(true);
            colors[x][y] = Color.GRAY;
            board[x][y].setFocusPainted(false);
            board[x][y].addActionListener(new Listener(x, y));
            if(x!=0)
            {
               board[x][y].setEnabled(false);
            }
            bord.add(board[x][y]);
         }
      answer = new JButton[7];
      for(int a = 6; a >= 0; a--)
      {
         answer[a] = new JButton("?");
         answer[a].setBackground(Color.BLACK);
         answer[a].setForeground(Color.WHITE);
         answer[a].addActionListener(new Listener1(a));
         if(a!=0)
         {
            answer[a].setEnabled(false);
         }
         answers.add(answer[a]);
      }
      theanswer = new Color[4];
      for(int x = 0; x < 4; x++)
      {
         theanswer[x]=randomize();
      }
   }
   public Color toggle(Color c)
   {
      if(c.equals(Color.GREEN))
      {
         return Color.MAGENTA;
      }
      if(c.equals(Color.MAGENTA))
      {
         return Color.YELLOW;
      }
      if(c.equals(Color.YELLOW))
      {
         return Color.RED;
      }
      if(c.equals(Color.RED))
      {
         return Color.BLUE;
      }
      if(c.equals(Color.BLUE))
      {
         return Color.CYAN;
      }
      if(c.equals(Color.CYAN))
      {
         return Color.ORANGE;
      }
      return Color.GREEN;
   }
   public String toggler(Color c)
   {
      if(c.equals(Color.GREEN))
      {
         return "Green";
      }
      if(c.equals(Color.MAGENTA))
      {
         return "Magenta";
      }
      if(c.equals(Color.YELLOW))
      {
         return "Yellow";
      }
      if(c.equals(Color.RED))
      {
         return "Red";
      }
      if(c.equals(Color.BLUE))
      {
         return "Blue";
      }
      if(c.equals(Color.CYAN))
      {
         return "Cyan";
      }
      return "Orange";
   }
   public void freeze()
   {
      for(int x = 6; x >= 0; x--)
      {
         for(int y = 3; y >= 0; y--)
         {
            board[x][y].setEnabled(false);
         }
      }
   }
    private class Reset implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         reset.setEnabled(false);
         label.setText("");
         for(int x = 6; x >= 0; x--)
         {
            answer[x].setText("?");
            answer[x].setEnabled(true);
            if(x!=0)
            {
               answer[x].setEnabled(false);
            }
            for(int y = 3; y >= 0; y--)
            {
               colors[x][y] = Color.GRAY;
               board[x][y].setBackground(colors[x][y]);
               board[x][y].setEnabled(true);
               if(x!=0)
               {
                  board[x][y].setEnabled(false);
               }
            }
         }
         for(int x = 0; x < 4; x++)
         {
            theanswer[x]=randomize();
         }
      }
   }
   private class Listener implements ActionListener
   {
      private int myX, myY;
      public Listener(int x, int y)
      {
         myX = x;
         myY = y;
      }
      public void actionPerformed(ActionEvent e)
      {
         colors[myX][myY]=toggle(colors[myX][myY]);
         board[myX][myY].setBackground(colors[myX][myY]);
      }
   }
   private class Listener1 implements ActionListener
   {
      private int myX;
      public Listener1(int a)
      {
         myX = a;
      }
      public void actionPerformed(ActionEvent e)
      {
         if(!colors[myX][0].equals(Color.GRAY)&&!colors[myX][1].equals(Color.GRAY)&&!colors[myX][2].equals(Color.GRAY)&&!colors[myX][3].equals(Color.GRAY))
         {
            int b = 0;
            int w = 0;
            boolean[] used = new boolean[4];
            for(int x = 0; x<4; x++)
            {
               used[x]=false;
            }
            for(int x = 0; x < 4; x++)
            {
               if(colors[myX][x].equals(theanswer[x]))
               {
                  b++;
               }
               for(int a = 0; a < 4; a++)
               {
                  if(colors[myX][x].equals(theanswer[a])&&!used[a])
                  {
                     used[a]=true;
                     w++;
                  }
               }
            }
            w = w - b;
            answer[myX].setText(b + " blacks, " + w + " white.");
            if(b == 4)
            {
               label.setText("Winner!");
               answer[myX].setEnabled(false);
               freeze();
               reset.setEnabled(true);
            }
            else if(myX==6)
            {
               label.setText("Loser! The correct combo was: " + toggler(theanswer[0]) + " " + toggler(theanswer[1]) + " " + toggler(theanswer[2]) + " " + toggler(theanswer[3]));
               answer[myX].setEnabled(false);
               freeze();
               reset.setEnabled(true);
            }
            else
            {
               for(int x = 0; x < 4; x++)
               {
                  board[myX][x].setEnabled(false);
                  board[myX+1][x].setEnabled(true);
                  answer[myX].setEnabled(false);
                  answer[myX+1].setEnabled(true);
               }
            }
         } 
      }
   }
   public Color randomize()
   {
      double rand = Math.random();
      if(rand<1.0/7.0)
      {
         return Color.GREEN;
      }
      else if(rand<2.0/7.0)
      {
         return Color.MAGENTA;
      }
      else if(rand<3.0/7.0)
      {
         return Color.RED;
      }
      else if(rand<4.0/7.0)
      {
         return Color.YELLOW;
      }
      else if(rand<5.0/7.0)
      {
         return Color.BLUE;
      }
      else if(rand<6.0/7.0)
      {
         return Color.CYAN;
      }
      else
      {
         return Color.ORANGE;
      }
   }
}