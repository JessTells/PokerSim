import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Cards {
   String[] cardInit = new String[]{"Ace of Spades", "2 of Spades", "3 of Spades", "4 of Spades", "5 of Spades", "6 of Spades", "7 of Spades", "8 of Spades", "9 of Spades", "10 of Spades", "Jack of Spades", "Queen of Spades", "King of Spades", "Ace of Hearts", "2 of Hearts", "3 of Hearts", "4 of Hearts", "5 of Hearts", "6 of Hearts", "7 of Hearts", "8 of Hearts", "9 of Hearts", "10 of Hearts", "Jack of Hearts", "Queen of Hearts", "King of Hearts", "Ace of Diamonds", "2 of Diamonds", "3 of Diamonds", "4 of Diamonds", "5 of Diamonds", "6 of Diamonds", "7 of Diamonds", "8 of Diamonds", "9 of Diamonds", "10 of Diamonds", "Jack of Diamonds", "Queen of Diamonds", "King of Diamonds", "Ace of Clubs", "2 of Clubs", "3 of Clubs", "4 of Clubs", "5 of Clubs", "6 of Clubs", "7 of Clubs", "8 of Clubs", "9 of Clubs", "10 of Clubs", "Jack of Clubs", "Queen of Clubs", "King of Clubs"};
   ArrayList<String> listCards;
   ArrayList<String> listCardClone;

   public Cards() {
      this.listCards = new ArrayList<String>(Arrays.asList(this.cardInit));
      this.listCardClone = new ArrayList<String>(Arrays.asList(this.cardInit));
   }

   public ArrayList<String> dealCards(int var1) {
      ArrayList<String> var2 = new ArrayList<>();
      //boolean var3 = true;

      for(int var4 = 0; var4 < var1; ++var4) {
         int var5 = this.randomNum(0, this.listCards.size());
         var2.add((String)this.listCards.get(var5));
         this.listCards.remove(var5);
      }

      return var2;
   }

   public String forceCards(String var1) {
      this.listCards.remove(var1);
      return var1;
   }

   public void clearCards() {
      this.listCards.clear();
      this.listCards.addAll(this.listCardClone);
   }

   private int randomNum(int var1, int var2) {
      return (int)(Math.random() * (double)(var2 - var1)) + var1;
   }

   public int[] returnCardValue(ArrayList<String> var1) {
      String var2 = "";
      int[] var3 = new int[var1.size()];

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         var2 = (String)var1.get(var4);
         switch (var2.substring(0, var2.indexOf(" "))) {
            case "Ace":
               var3[var4] = 14;
               break;
            case "King":
               var3[var4] = 13;
               break;
            case "Queen":
               var3[var4] = 12;
               break;
            case "Jack":
               var3[var4] = 11;
               break;
            default:
               var3[var4] = Integer.parseInt(var2.substring(0, var2.indexOf(" ")));
         }
      }

      return var3;
   }

   public int[] returnCardHouses(ArrayList<String> var1) {
      String var2 = "";
      int[] var3 = new int[var1.size()];

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         var2 = (String)var1.get(var4);
         switch (var2.substring(var2.lastIndexOf(" ") + 1)) {
            case "Spades":
               var3[var4] = 1;
               break;
            case "Hearts":
               var3[var4] = 2;
               break;
            case "Diamonds":
               var3[var4] = 3;
               break;
            case "Clubs":
               var3[var4] = 4;
               break;
            default:
               var3[var4] = -1;
               PrintStream var10000 = System.out;
               String var10001 = var2.substring(var2.lastIndexOf(" ") + 1);
               var10000.println("" + var10001);
         }
      }

      return var3;
   }
}
