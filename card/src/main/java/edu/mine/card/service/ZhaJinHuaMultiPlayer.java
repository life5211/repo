package edu.mine.card.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @version 1.0
 * @date 2026/3/2 17:32
 */


public class ZhaJinHuaMultiPlayer {
    static List<Card> deck = null;

    public static void newDeck() {
        if (deck == null) {
            deck = new ArrayList<>();
            for (int s = 1; s <= 4; s++) {
                for (int r = 2; r <= 14; r++) {
                    deck.add(new Card(s, r));
                }
            }
        }
        Collections.shuffle(deck);
    }

    // 发n手牌，返回赢家牌型
    public static int getWinnerType(int n) {
        newDeck();
        List<HandCard> hands = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Card> cards = new ArrayList<>();
            cards.add(deck.get(i));
            cards.add(deck.get(i + n));
            cards.add(deck.get(i + n + n));
            hands.add(new HandCard(cards));
            // hands.sort(HandCard::compareTo);
        }

        HandCard winner = Collections.max(hands);
        return winner.type;
    }

    public static void main(String[] args) {
        int totalSim = 2000000;
        String[] typeNames = {"单张", "对子", "顺子", "金花", "顺金", "豹子"};

        System.out.println("=== 炸金花 1~17 人 获胜牌型统计（每局 " + totalSim + " 次）===\n");

        for (int players = 1; players <= 17; players++) {
            int[] winCount = new int[6];

            for (int i = 0; i < totalSim; i++) {
                int winnerType = getWinnerType(players);
                winCount[winnerType]++;
            }

            System.out.println("【" + players + " 人局】");
            for (int t = 0; t < 6; t++) {
                double rate = winCount[t] * 100.0 / totalSim;
                System.out.printf("%s：%.2f%%\n", typeNames[t], rate);
            }
            System.out.println("------------------------");
        }
    }

}
