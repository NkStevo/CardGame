package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class BlackjackScreen implements Screen {
	private CardGame game;
	
	private SpriteBatch batch;
	private BitmapFont font;
	
	private Texture cardSheet;
	private TextureRegion card;
	private Sprite cardSprite;
	
	private Stage stage;
	private Skin skin;
	
	private TextButton drawCardButton;
	private TextButton standButton;
	
	private OrthographicCamera camera;
	
	private ArrayList<Card> cardDeck;
	
	/**Each ArrayList of Cards is a player's hand
	 * The ArrayList at index 0 is the dealer's hand
	 */
	private Stack<Player> players;
	
	private int cardInd;
	private boolean drawnCard;
	private boolean stand;
	private Card displayCard;
	
	public BlackjackScreen(CardGame game, Stack<Player> players) {
		this.game = game;
		
		batch = game.batch;
		font = game.font;
		
		skin = new Skin();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		BitmapFont buttonFont = new BitmapFont();
		
		skin.add("default", buttonFont);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.pressedOffsetY = -5;
		
		drawCardButton = new TextButton("Draw Card", textButtonStyle);
		standButton = new TextButton("Stand", textButtonStyle);
		
		stage.addActor(drawCardButton);
		stage.addActor(standButton);
		
		this.players = players;
		
		stand = false;
		drawnCard = false;
		
		drawCardButton.setPosition(100, 300);
		standButton.setPosition(100, 260);
		
		drawCardButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Random randGen = new Random();
				
				displayCard = cardDeck.remove(randGen.nextInt(cardDeck.size()));
				drawnCard = true;
				
				System.out.println(displayCard.getSuit());
				System.out.println(displayCard.getValue());
				System.out.println(displayCard.getAltVal(0));
			}
		});
		
		standButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				stand = true;
			}
		});
		
		camera = new OrthographicCamera();
		Random randGen = new Random();
		
		this.loadCards();
		
		System.out.println(randGen.nextInt(cardDeck.size()));
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		boolean turnDone = false;
		
		Gdx.gl.glClearColor(6f/255f, 156f/255f, 36f/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		batch.begin();
		
		if (!players.empty()) {
			turnDone = turn(players.peek());
			
			if (turnDone) {
				players.pop();
				turnDone = false;
			}
		}
		
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

	public boolean turn(Player player) {
		if (drawnCard) {
			player.addToHand(displayCard);
			System.out.println(player.getValue());
			drawnCard = false;
		}
			
		if (player.getValue() > 21 || stand){
			stand = false;
			return true;
		}
			
		game.font.draw(game.batch, new Integer(player.getValue()).toString(), 350, 400);
		game.font.draw(game.batch, player.getName(), 400, 400);
			
		if (displayCard != null) {
			displayCard.getSprite().draw(batch);
		}
		
		return false;
	}
}
