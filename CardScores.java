import java.util.ArrayList;

//import javax.smartcardio.Card;

public class CardScores {
   private int[][] playerCardValues;
   private int[][] playerCardHouses;
   private int[] comCardValues;
   private int[] comCardHouses;
   private int[][] playerPlusCCVals;
   private int[][] playerPlusCCHouses;
   private Cards deck;
   private CommunityCards comCards;
   private ArrayList<Players> playerInfoList = new ArrayList<>();

   CardScores(Players var1, ArrayList<Players> var2, Cards var3, CommunityCards var4) {
      this.playerInfoList.add(var1);
      this.playerInfoList.addAll(var2);
      this.deck = var3;
      this.comCards = var4;
   }

   public ArrayList<Integer> calculatePoints() {
      //new ArrayList();
      this.playerCardValues = new int[this.playerInfoList.size()][2];
      this.playerCardHouses = new int[this.playerInfoList.size()][2];
      this.valuePlayerCards();
      this.valueComCards();

      for(int var2 = 0; var2 < this.playerCardValues.length; ++var2) {
         this.orderCards(var2);
      }

      this.highCard();
      this.findPairs();
      this.findStraight_Flush();
      ArrayList<Integer> var1 = this.findWinner();
      return var1;
   }

   private ArrayList<Integer> findWinner() {
      int var1 = -1;
      int var2 = -1;
      int var3 = -1;
      boolean var4 = false;
      int[] var5 = new int[]{-1, -1, -1, -1, -1};
      //int[] var10000 = new int[]{0, 0};
      ArrayList<Integer> var7 = new ArrayList<>();

      int var8;
      for(var8 = 0; var8 < this.playerCardValues.length; ++var8) {
         if (((Players)this.playerInfoList.get(var8)).isNotFolded()) {
            int[] var6 = ((Players)this.playerInfoList.get(var8)).getPoints();
            if (var6[0] > var1 && var6[0] != var1) {
               var3 = var8;
               var1 = var6[0];
               var2 = var6[1];
               var4 = false;
               var5 = new int[]{-1, -1, -1, -1, -1};
            } else if (var6[0] == var1) {
               if (var2 < var6[1]) {
                  var3 = var8;
                  var1 = var6[0];
                  var2 = var6[1];
                  var4 = false;
                  var5 = new int[]{-1, -1, -1, -1, -1};
               } else if (var2 == var6[1]) {
                  var4 = true;
                  var5[var3] = var1;
                  var5[var8] = var1;
                  var3 = var8;
                  var1 = var6[0];
                  var2 = var6[1];
               }
            }
         }
      }

      if (var4) {
         System.out.println("There are multiple winners");

         for(var8 = 0; var8 < var5.length; ++var8) {
            if (var5[var8] >= 0) {
               if (var7.size() == 0) {
                  var7.add(var5[var8]);
                  var7.add(var8);
               } else {
                  var7.add(var8);
               }

               System.out.println(var5[var8] + "  " + (var8 + 1));
            }
         }
      } else {
         var7.add(var1);
         var7.add(var3);
      }

      return var7;
   }

   private void valuePlayerCards() {
      //new ArrayList();

      for(int var2 = 0; var2 < this.playerInfoList.size(); ++var2) {
         ArrayList<String> var1 = ((Players)this.playerInfoList.get(var2)).getCards();
         this.playerCardValues[var2] = this.deck.returnCardValue(var1);
         this.playerCardHouses[var2] = this.deck.returnCardHouses(var1);
      }

   }

   private void valueComCards() {
      //new ArrayList();
      ArrayList<String> var1 = this.comCards.getComCards();
      this.comCardValues = this.deck.returnCardValue(var1);
      this.comCardHouses = this.deck.returnCardHouses(var1);
      this.playerPlusCCVals = new int[this.playerCardValues.length][this.comCardValues.length + this.playerCardValues[0].length];
      this.playerPlusCCHouses = new int[this.playerCardValues.length][this.comCardValues.length + this.playerCardValues[0].length];
   }

