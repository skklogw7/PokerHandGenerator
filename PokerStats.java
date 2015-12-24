////////////////////////////////////////////////////////////////////////////////////////////////////
// Name: Kurt Schuepfer
// Date: 11/18/15

/** This program plays the game of poker and prints some outcome statistics */
////////////////////////////////////////////////////////////////////////////////////////////////////

import java.util.Scanner;

//This class plays a user-specified number of poker hands and then summarizes the results.
public class PokerStats{
   
   
   //Asks the user how many hands to play and then calls a method to play them
   public static void main (String[] args) { 
      
      System.out.print("How many poker hands should I deal?");
      Scanner keyboard = new Scanner (System.in);
      int numHands = keyboard.nextInt();
      
      while (numHands < 1) {
         System.out.print("Enter a positive number please: ");
         numHands = keyboard.nextInt();
      }
      
      playAndShowStats(numHands);
      
   }
   
   
   //Prints a 5-card hand
   public static void printHand(Card[] cards) {
      
      System.out.print(cards[0] + " ");
      System.out.print(cards[1] + " ");
      System.out.print(cards[2] + " ");
      System.out.print(cards[3] + " ");
      System.out.println(cards[4] + " ");
   }
   
   
   //Returns whether or not the hand was a flush
   public static boolean hasFlush(Card[] cards) {
      
      int difSuitCount = 0;
      
      for (int i = 0; i < cards.length-1; i++) {
         if (cards[i].getSuit() != cards[i+1].getSuit()) difSuitCount++;
      }
      
      return (difSuitCount == 0);  
      
   }
   
   
   //Returns whether or not the hand was a straight
   public static boolean hasStraight(Card[] cards) {
      int difValueCount = 0;
      boolean hasStraight = false;
      
      if(cards[0].getValue() != 1) {
         for (int i = 0; i < cards.length-1; i++) {
            if (cards[i].getValue() != cards[i+1].getValue() - 1) difValueCount++;  
         }
      }
      
      else if (cards[0].getValue() == 1) {
         for (int i = 1; i < cards.length-1; i++) {
            if (cards[i].getValue() != cards[i+1].getValue() - 1) difValueCount++; 
            
         }
      }
      
      if (cards[0].getValue() == 1) {
         hasStraight = (difValueCount == 0 && (cards[1].getValue() == 2 || 
                                               cards[1].getValue() == 10));
      }
      else hasStraight = (difValueCount == 0);
      
      
      return hasStraight;
   }
   
   
   //Returns whether or not the hand was a straight flush
   public static boolean hasStraightFlush(Card[] cards) {
      
      return (hasStraight(cards) && hasFlush(cards));
   }
   
   
   //Returns whether or not the hand was a royal flush
   public static boolean hasRoyalFlush(Card[] cards) {
      
      return (hasStraight(cards) && hasFlush(cards) && 
              cards[0].getValue() == 1 && cards[4].getValue() == 13);
   }
   
   
   //Returns whether or not the hand was a four of a kind
   public static boolean has4OfAKind(Card[] cards) {
      
      int fourOfAKindCount = 0;
      
      for (int i = 0; i < cards.length-3; i++) {
         if (cards[i].getValue() == cards[i+1].getValue() && 
             cards[i].getValue() == cards[i+2].getValue() &&
             cards[i].getValue() == cards[i+3].getValue()) fourOfAKindCount++;
      }
      
      return (fourOfAKindCount > 0);
   }
   
   
   //Returns whether or not the hand was a three of a kind
   public static boolean has3OfAKind(Card[] cards) {
      
      int threeOfAKindCount = 0;
      
      for (int i = 0; i < cards.length-2; i++) {
         if (cards[i].getValue() == cards[i+1].getValue() && 
             cards[i].getValue() == cards[i+2].getValue()) threeOfAKindCount++;
         
      }
      
      return (threeOfAKindCount > 0 && !has4OfAKind(cards));
   }
   
   
   //Returns whether or not the hand was a pair
   public static boolean hasPair(Card[] cards) {
      
      boolean hasPair = false;
      
      if ((cards[0].getValue() == cards[1].getValue() && cards[1].getValue() != cards[2].getValue())
             && cards[1].getValue() != cards[3].getValue() && cards[1].getValue() 
             != cards[4].getValue()) hasPair = true;
      else if ((cards[1].getValue() == cards[2].getValue() && cards[2].getValue() 
                   != cards[3].getValue()) && cards[2].getValue() 
                  != cards[4].getValue()) hasPair = true;
      else if (cards[2].getValue() == cards[3].getValue() && cards[3].getValue() 
                  != cards[4].getValue()) hasPair = true;
      else if (cards[3].getValue() == cards[4].getValue()) hasPair = true;
      else hasPair = false;
      
      return (hasPair && !has3OfAKind(cards));
   }
   
   
   //Returns whether or not the hand was a two pair
   public static boolean has2Pair(Card[] cards) {
      
      boolean hasTwoPair = false;
      
      if ((cards[0].getValue() == cards[1].getValue() && cards[2].getValue() == cards[3].getValue()) ||
          (cards[1].getValue() == cards[2].getValue() && cards[3].getValue() == cards[4].getValue()) ||
          (cards[0].getValue() == cards[1].getValue() && cards[3].getValue() == cards[4].getValue())) hasTwoPair = true;
      
      return (hasTwoPair && !has4OfAKind(cards) && !hasFullHouse(cards));
   }
   
   
   //Returns whether or not the hand was a full house
   public static boolean hasFullHouse(Card[] cards) {
      
      return ((cards[0].getValue() == cards[1].getValue() && cards[2].getValue() == cards[3].getValue() &&
               cards[3].getValue() == cards[4].getValue() && (cards[0].getValue() != cards[2].getValue())) ||
              
              (cards[0].getValue() == cards[1].getValue() && cards[1].getValue() == cards[2].getValue() &&
               cards[3].getValue() == cards[4].getValue() && (cards[0].getValue() != cards[3].getValue())));
   }
   
   
   //Creates new deck of cards, shuffles them, deals 5 cards to an array of cards, sorts the 
   //cards, calls up to nine other methods to check what type of hand was dealt. And returns
   //a String to indicate what type of hand it was. 
   public static String evaluateOneHandOfPoker() {
      
      String result = "";
      Deck d = new Deck();
      d.shuffle();
      
      Card[] hand = {d.deal(), d.deal(), d.deal(), d.deal(), d.deal()};
      java.util.Arrays.sort(hand);
      
      if (hasRoyalFlush(hand)) result = "Royal Flush";
      else if (hasStraightFlush(hand)) result = "Straight Flush";
      else if (has4OfAKind(hand)) result = "4 of a kind";
      else if (hasFullHouse(hand)) result ="Full house";
      else if (hasFlush(hand)) result = "Flush";
      else if (hasStraight(hand)) result = "Straight";
      else if (has3OfAKind(hand)) result = "3 of a kind";
      else if (has2Pair(hand)) result = "2 pair";
      else if (hasPair(hand)) result = "1 pair";
      else result = "Loser";
      
      return result;
      
   }
   
