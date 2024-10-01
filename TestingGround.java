import java.util.ArrayList;
import java.util.Scanner;

public class TestingGround {
   public TestingGround() {
   }

   public static void main(String[] var0) {
      Scanner var1 = new Scanner(System.in);
      boolean var2 = false;
      Cards var4 = new Cards();
      CommunityCards var5 = new CommunityCards(var4);
      Players var7 = new Players(String.format("Player: 1"), var4);
      ArrayList<Players> var9 = new ArrayList<>();

      for(int var10 = 0; var10 < 4; ++var10) {
         Players var8 = new Players(String.format("Player: %d", var10 + 2), var4);
         var9.add(var8);
      }

      CardScores var3 = new CardScores(var7, var9, var4, var5);
      RoundHandler var6 = new RoundHandler(var7, var9, var1, var3);
      var7.forceCards("3 of Clubs");
      var7.forceCards("6 of Hearts");
      ((Players)var9.get(0)).forceCards("6 of Clubs");
      ((Players)var9.get(0)).forceCards("3 of Hearts");
      ((Players)var9.get(1)).forceCards("Queen of Diamonds");
      ((Players)var9.get(1)).forceCards("4 of Diamonds");
      ((Players)var9.get(2)).forceCards("Ace of Clubs");
      ((Players)var9.get(2)).forceCards("Ace of Hearts");
      ((Players)var9.get(3)).forceCards("Ace of Diamonds");
      ((Players)var9.get(3)).forceCards("6 of Spades");
      var5.addCard("3 of Diamonds");
      var5.addCard("7 of Hearts");
      var5.addCard("2 of Hearts");
      var5.addCard("King of Spades");
      var5.addCard("9 of Spades");
      var5.printInfo();
      var7.printInfo();
      ((Players)var9.get(0)).printInfo();
      ((Players)var9.get(1)).printInfo();
      ((Players)var9.get(2)).printInfo();
      ((Players)var9.get(3)).printInfo();
      var6.calcWinner_scores();
      ((Players)var9.get(0)).addBalance(-100000.0);
      int var11 = var6.removePlayer();
      if (var11 != -1) {
         if (var11 == 0) {
            System.out.println("You have lost");
            var1.close();
            System.exit(1);
         } else {
            System.out.printf("Player: %s has lost\n", ((Players)var9.get(var11 - 1)).getName());
            var9.remove(var11 - 1);
            if (var9.size() == 0) {
               System.out.println("YOU ARE A WINNER!!! get a life");
               var1.close();
               System.exit(1);
            }
         }
      }

      var6.resetPlayers();
      var5.clearCards();
      var4.clearCards();
      var7.forceCards("3 of Clubs");
      var7.forceCards("6 of Hearts");
      ((Players)var9.get(0)).forceCards("6 of Clubs");
      ((Players)var9.get(0)).forceCards("3 of Hearts");
      ((Players)var9.get(1)).forceCards("Queen of Diamonds");
      ((Players)var9.get(1)).forceCards("4 of Diamonds");
      ((Players)var9.get(2)).forceCards("Ace of Clubs");
      ((Players)var9.get(2)).forceCards("Ace of Hearts");
      var5.addCard("3 of Diamonds");
      var5.addCard("7 of Hearts");
      var5.addCard("2 of Hearts");
      var5.addCard("King of Spades");
      var5.addCard("9 of Spades");
      var5.printInfo();
      var7.printInfo();
      ((Players)var9.get(0)).printInfo();
      ((Players)var9.get(1)).printInfo();
      ((Players)var9.get(2)).printInfo();
      var6.calcWinner_scores();
      var1.close();
   }
}
