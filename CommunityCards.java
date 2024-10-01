import java.util.ArrayList;

public class CommunityCards {
   private Cards deck;
   private int comCardAmt;
   private ArrayList<String> comCards = new ArrayList<>();

   CommunityCards(Cards var1) {
      this.deck = var1;
      this.comCardAmt = 0;
   }

   public void flop() {
      this.comCards = this.deck.dealCards(3);
      this.comCardAmt += 3;
   }

   public void addCard() {
      this.comCards.addAll(this.deck.dealCards(1));
      ++this.comCardAmt;
   }

   public void addCard(String var1) {
      this.comCards.add(this.deck.forceCards(var1));
      ++this.comCardAmt;
   }

   public void clearCards() {
      this.comCards.clear();
      this.comCardAmt = 0;
   }

   public int getHandAmt() {
      return this.comCardAmt;
   }

   public ArrayList<String> getComCards() {
      return this.comCards;
   }

   public void printInfo() {
      System.out.println("Community Hand:");

      for(int var1 = 0; var1 < this.comCards.size(); ++var1) {
         System.out.printf("%s\n", this.comCards.get(var1));
      }

      System.out.println();
   }
}
