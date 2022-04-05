package com.chessplusplus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.chessplusplus.game.views.StartMenuView;
import com.chessplusplus.game.Game;
import com.chessplusplus.game.views.BoardView;

public class ChessPlusPlus extends ApplicationAdapter implements ApplicationListener, InputProcessor {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	SpriteBatch batch;
	Texture img;
	FireBaseInterface _FBIC;
	Game game;
	BoardView boardView;
	ApplicationAdapter screen;
	boolean connected = false;

	public ChessPlusPlus(FireBaseInterface FBIC) {_FBIC = FBIC;}
	
	@Override
	public void create () {
		String gameID = "example-game-123";
		_FBIC.sendInitialState(gameID, "A3B4");
		_FBIC.getGameUpdates(gameID);
		//game = new Game();
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		_FBIC.sendMove(gameID, "1 C5");
		_FBIC.sendMove(gameID, "2 D6");
		_FBIC.getStatus();

		//Gdx.input.setInputProcessor(this);

		boardView = new BoardView(batch);

		//screen = new StartMenu(this);
		//screen.create();
		this.setScreen(new StartMenuView(this));
	}

	public void setScreen(ApplicationAdapter applicationAdapter){
		screen = applicationAdapter;
		screen.create();
	}

	@Override
	public void render () {
		dispose();
		screen.render();
	}
	
	@Override
	public void dispose () {
		screen.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		//game.update();
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
		//game.update();
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

	public boolean getConnected(){
		return this.connected;
	}
	
}
