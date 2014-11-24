package com.sample.box.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class PositionedFixture {

    Vector2 point;
    Fixture fixture;

    public PositionedFixture(Vector2 point, Fixture fixture) {
        this.point = point;
        this.fixture = fixture;
    }

    public Vector2 getPoint() {
        return point;
    }

    public void setPoint(Vector2 point) {
        this.point = point;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }
}
