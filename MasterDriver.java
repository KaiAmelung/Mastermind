   import javax.swing.JFrame;
   public class MasterDriver
   {
      public static void main(String[] args)
      {
         JFrame frame = new JFrame("Unit4, Lab16: Mastermind");
         frame.setSize(600, 800);
         frame.setLocation(300, 0);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.getContentPane().add(new MasterPanel());
         frame.setVisible(true);
      }
   }