   //Plays the user-specified amount of hands, records the results of each hand in an array, then
   //counts the number of each type of hand and prints some overall summary statistics and the
   //amount of time it took to analyze all the hands. 
   public static void playAndShowStats(int n) {
      
      long startTime = System.nanoTime();
      
      int royalFlushCount = 0;
      int straightFlushCount = 0;
      int fourOfAKindCount = 0;
      int fullHouseCount = 0;
      int flushCount = 0;
      int straightCount = 0;
      int threeOfAKindCount = 0;
      int twoPairCount = 0;
      int onepairCount = 0;
      int loserCount = 0;
      String loser = "Loser";
      String onePair = "One Pair";
      String twoPair = "Two Pair";
      String threeOfAKind = "Three of a Kind";
      String straight = "Straight";
      String flush = "Flush";
      String fullHouse = "Full House";
      String fourOfAKind = "Four of a Kind";
      String straightFlush = "Straight Flush";
      String royalFlush = "Royal Flush";
      
      
      String[] resultsArray = new String[n];
      for (int i = 0; i < resultsArray.length; i++) {
         resultsArray[i] = evaluateOneHandOfPoker();
      }
      
      for (int i = 0; i < resultsArray.length; i++) {
         if (resultsArray[i].equals("Royal Flush")) royalFlushCount++;
         if (resultsArray[i].equals("Straight Flush")) straightFlushCount++;
         if (resultsArray[i].equals("4 of a kind")) fourOfAKindCount++;
         if (resultsArray[i].equals("Full house")) fullHouseCount++;
         if (resultsArray[i].equals("Flush")) flushCount++;
         if (resultsArray[i].equals("Straight")) straightCount++;
         if (resultsArray[i].equals("3 of a kind")) threeOfAKindCount++;
         if (resultsArray[i].equals("2 pair")) twoPairCount++;
         if (resultsArray[i].equals("1 pair")) onepairCount++;
         if (resultsArray[i].equals("Loser")) loserCount++;
      }
      long endTime = System.nanoTime();
      
      
      
      System.out.printf("%16s:%8d %10.5f%%%n", loser, loserCount, (double) (loserCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", onePair, onepairCount, (double) (onepairCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", twoPair, twoPairCount, (double) (twoPairCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", threeOfAKind, threeOfAKindCount, (double) (threeOfAKindCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", straight, straightCount, (double) (straightCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", flush, flushCount, (double) (flushCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", fullHouse, fullHouseCount, (double) (fullHouseCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", fourOfAKind, fourOfAKindCount, (double) (fourOfAKindCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", straightFlush, straightFlushCount, (double) (straightFlushCount)/n*100);
      System.out.printf("%16s:%8d %10.5f%%%n", royalFlush, royalFlushCount, (double) (royalFlushCount)/n*100);
      long duration = (endTime - startTime);
      double seconds = duration / 1.0E09;
      System.out.println("---------------------------------------------");
      System.out.print(n + " hands analyzed in ");
      System.out.printf("%.3f", seconds);
      System.out.println(" seconds.");
   }
   
   
}








