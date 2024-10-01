import java.util.ArrayList;

public class Players {
   private String playerName;
   private double playerBalance = 1000.0;
   private Cards deck;
   private boolean isNotFolded;
   private int[] playerPoints;
   private ArrayList<String> playerCards;

   Players(String var1, Cards var2) {
      this.playerName = var1;
      this.deck = var2;
      this.isNotFolded = true;
      this.playerCards = new ArrayList<String>();
      this.playerPoints = new int[]{0, 0};
   }

   public void dealCards() {
      this.playerCards = this.deck.dealCards(2);
   }

   public void forceCards(String var1) {
      this.playerCards.add(this.deck.forceCards(var1));
   }

   public void resetPlayer() {
      this.playerCards.clear();
      this.isNotFolded = true;
      this.playerPoints[0] = 0;
      this.playerPoints[1] = 0;
   }

   public void setIsNotFolded(boolean var1) {
      this.isNotFolded = var1;
   }

   public void addBalance(double var1) {
      this.playerBalance += var1;
   }

   public void subBalance(double var1) {
      this.playerBalance -= var1;
   }

   public void addPoints(int var1, int var2) {
      if (this.playerPoints[0] < var1) {
         switch (var1) {
            case 5:
               this.playerPoints[0] = 7;
               break;
            case 6:
               this.playerPoints[0] = 8;
               break;
            case 7:
               this.playerPoints[0] = 8;
               break;
            case 100:
               if (var2 == 14) {
                  this.playerPoints[0] = 10;
               } else {
                  this.playerPoints[0] = 9;
               }
               break;
            case 200:
               if (this.playerPoints[0] <= 6) {
                  this.playerPoints[0] = 6;
               }
               break;
            case 300:
               this.playerPoints[0] = 5;
               break;
            default:
               this.playerPoints[0] = var1;
         }
      }

      this.playerPoints[1] = var2;
   }

   public String getName() {
      return this.playerName;
   }

   public double getBal() {
      return this.playerBalance;
   }

   public int[] getPoints() {
      int[] var1 = new int[]{0, 0};
      return this.isNotFolded ? this.playerPoints : var1;
   }

   public boolean isNotFolded() {
      return this.isNotFolded;
   }

   public ArrayList<String> getCards() {
      return this.playerCards;
   }

   public void printInfo() {
      System.out.println(this.playerName);
      System.out.println("Balance: $" + this.playerBalance);
      System.out.println(" Player Cards:");

      for(int var1 = 0; var1 < this.playerCards.size(); ++var1) {
         System.out.printf("  %s\n", this.playerCards.get(var1));
      }

      System.out.println();
   }
}
