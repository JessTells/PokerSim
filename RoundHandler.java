import java.util.ArrayList;
import java.util.Scanner;

public class RoundHandler {
   private String userChoice;
   private String playerWhoRaised = "none";
   private int foldAmt = 0;
   private int currPlayerIndex = 0;
   private double betAmt = 0.0;
   private double callAmt = 0.0;
   private double totalPot;
   private boolean needCall = false;
   private Players userPlayer;
   private CardScores scores;
   private ArrayList<Players> cpuInfoList = new ArrayList<>();
   private ArrayList<Players> currPlayerTurn = new ArrayList<>();
   private Scanner scnr;

   RoundHandler(Players var1, ArrayList<Players> var2, Scanner var3, CardScores var4) {
      this.userPlayer = var1;
      this.cpuInfoList = var2;
      this.scores = var4;
      this.scnr = var3;
      this.currPlayerTurn.add(this.userPlayer);
      this.currPlayerTurn.addAll(this.cpuInfoList);
   }

   public void roundOrder() {
      int var1 = 0;

      int var2;
      for(var2 = this.currPlayerIndex; var2 < this.currPlayerTurn.size(); ++var2) {
         if (((Players)this.currPlayerTurn.get(var2)).isNotFolded() && !this.findFolded()) {
            if (((Players)this.currPlayerTurn.get(var2)).getName().equals(this.userPlayer.getName())) {
               this.userPlayerTurn();
            } else {
               this.cpuTurn((Players)this.currPlayerTurn.get(var2));
            }
         }

         ++var1;
      }

      for(var2 = 0; var2 < this.currPlayerTurn.size() - var1; ++var2) {
         if (!this.findFolded() && ((Players)this.currPlayerTurn.get(var2)).isNotFolded()) {
            if (((Players)this.currPlayerTurn.get(var2)).getName().equals(this.userPlayer.getName())) {
               this.userPlayerTurn();
            } else {
               this.cpuTurn((Players)this.currPlayerTurn.get(var2));
            }
         }
      }

   }

   public void userPlayerTurn() {
      if (this.playerWhoRaised.equals(this.userPlayer.getName())) {
         this.callAmt = 0.0;
         this.needCall = false;
      }

      if (this.needCall) {
         System.out.println("Please type an option\n Call\n Raise\n Fold\n");
      } else {
         System.out.println("Please type an option\n Check\n Raise\n Fold\n");
      }

      System.out.print("Enter choice here: ");
      this.userChoice = this.scnr.next();
      this.playerChoice(this.userPlayer.getName());
   }

   public void cpuTurn(Players var1) {
      String var3 = var1.getName();
      if (this.playerWhoRaised.equals(var1.getName())) {
         this.callAmt = 0.0;
         this.needCall = false;
      }

      int var2;
      if (this.needCall) {
         var2 = this.randomNum(1, 3);
      } else {
         var2 = this.randomNum(0, 2);
      }

      switch (var2) {
         case 0:
            this.check(var3);
            break;
         case 1:
            this.betAmt = (double)this.randomNum((int)this.callAmt, (int)this.callAmt + 20);
            var1.subBalance(this.betAmt);
            this.raise(var3);
            break;
         case 2:
            this.fold(var3);
            var1.setIsNotFolded(false);
            break;
         case 3:
            var1.subBalance(this.betAmt);
            this.call(var3);
            break;
         default:
            this.check(var3);
      }

   }

   public void playerChoice(String var1) {
      if (this.userChoice.equalsIgnoreCase("check")) {
         this.check(var1);
      } else if (this.userChoice.equalsIgnoreCase("raise")) {
         this.raise(var1);
      } else if (this.userChoice.equalsIgnoreCase("fold")) {
         this.fold(var1);
         if (var1.equals(this.userPlayer.getName())) {
            this.userPlayer.setIsNotFolded(false);
         }
      } else if (this.userChoice.equalsIgnoreCase("call")) {
         this.call(var1);
      } else {
         System.out.println("Invalid choice, auto fold\n");
         this.fold(var1);
      }

   }

   public void check(String var1) {
      System.out.printf("%s has checked\n", var1);
   }

   public void raise(String var1) {
      if (var1.equals(this.userPlayer.getName())) {
         System.out.print("Enter a betting amount: ");
         this.betAmt = this.scnr.nextDouble();
         System.out.println();
         if (this.betAmt <= this.callAmt) {
            System.out.println("Please enter a higher number");
            this.raise(var1);
         }

         this.userPlayer.subBalance(this.betAmt);
      }

      System.out.printf("%s has bet $%.2f\n\n", var1, this.betAmt);
      this.needCall = true;
      this.callAmt = this.betAmt;
      this.playerWhoRaised = var1;
      this.totalPot += this.betAmt;
   }

   public void call(String var1) {
      if (var1.equals(this.userPlayer.getName())) {
         this.userPlayer.subBalance(this.betAmt);
      }

      System.out.printf("%s has called $%.2f\n\n", var1, this.betAmt);
      this.totalPot += this.betAmt;
   }

   public void fold(String var1) {
      System.out.printf("%s has folded\n\n", var1);
      ++this.foldAmt;
   }

