import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author You
 *
 */
public class Player {
	// Add member variables as needed. You MAY NOT use static variables, or otherwise allow direct communication between
	// different instances of this class by any means; doing so will result in a score of 0.


	/**
	 * This default constructor should be the only constructor you supply.
	 */
	public Player() {

	}
	
	/**
	 * This method runs whenever your partner discards a card.
	 * @param startHand The hand your partner started with before discarding.
	 * @param discard The card he discarded.
	 * @param disIndex The index from which he discarded it.
	 * @param draw The card he drew to replace it; null, if the deck is empty.
	 * @param drawIndex The index to which he drew it.
	 * @param finalHand The hand your partner ended with after redrawing.
	 * @param boardState The state of the board after play.
	 */
	public void tellPartnerDiscard(Hand startHand, Card discard, int disIndex, Card draw, int drawIndex, 
			Hand finalHand, Board boardState) {
		// update my card knowledge

	}
	
	/**
	 * This method runs whenever you discard a card, to let you know what you discarded.
	 * @param discard The card you discarded.
	 * @param disIndex The index from which you discarded it.
	 * @param drawIndex The index to which you drew the new card (if drawSucceeded)
	 * @param drawSucceeded true if there was a card to draw; false if the deck was empty
	 * @param boardState The state of the board after play.
	 */
	public void tellYourDiscard(Card discard, int disIndex, int drawIndex, boolean drawSucceeded, Board boardState) {

		// update my card knowledge with the card (increase the count of the card)
	}




	
	/**
	 * This method runs whenever your partner played a card
	 * @param startHand The hand your partner started with before playing.
	 * @param play The card she played.
	 * @param playIndex The index from which she played it.
	 * @param draw The card she drew to replace it; null, if the deck was empty.
	 * @param drawIndex The index to which she drew the new card.
	 * @param finalHand The hand your partner ended with after playing.
	 * @param wasLegalPlay Whether the play was legal or not.
	 * @param boardState The state of the board after play.
	 */
	public void tellPartnerPlay(Hand startHand, Card play, int playIndex, Card draw, int drawIndex,
			Hand finalHand, boolean wasLegalPlay, Board boardState) {

		// take any saved cards that were equal to that and unsave them

	}







	
	/**
	 * This method runs whenever you play a card, to let you know what you played.
	 * @param play The card you played.
	 * @param playIndex The index from which you played it.
	 * @param drawIndex The index to which you drew the new card (if drawSucceeded)
	 * @param drawSucceeded  true if there was a card to draw; false if the deck was empty
	 * @param wasLegalPlay Whether the play was legal or not.
	 * @param boardState The state of the board after play.
	 */
	public void tellYourPlay(Card play, int playIndex, int drawIndex, boolean drawSucceeded,
							 boolean wasLegalPlay, Board boardState) {
		// ???
	}








