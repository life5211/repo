package edu.mine.card.service;

/**
 * @version 1.0
 * @date 2026/3/2 20:11
 */
@lombok.Data
public class Card implements Comparable<Card> {
    public static String[] SUITS = {"♠", "♥", "♣", "♦"};
    public static String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    public int suit;
    public int rank;
    public int sort;
    public String tag;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        this.sort = rank * 4 + suit;
        this.tag = String.format("%s%s", SUITS[suit - 1], RANKS[rank - 2]);
    }

    @Override
    public int compareTo(Card c) {
        return Integer.compare(sort, c.sort);
    }

    @Override
    public String toString() {
        return this.tag;
    }
}