   public int getFoldAmt() {
      return this.foldAmt;
   }

   public boolean getNeedCall() {
      return this.needCall;
   }

   public void beginBet() {
      if (this.currPlayerIndex == 0) {
         this.betAmt = 2.0;
         ((Players)this.currPlayerTurn.get(this.currPlayerTurn.size() - 1)).subBalance(this.betAmt);
         this.raise(((Players)this.currPlayerTurn.get(this.currPlayerTurn.size() - 1)).getName());
      } else if (this.currPlayerIndex == 1) {
         this.betAmt = 2.0;
         System.out.printf("%s has bet $%.2f\n\n", ((Players)this.currPlayerTurn.get(0)).getName(), this.betAmt);
         this.needCall = true;
         this.callAmt = this.betAmt;
         this.playerWhoRaised = ((Players)this.currPlayerTurn.get(0)).getName();
         this.totalPot += this.betAmt;
      } else if (this.currPlayerIndex > 1) {
         this.betAmt = 2.0;
         ((Players)this.currPlayerTurn.get(this.currPlayerIndex)).subBalance(this.betAmt);
         this.raise(((Players)this.currPlayerTurn.get(this.currPlayerIndex)).getName());
      }

   }

   public boolean calcWinner_fold() {
      String var1 = "";
      boolean var2 = false;
      var2 = this.findFolded();
      if (var2) {
         for(int var3 = 0; var3 < this.currPlayerTurn.size(); ++var3) {
            if (((Players)this.currPlayerTurn.get(var3)).isNotFolded()) {
               var1 = ((Players)this.currPlayerTurn.get(var3)).getName();
               ((Players)this.currPlayerTurn.get(var3)).addBalance(this.totalPot);
            }
         }

         System.out.printf("%s is the winner with a pot of %.2f\n\n", var1, this.totalPot);
      }

      return var2;
   }

   public void calcWinner_scores() {
      //new ArrayList<>();
      ArrayList<Integer> var1 = this.scores.calculatePoints();
      String var2 = this.findWinCat((Integer)var1.get(0));
      if (var1.size() > 2) {
         for(int var3 = 1; var3 < var1.size() - 1; ++var3) {
            System.out.printf("%s, ", ((Players)this.currPlayerTurn.get((Integer)var1.get(var3))).getName());
            ((Players)this.currPlayerTurn.get(var3)).addBalance(this.totalPot / (double)var1.size());
         }

         System.out.printf("and %s have won an equal \"%s\" and split a pot of $%.2f ($%.2f each) \n\n ", ((Players)this.currPlayerTurn.get((Integer)var1.get(var1.size() - 1))).getName(), var2, this.totalPot, this.totalPot / (double)var1.size());
         ((Players)this.currPlayerTurn.get(var1.size() - 1)).addBalance(this.totalPot / (double)var1.size());
      } else {
         System.out.printf("%s has won %s with a pot of $%.2f\n\n ", ((Players)this.currPlayerTurn.get((Integer)var1.get(var1.size() - 1))).getName(), var2, this.totalPot, this.totalPot / (double)var1.size());
         ((Players)this.currPlayerTurn.get(0)).addBalance(Math.floor(this.totalPot));
      }

   }

   public void resetPlayers() {
      this.betAmt = 0.0;
      this.callAmt = 0.0;
      this.totalPot = 0.0;
      this.foldAmt = 0;
      this.playerWhoRaised = "none";
      this.needCall = false;
      if (this.currPlayerIndex < this.currPlayerTurn.size() - 1) {
         ++this.currPlayerIndex;
      } else {
         this.currPlayerIndex = 0;
      }

      for(int var1 = 0; var1 < this.currPlayerTurn.size(); ++var1) {
         ((Players)this.currPlayerTurn.get(var1)).resetPlayer();
      }

   }

   private boolean findFolded() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.currPlayerTurn.size(); ++var2) {
         if (((Players)this.currPlayerTurn.get(var2)).isNotFolded()) {
            ++var1;
         }
      }

      if (var1 == 1) {
         return true;
      } else {
         return false;
      }
   }

   private String findWinCat(int var1) {
      switch (var1) {
         case 1:
            return "high card";
         case 2:
            return "one pair";
         case 3:
            return "two pair";
         case 4:
            return "three of a kind";
         case 5:
            return "straight";
         case 6:
            return "flush";
         case 7:
            return "full house";
         case 8:
            return "Four of a Kind!";
         case 9:
            return "STRAIGHT FLUSH!!";
         case 10:
            return "ROYAL FLUSH!!!";
         default:
            return "something fucked up";
      }
   }

   private int randomNum(int var1, int var2) {
      return (int)(Math.random() * (double)(var2 - var1)) + var1;
   }

   public int removePlayer() {
      for(int var1 = 0; var1 < this.currPlayerTurn.size(); ++var1) {
         if (((Players)this.currPlayerTurn.get(var1)).getBal() <= 0.0) {
            this.currPlayerTurn.remove(var1);
            this.scores.removePlayer(var1);
            return var1;
         }
      }

      return -1;
   }
}
