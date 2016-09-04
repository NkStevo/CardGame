package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Card {
	
	public enum Suit {
		HEARTS, SPADES, DIAMONDS, CLUBS
	}
	
	private Sprite cSprite;
	private Suit cSuit;
	
	private int value;
	
	//For when a card has two possible values
	private int val1;
	private int val2;
	
	//Constructor for regular card
	public Card(Sprite sprite, Suit suit, int value) {
		this.cSprite = sprite;
		this.cSuit = suit;
		this.value = value;
	}
	
	//Constructor for card with two possible values
	public Card(Sprite sprite, Suit suit, int val1, int val2) {
		this(sprite, suit, val1);
		
		this.val1 = val1;
		this.val2 = val2;
	}
	
	public int getValue() {
		return value;
	}
	
	public Sprite getSprite() {
		return cSprite;
	}
	
	public Suit getSuit() {
		return cSuit;
	}
	
	public int getAltVal(int alt) {
		if (alt == 1) { 
			return this.val1; 
		} else {
			return this.val2;
		}
	}
	
	public void changeVal(int choice) {
		this.value = (choice == 1) ? val1 : val2;
	}
}
