package com.chessplusplus.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chessplusplus.ChessPlusPlus;

public class ManualView extends ApplicationAdapter {

    private ChessPlusPlus chessPlusPlus;
    private Stage stage;
    private Skin skin;
    private TextButton conBut;

    private GameView gameView;
    private boolean isFromGame;

    public ManualView(ChessPlusPlus c){
        chessPlusPlus = c;
        isFromGame = false;
    }

    public ManualView(ChessPlusPlus c, GameView g){
        chessPlusPlus = c;
        gameView = g;
        isFromGame = true;
    }

    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        skin.getFont("default-font").getData().setScale(3,3);


        final TextButton connectedButton = new TextButton("", skin, "default");
        connectedButton.setDisabled(true);
        connectedButton.align(Align.center);
        connectedButton.setWidth(stage.getWidth());
        conBut = connectedButton;


        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.align(Align.center);
        table.setPosition(0, 0);


        final TextField titleField = new TextField("Manual", skin, "default");
        titleField.setDisabled(true);
        titleField.setAlignment(Align.center);

        final Label descriptionLabel = new Label("This game is played with the normal rules of Chess, with a few additions. Whenever a piece performs an action (such as moving) it receives XP (experience points). When a piece other than the Queen and the King reaches a certain amount of XP it levels up and gains a new ability. These pieces can level up and gain new abilities twice. The Queen and the King have abilities which functions a bit differently. This page explains each piece’s additional abilities.", skin);
        descriptionLabel.setWrap(true);


        final TextField pawnTitle = new TextField("Pawn", skin, "default");
        pawnTitle.setDisabled(true);
        pawnTitle.setAlignment(Align.center);

        final Label pawnLabel = new Label("Level 1: The Pawn gains the ability to move one step backwards, in addition to any other available moves. \n" +
                "\n" +
                "Level 2: The Pawn gains the ability to move one step in any direction and may also strike a piece in any direction. ", skin);
        pawnLabel.setWrap(true);


        final TextField Title = new TextField("", skin, "default");
        Title.setDisabled(true);
        Title.setAlignment(Align.center);

        final Label Label = new Label("", skin);
        Label.setWrap(true);


        final TextField rookTitle = new TextField("Rook", skin, "default");
        rookTitle.setDisabled(true);
        rookTitle.setAlignment(Align.center);

        final Label rookLabel = new Label("Level 1: The Rook gains the ability to move off the board and continue its move from the opposite side, following any other rules for moving and striking. \n" +
                "\n" +
                "Level 2: The Rook gains the ability to strike multiple pieces at the same time given that they are next to each other, following any other rules for moving and striking. ", skin);
        rookLabel.setWrap(true);


        final TextField knightTitle = new TextField("Knight", skin, "default");
        knightTitle.setDisabled(true);
        knightTitle.setAlignment(Align.center);

        final Label knightLabel = new Label("Level 1: The Knight gains the ability to move one step in any direction, in addition to any other available moves. \n" +
                "\n" +
                "Level 2: The Knight gains the ability to strike a piece in a movable square, without moving into it itself. ", skin);
        knightLabel.setWrap(true);


        final TextField bishopTitle = new TextField("Bishop", skin, "default");
        bishopTitle.setDisabled(true);
        bishopTitle.setAlignment(Align.center);

        final Label bishopLabel = new Label("Level 1: The Bishop gains the ability to move one step horizontally or vertically, in addition to any other available moves. \n" +
                "\n" +
                "Level 2: The Bishop gains the ability to move over allied pieces, following any other rules for moving and striking. ", skin);
        bishopLabel.setWrap(true);


        final TextField queenTitle = new TextField("Queen", skin, "default");
        queenTitle.setDisabled(true);
        queenTitle.setAlignment(Align.center);

        final Label queenLabel = new Label("Whenever the Queen reaches X xp, the player may put another Pawn on the board. These Pawns are put where Pawns normally start the game. If none of these spaces are free, this ability does nothing. ", skin);
        queenLabel.setWrap(true);


        final TextField kingTitle = new TextField("King", skin, "default");
        kingTitle.setDisabled(true);
        kingTitle.setAlignment(Align.center);

        final Label kingLabel = new Label("Whenever the King strikes another piece, it gives any allied pieces within X spaces X xp. ", skin);
        kingLabel.setWrap(true);


        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (isFromGame) {
                    chessPlusPlus.setScreen(gameView);
                } else {
                    chessPlusPlus.setScreen(new StartMenuView(chessPlusPlus));
                }
            }
        });


        table.add(titleField).width((float) (stage.getWidth()*0.8)).padTop(50);
        table.row();
        table.add(descriptionLabel).padBottom(50).width((float) (stage.getWidth()*0.8));
        table.row();

        table.add(pawnTitle).width((float) (stage.getWidth()*0.8));
        table.row();
        table.add(pawnLabel).padBottom(50).width((float) (stage.getWidth()*0.8));
        table.row();

        table.add(rookTitle).width((float) (stage.getWidth()*0.8));
        table.row();
        table.add(rookLabel).padBottom(50).width((float) (stage.getWidth()*0.8));
        table.row();

        table.add(knightTitle).width((float) (stage.getWidth()*0.8));
        table.row();
        table.add(knightLabel).padBottom(50).width((float) (stage.getWidth()*0.8));
        table.row();

        table.add(bishopTitle).width((float) (stage.getWidth()*0.8));
        table.row();
        table.add(bishopLabel).padBottom(50).width((float) (stage.getWidth()*0.8));
        table.row();

        table.add(queenTitle).width((float) (stage.getWidth()*0.8));
        table.row();
        table.add(queenLabel).padBottom(50).width((float) (stage.getWidth()*0.8));
        table.row();

        table.add(kingTitle).width((float) (stage.getWidth()*0.8));
        table.row();
        table.add(kingLabel).padBottom(50).width((float) (stage.getWidth()*0.8));
        table.row();

        table.add(backButton).width((float) (stage.getWidth()*0.8)).padBottom(50);

        final ScrollPane scroller = new ScrollPane(table);
        final Table scrollTable = new Table();
        scrollTable.setFillParent(true);
        scrollTable.add(scroller).fill().expand();

        //stage.addActor(connectedButton);
        //stage.addActor(table);
        stage.addActor(scrollTable);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {

        if (chessPlusPlus.isConnected()){
            conBut.setText("Connected");
            conBut.setColor(Color.GREEN);
        } else {
            conBut.setText("Not Connected");
            conBut.setColor(Color.RED);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
