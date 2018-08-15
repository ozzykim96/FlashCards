package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.jdent.flashcards.card.Card;

class CardsTest {
	Card card;
	
	public CardsTest() {
		card = new Card("banana", "one of fruits.");
	}
	
	@Test
	void testCard() {
		
	}

	@Test
	void testGetName() {
		assertEquals("banana", card.getName(), "name must be \"banana");
	}

	@Test
	void testGetDescription() {
		assertEquals("one of fruits", card.getDescription());
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