   private void orderCards(int var1) {
      int[] var2 = new int[]{this.playerCardValues[var1][0], this.playerCardValues[var1][1], this.comCardValues[0], this.comCardValues[1], this.comCardValues[2], this.comCardValues[3], this.comCardValues[4]};
      int[] var3 = new int[]{this.playerCardHouses[var1][0], this.playerCardHouses[var1][1], this.comCardHouses[0], this.comCardHouses[1], this.comCardHouses[2], this.comCardHouses[3], this.comCardHouses[4]};
      this.playerPlusCCHouses[var1] = var3;
      this.playerPlusCCVals[var1] = var2;
      this.quickSort(var1, 0, var2.length - 1);
   }

   private void highCard() {
      int[] var1 = new int[this.playerCardValues.length];
      int var2 = this.playerCardValues[0][0];

      int var3;
      int var4;
      for(var3 = 0; var3 < this.playerCardValues.length; ++var3) {
         if (((Players)this.playerInfoList.get(var3)).isNotFolded()) {
            for(var4 = 0; var4 < this.playerCardValues[0].length; ++var4) {
               if ((this.playerCardValues[var3][var4] > var2 || this.playerCardValues[var3][var4] == 1) && var2 != 1) {
                  var2 = this.playerCardValues[var3][var4];
               }
            }
         }
      }

      for(var3 = 0; var3 < this.playerCardValues.length; ++var3) {
         if (((Players)this.playerInfoList.get(var3)).isNotFolded()) {
            for(var4 = 0; var4 < this.playerCardValues[0].length; ++var4) {
               if (this.playerCardValues[var3][var4] == var2 && var1[var3] != var2) {
                  var1[var3] = var2;
               }
            }
         }
      }

      for(var3 = 0; var3 < var1.length; ++var3) {
         if (((Players)this.playerInfoList.get(var3)).isNotFolded() && var1[var3] > 0) {
            ((Players)this.playerInfoList.get(var3)).addPoints(1, var2);
         }
      }

   }

   private void findPairs() {
      int[] var1 = new int[]{0, 0, 0, 0, 0};
      int[] var2 = new int[]{0, 0, 0, 0, 0};
      int[] var3 = new int[]{0, 0, 0, 0, 0};
      int var4 = 0;

      int var5;
      for(var5 = 0; var5 < this.playerPlusCCVals.length; ++var5) {
         if (((Players)this.playerInfoList.get(var5)).isNotFolded()) {
            int var10002;
            for(int var6 = 0; var6 < this.playerPlusCCVals[0].length - 1; ++var6) {
               if (var1[var5] + var3[var5] < 5 && this.playerPlusCCVals[var5][var6] == this.playerPlusCCVals[var5][var6 + 1] && var4 < 3) {
                  if (var6 > 0 && this.playerPlusCCVals[var5][var6 - 1] == this.playerPlusCCVals[var5][var6]) {
                     var10002 = var3[var5]++;
                  }

                  if (this.playerPlusCCVals[var5][var6] > var2[var5]) {
                     var2[var5] = this.playerPlusCCVals[var5][var6];
                  }

                  var10002 = var1[var5]++;
                  ++var4;
               }
            }

            if (var4 > 3) {
               var10002 = var1[var5]--;
            }

            var4 = 0;
         }
      }

      for(var5 = 0; var5 < this.playerInfoList.size(); ++var5) {
         if (((Players)this.playerInfoList.get(var5)).isNotFolded() && var1[var5] > 0) {
            ((Players)this.playerInfoList.get(var5)).addPoints(var1[var5] + 1 + var3[var5], var2[var5]);
         }
      }

   }

