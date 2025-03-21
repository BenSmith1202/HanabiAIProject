import java.util.HashSet;
import java.util.Set;

/**
 * Tracks possible values for one unknown card
 */
public class CardKnowledge {



    public static final int UNKNOWN = 0;
    public static final int SAVED = 1;
    public static final int IMMEDIATELY_PLAYABLE = 2;
    public int cardState = 0;



    private Set<Card> options;
    /**
     * Allow all possible cards
     */
    public CardKnowledge(){
        this(null);
    }

    /**
     * Allow all cards except those specified in...
     * @param impossibleCards - this card could not be any card in impossibleCards
     */
    public CardKnowledge(Set<Card> impossibleCards) {
        options = new HashSet<>();

        for(int clr=Colors.MIN_COLOR; clr<=Colors.MAX_COLOR; clr++) {
            for (int val=Card.MIN_VALUE; val<=Card.MAX_VALUE; val++) {
                Card theCard = new Card(clr, val);

                if (impossibleCards == null || !impossibleCards.contains(theCard)) {
                    options.add(theCard);
                }
            }
        }
    }


}
