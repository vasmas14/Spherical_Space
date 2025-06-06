package ru.itschool.spherical_space;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class StaticBody {
    public float x, y;
    public float width, height;
    Body body;

    public StaticBody(World world, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        body.createFixture(shape, 0);
        shape.dispose();
    }
    public float getX(){
        return body.getPosition().x - width/2;
    }
    public float getY(){
        return body.getPosition().y - height/2;
    }
    public float getW(){
        return width;
    }
    public float getH(){
        return height;
    }
    public float getR(){
        return body.getAngle()* MathUtils.radiansToDegrees;
    }
}
