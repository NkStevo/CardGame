package com.mygdx.game;

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
	CardGame game;
	
	SpriteBatch batch;
	BitmapFont font;
	
	Texture cardSheet;
	TextureRegion card;
	Sprite cardSprite;
	
	private OrthographicCamera camera;
	
	public BlackjackScreen(CardGame game) {
		this.game = game;
		
		batch = game.batch;
		font = game.font;
		
		camera = new OrthographicCamera();
		
		//cards are 140x190
		cardSheet = new Texture(Gdx.files.internal("spritesheets\\playingCards.png"));
		card = new TextureRegion(cardSheet, 0, 0, 140, 190);
		cardSprite = new Sprite(card);
		
		cardSprite.setScale(0.5f);
		cardSprite.setCenterX(200);
		cardSprite.setCenterY(200);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(6f/255f, 156f/255f, 36f/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		cardSprite.draw(batch);
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

}
