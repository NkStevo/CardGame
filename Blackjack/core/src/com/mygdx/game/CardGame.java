package com.mygdx.game;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardGame extends Game{
	SpriteBatch batch;
	BitmapFont font;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		Stack<Player> players = new Stack<Player>();
		players.push(new Player("Dealer"));
		players.push(new Player("Steve"));
		
		this.setScreen(new BlackjackScreen(this, players));
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
