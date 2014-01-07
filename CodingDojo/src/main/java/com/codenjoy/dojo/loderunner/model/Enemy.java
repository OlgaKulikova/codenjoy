package com.codenjoy.dojo.loderunner.model;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.Tickable;

/**
 * User: sanja
 * Date: 07.01.14
 * Time: 16:00
 */
public class Enemy extends PointImpl implements Tickable, Fieldable {

    private Direction direction;
    private EnemyAI ai;
    private Field field;

    public Enemy(Point pt, Direction direction, EnemyAI ai) {
        super(pt);
        this.direction = direction;
        this.ai = ai;
    }

    @Override
    public void init(Field field) {
        this.field = field;
    }

    @Override
    public void tick() {
        if (isFall()) {
            move(x, y - 1);
        } else {
            Direction direction = ai.getDirection(field);
            if (direction != null) {
                this.direction = direction;
                move(direction.change(this));
            }
        }
    }

    public boolean isFall() {
        return field.isPit(x, y);
//                && !field.isPipe(x, y) && !field.isLadder(x, y); // TODO продолжить тут
    }

    public Enum state() {
        if (field.isBrick(x, y)) {
            return Elements.ENEMY_PIT;
        }

        if (direction.equals(Direction.LEFT)) {
            return Elements.ENEMY_LEFT;
        }
//        if (direction.equals(Direction.RIGHT)) { // TODO продолжить тут
            return Elements.ENEMY_RIGHT;
//        }
    }
}