	/**
	 * This method runs whenever your partner gives you a hint as to the color of your cards.
	 * @param color The color hinted, from Colors.java: RED, YELLOW, BLUE, GREEN, or WHITE.
	 * @param indices The indices (from 0-4) in your hand with that color.
	 * @param partnerHand Your partner's current hand.
	 * @param boardState The state of the board after the hint.
	 */
	public void tellColorHint(int color, ArrayList<Integer> indices, Hand partnerHand, Board boardState) {

		//  if the hint was about chop = save that card
		// if the hint was about one card not in chop = play that card
		// if the hint was about multiple cards including chop, only save the chop, ignore others
	}




	
	/**
	 * This method runs whenever your partner gives you a hint as to the numbers on your cards.
	 * @param number The number hinted, from 1-5.
	 * @param indices The indices (from 0-4) in your hand with that number.
	 * @param partnerHand Your partner's current hand.
	 * @param boardState The state of the board after the hint.
	 */
	public void tellNumberHint(int number, ArrayList<Integer> indices, Hand partnerHand, Board boardState) {
		//  if the hint was about chop = save that card
		// if the hint was about one card not in chop = play that card
		// if the hint was about multiple cards including chop, only save the chop, ignore others
	}





	
	/**
	 * This method runs when the game asks you for your next move.
	 * @param yourHandSize How many cards you have in hand.
	 * @param partnerHand Your partner's current hand.
	 * @param boardState The current state of the board.
	 * @return A string encoding your chosen action. Actions should have one of the following formats; in all cases,
	 *  "x" and "y" are integers.
	 * 	a) "PLAY x y", which instructs the game to play your card at index x and to draw a card back to index y. You
	 *     should supply an index y even if you know the deck to be empty. All indices should be in the range 0-4.
	 *     Illegal plays will consume a fuse; at 0 fuses, the game ends with a score of 0.
	 *  b) "DISCARD x y", which instructs the game to discard the card at index x and to draw a card back to index y.
	 *     You should supply an index y even if you know the deck to be empty. All indices should be in the range 0-4.
	 *     Discarding returns one hint if there are fewer than the maximum number available.
	 *  c) "NUMBERHINT x", where x is a value from 1-5. This command informs your partner which of his cards have a value
	 *     of the chosen number. An error will result if none of his cards have that value, or if no hints remain.
	 *     This command consumes a hint.
	 *  d) "COLORHINT x", where x is one of the RED, YELLOW, BLUE, GREEN, or WHITE constant values in Colors.java.
	 *     This command informs your partner which of his cards have the chosen color. An error will result if none of
	 *     his cards have that color, or if no hints remain. This command consumes a hint.
	 */
	public String ask(int yourHandSize, Hand partnerHand, Board boardState) {
		// A really dumb agent that just discards
		// TODO: replace this with your agent's decision-making code

		//MAIN PSEUDOCODE

		/*-=|CARD MARKERS|=-
    There are four card markers:
    -  UNKNOWN cards are cards which have NOT been hinted at yet.
    -  The leftmost UNKNOWN card is the CHOP card
    -  if the CHOP card is hinted at (even if hinted at as part of a multi hint) is becomes SAVED
    -  if a SAVED or UNKNOWN card is hinted at (by itself) it is IMMEDIATELY_PLAYABLE.
      IMMEDIATELY_PLAYABLE cards are valid to play onto the stacks right now.

    Additionally, if there is only one card of a certain type left in play (out of the discard pile), and
     it has not been played yet, it is ENDANGERED.

    -=|STRATEGY|=-
    Flow of play:

    If you see any ENDANGERED cards on another player's CHOP position ->
      Hint at it with the fewest collateral cards possible (hint as few cards other than the chop as you can)
    Else:
    If you have a card marked IMMEDIATELY_PLAYABLE ->
      play it!
    Else:
    If another player has a card that they should mark IMMEDIATELY_PLAYABLE ->
      If you can hint at that card alone (no collateral) ->
        Hint at the card
    Else:
    If you have an unknown card in the CHOP position ->
      Discard the card in your Chop position.
    Else:
    Chop your oldest saved card
		 */

		// If the card in other player's chop position is endangered, save it.
		if (otherPlayerHasEndangered()){
			//hint at the endangered to save it
			hintAtEndangered();
			//return something
		}

		//else
		//if I have a playable card, play it.

		int playableIndex = iHavePlayable();
		if (playableIndex >= 0){
			//play card at playableIndex
			playCard(playableIndex);
			//return something
		}

		//else
		//if the other player has a playable card

		int otherPlayableIndex = otherPlayerHasPlayable();
		if (otherPlayableIndex >= 0){
			//if I can hint at it without collateral, hint at it
			if (getMinimumCluedCards(otherPlayableIndex) == 1){
				//hint specifically at the playable card and no others
				hintAtOthersPlayable(otherPlayableIndex);
				//return something
			}
		}

		//else
		// if I have at least one unknown card, chop the one in the chopping position.

		int chopIndex = canIChopUnknown();
		if (chopIndex >= 0){
			//chop it
			chop(chopIndex);
		}

		//else
		// if I have only saved cards, chop the oldest

		chopIndex = getOldestSavedIndex();
		if (chopIndex >= 0)
		{
			//chop it
			chop(chopIndex);
		}

		//else
		// Something went wrong, I should never be here.



		if(boardState.numFuses == 0) {
			return "DISCARD 0 0";
		}





		return "DISCARD 0 0";
	}


	/**
	 *
	 * @return True if the other player should save their chop card.
	 */
	private boolean otherPlayerHasEndangered()
	{

		return false;
	}

	/**
	 * Hints at endangered card in other player's hand using with the minimum collateral
	 */
	private void hintAtEndangered()
	{
		//hints at endangered card using TELL command
	}

	/**
	 *
	 * @return integer of card that I should play, or -1 for none.
	 */
	private int iHavePlayable()
	{

		return -1;
	}

	/**
	 * Plays a card from my hand at a certain index.
	 * @param index the index of the card you want to play.
	 */
	private void playCard(int index)
	{
		// play the card using the PLAY command
	}

	/**
	 *
	 * @return index of a card which could be played onto the tableau right now in the other player's hand.
	 * Returns -1 if no cards are playable.
	 */
	private int otherPlayerHasPlayable()
	{

		return -1;
	}

	/**
	 * Hints specifically at one and only one of your opponents cards.
	 * @param index the index of the card you want to hint at
	 */
	private void hintAtOthersPlayable(int index)
	{

	}

	/**
	 *
	 * @return the minimum number of cards that could be hinted at with a hint that includes i
	 * in other words, if i want to hint i, how few other cards can i collaterally hint at.
	 * If i return 1, then i can hint at the given card alone.
	 * Returns -1 in case of failure.
	 * @param cardIndex The index of the card in the other player's hand that i want to hint at.
	 */
	private int getMinimumCluedCards(int cardIndex)
	{

		return -1;
	}


	/**
	 *
	 * @return the index of the chop card, or -1 if there are no unknown cards.
	 */
	private int canIChopUnknown()
	{

		return -1;
	}

	/**
	 *
	 * Attempts to chop the card at the chop position.
	 * @return false if chopping failed.
	 * @param index index of card to chop
	 */
	private boolean chop(int index)
	{

		System.out.println("Chopping failed");
		return false;
	}

	/**
	 *
	 * @return the index of the longest ago saved card in your hand, or -1 for no saved cards.
	 */
	private int getOldestSavedIndex()
	{

		return -1; //THIS IS A GAME LOSS
	}



}
