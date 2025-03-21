import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author You
 *
 */
public class Player {
	// Add member variables as needed. You MAY NOT use static variables, or otherwise allow direct communication between
	// different instances of this class by any means; doing so will result in a score of 0.


	private ArrayList<CardKnowledge> cardKnowledges; // Tracks knowledge about cards in the player's hand
	private ArrayList<CardKnowledge> PartnerCardKnowledges; // Tracks knowledge about cards in the partner's hand
	private int handSize; // Tracks the size of the player's hand

	public Player() {
		cardKnowledges = new ArrayList<>();
		PartnerCardKnowledges = new ArrayList<>();
		// Initialize the cardKnowledges list with 5 CardKnowledge objects, all with cardState = 0
		for (int i = 0; i < 5; i++) {
			CardKnowledge ck = new CardKnowledge();
			ck.cardState = CardKnowledge.UNKNOWN; // Set cardState to 0 (unknown)
			cardKnowledges.add(ck);
			CardKnowledge c2 = new CardKnowledge();
			c2.cardState = CardKnowledge.UNKNOWN;
			PartnerCardKnowledges.add(c2); // Initialize partner's card knowledge
		}
		handSize = 0;
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

		// Update partner's card knowledge
		PartnerCardKnowledges.remove(disIndex); // Remove the discarded card
		if (draw != null) {
			PartnerCardKnowledges.add(drawIndex, new CardKnowledge()); // Add new card knowledge for the drawn card
		}

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
		cardKnowledges.remove(disIndex);
		if (drawSucceeded) {
			cardKnowledges.add(drawIndex, new CardKnowledge());
		}

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

		// Update partner's card knowledge
		PartnerCardKnowledges.remove(playIndex); // Remove the played card
		if (draw != null) {
			PartnerCardKnowledges.add(drawIndex, new CardKnowledge()); // Add new card knowledge for the drawn card
		}

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
		cardKnowledges.remove(playIndex);
		if (drawSucceeded) {
			cardKnowledges.add(drawIndex, new CardKnowledge());
		}

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

		if (isChopForHint(indices)) {
			// Only set the chop card to SAVED (cardState = 1)
			int chopIndex = indices.get(indices.size() - 1);
			cardKnowledges.get(chopIndex).cardState = CardKnowledge.SAVED;
		} else if (indices.size() == 1) {
			// If the hint includes only one card, set it to IMMEDIATELY_PLAYABLE (cardState = 2)
			cardKnowledges.get(indices.get(0)).cardState = CardKnowledge.IMMEDIATELY_PLAYABLE;
		}
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

		if (isChopForHint(indices)) {
			// Only set the chop card to SAVED (cardState = 1)
			int chopIndex = indices.get(indices.size() - 1);


			//possibly check if this number for each color is boardstate.isPlayable,
			//if it is, then set it to immediately playable

			if(number == 1){
				cardKnowledges.get(chopIndex).cardState = CardKnowledge.IMMEDIATELY_PLAYABLE;
			} else if(number == 2){

				boolean check = false;
				for(int i = 0; i < 5; i++){
					if(boardState.tableau.get(i) >= 1){
						check = true;
					}
					else{
						check = false;
					}
				}
				if(check){
					cardKnowledges.get(chopIndex).cardState = CardKnowledge.IMMEDIATELY_PLAYABLE;
				}
				else{
					cardKnowledges.get(chopIndex).cardState = CardKnowledge.SAVED;
				}
			} else if (number == 3){
				boolean check = false;
				for(int i = 0; i < 5; i++){
					if(boardState.tableau.get(i) >= 2){
						check = true;
					}
					else{
						check = false;
					}
				}
				if(check){
					cardKnowledges.get(chopIndex).cardState = CardKnowledge.IMMEDIATELY_PLAYABLE;
				}
				else{
					cardKnowledges.get(chopIndex).cardState = CardKnowledge.SAVED;
				}
			}
			else{
				cardKnowledges.get(chopIndex).cardState = CardKnowledge.SAVED;
			}


		} else if (indices.size() == 1) {
			// If the hint includes only one card, set it to IMMEDIATELY_PLAYABLE (cardState = 2)
			cardKnowledges.get(indices.get(0)).cardState = CardKnowledge.IMMEDIATELY_PLAYABLE;
		} else if(number == 1){
			for(int i = 0; i < indices.size(); i++){
				cardKnowledges.get(indices.get(i)).cardState = CardKnowledge.IMMEDIATELY_PLAYABLE;
			}
		}

	}


