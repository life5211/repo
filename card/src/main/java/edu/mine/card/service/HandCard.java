package edu.mine.card.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @date 2026/3/2 20:47
 */
public class HandCard implements Comparable<HandCard> {
    List<Card> cards;
    int type;  // 单张0 对子1 顺子2 金花3 顺金4 豹子5
    int r1, r2, r3;

    public HandCard(List<Card> cards) {
        this.cards = cards.stream().sorted(Comparator.comparing(Card::getSort)).collect(Collectors.toList());
        this.r1 = cards.get(0).rank;
        this.r2 = cards.get(1).rank;
        this.r3 = cards.get(2).rank;
        this.type = this.getHandType();
    }

    private int getHandType() {
        boolean triple = (r1 == r2 && r2 == r3);
        if (triple) return 5;
        boolean sameSuit = cards.stream().map(Card::getSuit).allMatch(c -> c == cards.get(0).suit);
        boolean straight = (r2 == r1 + 1 && r3 == r2 + 1) || (r1 == 2 && r2 == 3 && r3 == 14);
        if (sameSuit && straight) return 4;
        if (sameSuit) return 3;
        if (straight) return 2;
        boolean pair = (r1 == r2 || r2 == r3 || r1 == r3);
        if (pair) return 1;
        return 0;
    }

    @Override
    public int compareTo(HandCard b) {
        if (this.type != b.type) return Integer.compare(this.type, b.type);
        switch (this.type) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
                int i = Integer.compare(this.r3, b.r3);
                if (i != 0) return i;
                i = Integer.compare(this.r2, b.r2);
                if (i != 0) return i;
                return Integer.compare(this.r1, b.r1);
            case 1:
                int a11 = r2, a2, b11 = b.r2, b2;
                if (r1 == r2) a2 = r3;
                else a2 = r1;
                if (b.r1 == b.r2) b2 = b.r3;
                else b2 = b.r1;
                if (a11 == b11) return Integer.compare(a2, b2);
                return Integer.compare(a11, b11);
            default:
                System.err.println(this);
        }
        return 0;
    }
}
