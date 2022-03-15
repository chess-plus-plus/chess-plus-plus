package com.chessplusplus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.chessplusplus.game.Game;

public class ChessPlusPlus extends ApplicationAdapter implements ApplicationListener, InputProcessor {
	SpriteBatch batch;
	Texture img;
	FireBaseInterface _FBIC;
	Game game;

	public ChessPlusPlus(FireBaseInterface FBIC) {_FBIC = FBIC;}
	
	@Override
	public void create () {
		_FBIC.Status();
		_FBIC.FirstFireBaseTest();
		game = new Game();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		game.update();
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		game.update();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
