package com.chessplusplus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.chessplusplus.game.BoardFactory;
import com.chessplusplus.game.ChessGame;
import com.chessplusplus.game.ChessGameImpl;
import com.chessplusplus.game.views.StartMenuView;

public class ChessPlusPlus extends ApplicationAdapter implements ApplicationListener, InputProcessor {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	SpriteBatch batch;

	ApplicationAdapter screen;
	FirebaseController FBC;

	ChessGame game;

	public ChessPlusPlus(FireBaseInterface FBIC) {FBC = new FirebaseController(FBIC);}
	
	@Override
	public void create () {
		//game = new Game();
		batch = new SpriteBatch();

		String playerId1 = "1";
		String playerId2 = "2";
		game = new ChessGameImpl(
				BoardFactory.standardBoardAndPieces(playerId1, playerId2),
				playerId1, playerId2);

		Gdx.input.setInputProcessor(this);

		this.setScreen(new StartMenuView(this));
	}

	public void setScreen(ApplicationAdapter applicationAdapter){
		if (screen != null) {
			screen.dispose();
		}
		screen = applicationAdapter;
		screen.create();
	}

	@Override
	public void render () {

		ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
		batch.begin();
		screen.render();
		batch.end();


		/*
		dispose();
		screen.render();*/
	}
	
	@Override
	public void dispose () {
		screen.dispose();

		batch.dispose();
		/*
		img.dispose();
		 */
	}

	@Override
	public boolean keyDown(int keycode) {
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

	public boolean isConnected(){
		return this.FBC.pingEcho();
	}

	public SpriteBatch getBatch(){
		return this.batch;
	}

}
