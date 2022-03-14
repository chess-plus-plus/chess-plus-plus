package com.chessplusplus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class ChessPlusPlus extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	FireBaseInterface _FBIC;

	public ChessPlusPlus(FireBaseInterface FBIC) {_FBIC = FBIC;}
	
	@Override
	public void create () {
		_FBIC.Status();
		_FBIC.FirstFireBaseTest();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
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
}