   private void findStraight_Flush() {
      int var1 = 0;
      int[] var2 = new int[]{0, 0, 0, 0};
      int[] var3 = new int[]{0, 0, 0, 0};
      int[][] var4 = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
      boolean var5 = false;

      int var6;
      int var7;
      for(var6 = 0; var6 < this.playerPlusCCVals.length; ++var6) {
         if (((Players)this.playerInfoList.get(var6)).isNotFolded()) {
            ++var3[this.playerPlusCCHouses[var6][0] - 1];
            ++var2[this.playerPlusCCHouses[var6][0] - 1];

            for(var7 = 1; var7 < this.playerPlusCCVals[0].length; ++var7) {
               if (this.playerPlusCCVals[var6][var7 - 1] + 1 == this.playerPlusCCVals[var6][var7]) {
                  ++var1;
               } else if (this.playerPlusCCVals[var6][var7 - 1] != this.playerPlusCCVals[var6][var7]) {
                  var1 = 0;
               }

               ++var2[this.playerPlusCCHouses[var6][var7] - 1];
               if (var1 == 3 && this.playerPlusCCVals[var6][var7 - 1] == 4 && this.playerPlusCCVals[var6][this.playerPlusCCVals[0].length - 1] == 14) {
                  ++var2[this.playerPlusCCHouses[var6][this.playerPlusCCHouses[0].length - 1] - 1];
               }

               if (var2[this.playerPlusCCHouses[var6][var7] - 1] >= 5) {
                  var5 = true;
               }

               if (var1 >= 4) {
                  if (var5) {
                     var4[var6][0] = this.playerPlusCCVals[var6][var7];
                  } else {
                     var4[var6][2] = this.playerPlusCCVals[var6][var7];
                  }
               } else if (var1 == 3 && this.playerPlusCCVals[var6][var7 - 1] == 4 && this.playerPlusCCVals[var6][this.playerPlusCCVals[0].length - 1] == 14) {
                  if (var5) {
                     var4[var6][0] = this.playerPlusCCVals[var6][var7];
                  } else {
                     var4[var6][2] = this.playerPlusCCVals[var6][var7];
                  }
               }

               ++var3[this.playerPlusCCHouses[var6][var7] - 1];
               if (var3[this.playerPlusCCHouses[var6][var7] - 1] >= 5) {
                  if (this.playerPlusCCVals[var6][0] == 1 && this.playerCardHouses[var6][0] == this.playerPlusCCHouses[var6][var7]) {
                     var4[var6][1] = this.playerPlusCCVals[var6][0];
                  } else {
                     var4[var6][1] = this.playerPlusCCVals[var6][var7];
                  }
               }
            }

            var1 = 0;
            var3 = new int[]{0, 0, 0, 0, 0};
            var2 = new int[]{0, 0, 0, 0};
            var5 = false;
         }
      }

      for(var6 = 0; var6 < this.playerInfoList.size(); ++var6) {
         if (((Players)this.playerInfoList.get(var6)).isNotFolded()) {
            for(var7 = 0; var7 < var4[0].length; ++var7) {
               if (var4[var6][var7] > 0) {
                  ((Players)this.playerInfoList.get(var6)).addPoints((var7 + 1) * 100, var4[var6][var7]);
                  break;
               }
            }
         }
      }

   }

   public void printInfo() {
      for(int var1 = 0; var1 < 5; ++var1) {
         System.out.printf("Player: %d Cards values\n", var1 + 1);

         for(int var2 = 0; var2 < 2; ++var2) {
            System.out.printf("   %d %d", this.playerCardValues[var1][var2], this.playerCardHouses[var1][var2]);
         }

         System.out.println();
      }

   }

   private void quickSort(int var1, int var2, int var3) {
      if (var2 < var3) {
         int var4 = this.playerPlusCCVals[var1][var3];
         int var5 = var2;

         for(int var6 = var3; var5 < var6; this.playerPlusCCHouses[var1] = this.swap(this.playerPlusCCHouses[var1], var5, var6)) {
            while(this.playerPlusCCVals[var1][var5] <= var4 && var5 < var6) {
               ++var5;
            }

            while(this.playerPlusCCVals[var1][var6] >= var4 && var5 < var6) {
               --var6;
            }

            this.playerPlusCCVals[var1] = this.swap(this.playerPlusCCVals[var1], var5, var6);
         }

         this.playerPlusCCVals[var1] = this.swap(this.playerPlusCCVals[var1], var5, var3);
         this.playerPlusCCHouses[var1] = this.swap(this.playerPlusCCHouses[var1], var5, var3);
         this.quickSort(var1, var2, var5 - 1);
         this.quickSort(var1, var5 + 1, var3);
      }
   }

   private int[] swap(int[] var1, int var2, int var3) {
      int var4 = var1[var2];
      var1[var2] = var1[var3];
      var1[var3] = var4;
      return var1;
   }

   public void removePlayer(int var1) {
      this.playerInfoList.remove(var1);
   }
}

