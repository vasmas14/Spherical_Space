package ru.itschool.spherical_space;

import static ru.itschool.spherical_space.Main.SCR_HEIGHT;
import static ru.itschool.spherical_space.Main.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ScrMenu implements Screen {
    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;

    Texture img;

    Button bPlay;
    Button bSettings;
    Button bLeaderboard;
    Button bAbout;
    Button bQuit;

    public ScrMenu(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        img = new Texture("bg1.jpg");
        bPlay = new Button(font, ">Play", 20, 700);
        bSettings = new Button(font, ">Settings", 20, 500);
        bLeaderboard = new Button(font, ">Leaderboard", 20, 300);
        bAbout = new Button(font, ">About", 20, 100);
        bQuit = new Button(font, ">Quit game", 1150, 50);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (bPlay.hit(touch.x, touch.y)){
                main.setScreen(main.scrGame);
            }
            if (bSettings.hit(touch.x, touch.y)){
                main.setScreen(main.scrSettings);
            }
            if (bLeaderboard.hit(touch.x, touch.y)){
                main.setScreen(main.scrLeaderboard);
            }
            if (bAbout.hit(touch.x, touch.y)){
                main.setScreen(main.scrAbout);
            }
            if (bQuit.hit(touch.x, touch.y)){
                Gdx.app.exit();
            }
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0 , 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch, "MAIN MENU", 650, 800);
        bPlay.f.draw(batch, bPlay.text, bPlay.x, bPlay.y);
        bSettings.f.draw(batch, bSettings.text, bSettings.x, bSettings.y);
        bLeaderboard.f.draw(batch, bLeaderboard.text, bLeaderboard.x, bLeaderboard.y);
        bAbout.f.draw(batch, bAbout.text, bAbout.x, bAbout.y);
        bQuit.f.draw(batch, bQuit.text, bQuit.x, bQuit.y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
