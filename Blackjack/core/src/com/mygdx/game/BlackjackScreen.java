package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlackjackScreen implements Screen {
	private CardGame game;
	
	private SpriteBatch batch;
	private BitmapFont font;
	
	private Texture cardSheet;
	private TextureRegion card;
	private Sprite cardSprite;
	
	private OrthographicCamera camera;
	
	private ArrayList<Card> cardDeck;
	
	/**Each ArrayList of Cards is a player's hand
	 * The ArrayList at index 0 is the dealer's hand
	 */
	private ArrayList<ArrayList<Card>> playerHands;

	
	private int cardInd;
	private Card testCard;
	
	public BlackjackScreen(CardGame game) {
		this.game = game;
		
		batch = game.batch;
		font = game.font;
		
		camera = new OrthographicCamera();
		
		Random randGen = new Random();
		cardInd = randGen.nextInt(51);
		
		this.loadCards();
		
		testCard = cardDeck.remove(cardInd);
		
		System.out.println(testCard.getSuit());
		System.out.println(testCard.getValue());
		System.out.println(testCard.getAltVal(0));
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(6f/255f, 156f/255f, 36f/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		testCard.getSprite().draw(batch);	
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		game.dispose();
	}

	public void loadCards() {
		TextureRegion card;
		Sprite cardSprite;
		
		int row = 9;
		
		int width = 140;
		int height = 190;
		
		int xPos = 0;
		int yPos = 0;
		
		int value = 10;
		int altVal = 1;
		
		int valKey;
		int suitKey;
		
		Card.Suit suit;
		
		cardSheet = new Texture(Gdx.files.internal("spritesheets\\playingCards.png"));
		cardDeck = new ArrayList<Card>();
		
		for (int i = 0; i < 52; i++, yPos += height) {
			if (xPos == 140 && yPos == height * 3) {
				i--;
				continue;
			}
			
			card = new TextureRegion(cardSheet, xPos, yPos, width, height);
			cardSprite = new Sprite(card);
			
			cardSprite.setScale(0.5f);
			cardSprite.setCenter(400, 300);
			
			//Keeps track of which card in the suit is being loaded
			valKey = i % 13;
			
			if (valKey >= 0 && valKey < 3) {
				value = 10;
			} else if (valKey == 3) {
				value = 11;
				altVal = 1;
			} else {
				value--;
			}
			
			//Keeps track of the suit of the card being loaded
			suitKey = i / 13;
			
			if (suitKey < 1) {
				suit = Card.Suit.SPADES;
			} else if (suitKey == 1) {
				suit = Card.Suit.HEARTS;
			} else if (suitKey == 2) {
				suit = Card.Suit.DIAMONDS;
			} else {
				suit = Card.Suit.CLUBS;
			}
			
			//A special exception for two cards on the spritesheet which are out of place
			if (valKey == 12 && suitKey == 1) {
				suit = Card.Suit.CLUBS;
			} else if (valKey == 12 && suitKey > 2) {
				suit = Card.Suit.HEARTS;
			}
			
			//If the card is an ace, it uses the alt value constructor
			if (valKey == 3) {
				cardDeck.add(new Card(cardSprite, suit, value, altVal));
			} else {
				cardDeck.add(new Card(cardSprite, suit, value));
			}
			
			//Moves to the next column of the sprite sheet after reaching the 10th row
			if (yPos >= height * row) {
				yPos = -height;
				xPos += width;
			}
		}
	}
}
