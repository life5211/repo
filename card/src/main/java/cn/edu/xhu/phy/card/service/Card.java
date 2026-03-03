package cn.edu.xhu.phy.card.service;

/**
 * @version 1.0
 * @date 2026/3/2 20:11
 */
@lombok.Data
public class Card {
    public int suit;
    public int rank;
    public int sort;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        this.sort = sort * 4 + suit;
    }
}
