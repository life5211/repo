package cn.edu.xhu.phy.card.service;

/**
 * @version 1.0
 * @date 2026/3/2 20:11
 */
@lombok.Data
public class Card implements Comparable<Card> {
    public static char[] SUITS = {'♠', '♥', '♣', '♦'};
    public static char[] RANKS = {'2', '3', '4', '5', '6', '7', '8', '9', '0', 'J', 'Q', 'K', 'A'};
    public int suit;
    public int rank;
    public int sort;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        this.sort = rank * 4 + suit;
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(sort, o.sort);
    }

    @Override
    public String toString() {
        return String.format("%s%s", RANKS[rank - 2], SUITS[suit - 1]);
    }
}