	// Helper method to check if a card is the chop card
	private boolean isChopForHint(ArrayList<Integer> indices) {
		int chopIndex = 4;
		for(int i = handSize - 1; i >= 0; i--){
			if(cardKnowledges.get(i).cardState == CardKnowledge.UNKNOWN){
				chopIndex = i;
				break;
			}
		}
		int highestIndex = indices.get(indices.size() - 1);
		if(highestIndex == chopIndex){
			return true;
		}
		else{
			return false;
		}
		//return cardKnowledges.get(highestIndex).cardState == CardKnowledge.UNKNOWN;
	}











	// THIS IS THE ASK METHOD




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


		handSize = yourHandSize;

//
//		//if its the end of the game, play one of your saved cards
//		if(boardState.deckSize <= 2 && boardState.numFuses > 1){
//			for(int i = 0; i < cardKnowledges.size(); i++){
//				if(cardKnowledges.get(i).cardState == CardKnowledge.SAVED){
//					return "PLAY " + i + " 0";
//				}
//			}
//		}

		if(boardState.numHints > 0){
			// Check for endangered cards first
			String endangeredAction = checkForEndangered(partnerHand, boardState);
			if (endangeredAction != null) {
				return endangeredAction;
			}
		}


// Check if the player can play a card
		String playAction = canIPlay();
		if (playAction != null) {
			return playAction;
		}








		if(boardState.numHints > 0){
			// Check for playable cards in partner's hand
			String playableAction = checkForPlayable(partnerHand, boardState);
			if (playableAction != null) {
				return playableAction;
			}
		}



		// Discard the highest-indexed unknown card (chop)
		String discardAction = chop();
		if (discardAction != null) {
			return discardAction;
		}


