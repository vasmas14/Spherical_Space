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

public class ScrSettings implements Screen {
    private Main main;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;
    private BitmapFont font;

    Texture img;

    Button bBack;

    public ScrSettings(Main main) {
        this.main = main;
        batch = main.batch;
        camera = main.camera;
        touch = main.touch;
        font = main.font;
        img = new Texture("bg2.jpg");
        bBack = new Button(font, "X", 870, 1600);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (bBack.hit(touch.x, touch.y)){
                main.setScreen(main.scrMenu);
            }
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0 , 0, SCR_WIDTH, SCR_HEIGHT);
        font.draw(batch, "GAME", 400, 1500);
        bBack.f.draw(batch, bBack.text, bBack.x, bBack.y);
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
