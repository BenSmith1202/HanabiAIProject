import java.util.HashSet;
import java.util.Set;

/**
 * Tracks possible values for one unknown card
 */
public class CardKnowledge {
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

    public int numOptions(){
        return options.size();
    }

    public void knowColor(int clr){
        options.removeIf(crd -> crd.color != clr);
    }

    public void knowValue(int val){
        options.removeIf(crd -> crd.value != val);
    }


    public void eliminateColor(int clr){
        options.removeIf(crd -> crd.color == clr);
    }

    public void eliminateValue(int val){
        options.removeIf(crd -> crd.value == val);
    }

    public void eliminateCard(Card crd){
        options.remove(crd);
    }

    public void eliminateNonPlayableOptions(Board b) {
        options.removeIf(crd -> !(b.isLegalPlay(crd)));
    }

    /**
     * If there is only one color option for this card, return it
     * @return the color of the card, or -1 if it could be more than one color
     */
    public int getKnownColor() {
        int color = options.iterator().next().color;

        for (Card crd : options) {
            if (crd.color != color) {
                return -1;
            }
        }
        return color;
    }

    /**
     * If there is only one value option for this card, return it
     * @return the value of the card, or -1 if it could be more than one value
     */
    public int getKnownValue() {
        int val = options.iterator().next().value;

        for (Card crd : options) {
            if (crd.value != val) {
                return -1;
            }
        }
        return val;
    }

    public boolean isDefinitelyPlayable(Board b){
        for (Card crd : options) {
            if (!b.isLegalPlay(crd)) {
                return false;
            }
        }
        return true;
    }

    public boolean couldBePlayable(Board b){
        for (Card crd : options) {
            if (b.isLegalPlay(crd)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDiscardable(Board b){
        for (Card crd : options) {
            if (crd.value > b.tableau.get(crd.color)) {
                // This card option is still useful for the tableau
                return false;
            }
        }
        return true;
    }
}