		return "DISCARD 0 0";
	}



	//THIS IS THE ASK METHOD




	// Helper method to check if a card is endangered
	private boolean isEndangered(Card card, Board boardState) {
		int count = 0;
		for (Card discardedCard : boardState.discards) {
			if (discardedCard.equals(card)) {
				count++;
			}
		}


////NEW, GOES FOR RULE THAT 2 IS VALUABLE SO SAVE THEM
//		if(card.value == 2){
//			if(boardState.tableau.get(card.color) <= 1){
//				return true;
//			}
//		}



		if (card.value == 1 && count >= 2) {
			return true;
		} else if ((card.value == 2 || card.value == 3 || card.value == 4) && count >= 1) {
			return true;
		} else if (card.value == 5) {
			return true;
		}
		return false;
	}

	// Helper method to determine whether to hint color or number for an endangered card
	private String makeEndangeredHint(Card endangeredCard, Hand partnerHand) {
		int colorMatches = 0;
		int numberMatches = 0;
		for (int i = 0; i < partnerHand.size(); i++) {
			Card partnerCard = partnerHand.get(i);
			if (partnerCard.color == endangeredCard.color) {
				colorMatches++;
				//System.out.println("Color match: " + partnerCard + " " + endangeredCard); // Debugging
			}
			if (partnerCard.value == endangeredCard.value) {
				numberMatches++;
				//System.out.println("Number match: " + partnerCard + " " + endangeredCard); // Debugging
			}
		}


		// Hint the attribute (color or number) that has fewer matches
		if (numberMatches <= colorMatches) {
			return "NUMBERHINT " + endangeredCard.value;
		} else {
			return "COLORHINT " + endangeredCard.color;
		}

	}




	// Helper method to determine whether to hint color or number for a card
	private String makeNormalHint(Card card, Hand partnerHand) {
		int colorMatches = 0;
		int numberMatches = 0;
		for (int i = 0; i < partnerHand.size(); i++) {
			Card partnerCard = partnerHand.get(i);
			if (partnerCard.color == card.color) {
				colorMatches++;
				//System.out.println("Color match: " + partnerCard + " " + endangeredCard); // Debugging
			}
			if (partnerCard.value == card.value) {
				numberMatches++;
				//System.out.println("Number match: " + partnerCard + " " + endangeredCard); // Debugging
			}
		}


		// Hint the attribute (color or number) that has fewer matches
		if (numberMatches <= 1) {
			return "NUMBERHINT " + card.value;
		} else if (colorMatches <= 1) {
			return "COLORHINT " + card.color;
		}

		//maybe use flag here for doubles
//		//loop through all the cards in partner's hand, if color AND number matches twice, grab both indexes
//		//send in a hint for the number they share
//		int count = 0;
//		for(int i = 0; i < partnerHand.size(); i++){
//			Card partnerCard = partnerHand.get(i);
//			if(partnerCard.color == card.color && partnerCard.value == card.value){
//				count++;
//			}
//		}
//		if(count >= 2){
//			return "NUMBERHINT " + card.value;
//		}

		//THIS WOULD BE IF WE WANT TO HINT FOR MANY 1s
//		if(card.value == 1){
//			return "NUMBERHINT " + card.value;
//		}

		return null; // No hint needed
	}




	// First ask method: Check for endangered cards in partner's hand
	private String checkForEndangered(Hand partnerHand, Board boardState) {
		for (int i = partnerHand.size() - 1; i >= 0; i--) {
			Card partnerCard = partnerHand.get(i);

			if(PartnerCardKnowledges.get(i).cardState == CardKnowledge.UNKNOWN){
				if (isEndangered(partnerCard, boardState)) {
					PartnerCardKnowledges.get(i).cardState = CardKnowledge.SAVED;

					String hint = makeEndangeredHint(partnerCard, partnerHand);
					if (hint != null) {
						return hint;
					}
				}
				else{
					break;
				}
			}

		}
		return null; // No endangered cards found
	}


	// Second ask method: Check if the player can play a card
	private String canIPlay() {
		for (int i = 0; i < cardKnowledges.size(); i++) {
			if (cardKnowledges.get(i).cardState == CardKnowledge.IMMEDIATELY_PLAYABLE) {
				return "PLAY " + i + " 0";
			}
		}
		return null; // No immediately playable cards
	}


	// Third ask method: Check for playable cards in partner's hand
	private String checkForPlayable(Hand partnerHand, Board boardState) {
		for (int i = 0; i < partnerHand.size(); i++) {
			Card partnerCard = partnerHand.get(i);
			//System.out.println("Checking for playable card: " + partnerCard);
			if (boardState.isLegalPlay(partnerCard)) {
				//System.out.println("Found playable card: " + partnerCard);
				String hint = makeNormalHint(partnerCard, partnerHand);
				if (hint != null) {
					//System.out.println("Hinting at playable card: " + partnerCard);
					PartnerCardKnowledges.get(i).cardState = CardKnowledge.IMMEDIATELY_PLAYABLE;
					return hint;
				}
			}
		}
		return null; // No playable cards found
	}


	// Fourth ask method: Discard the highest-indexed unknown card (chop)
	private String chop() {
		for (int i = handSize - 1; i >= 0; i--) {
			if (cardKnowledges.get(i).cardState == CardKnowledge.UNKNOWN) {
				return "DISCARD " + i + " 0";
			}
		}
		return null; // No unknown cards to discard
	}







}
