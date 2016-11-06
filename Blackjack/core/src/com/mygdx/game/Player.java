package com.mygdx.game;

import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand;
	private int handValue;
	private String name;
	
	public Player(String name) {
		hand = new ArrayList<Card>();
		handValue = 0;
		this.name = name;
	}
	
	public void addValue(int add) {
		handValue += add;
	}
	
	public void addToHand(Card card) {
		hand.add(card);
		
		if (card.getValue() == 11 && handValue + card.getValue() > 21 && handValue + card.getAltVal(2) <= 21) {
			handValue += card.getAltVal(2);
		} else {
			handValue += card.getValue();
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getValue() {
		return handValue;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public String getName() {
		return name;
	}
}
