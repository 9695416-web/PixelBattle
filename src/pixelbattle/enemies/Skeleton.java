package pixelbattle.enemies;

import pixelbattle.core.Enemy;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.engine.Sprite;

/** Скелет — повстає після першого смертельного удару. */
public class Skeleton extends Enemy {

    private boolean resurrected = false;

    public Skeleton() {
        super("Скелет", 50, new Sprite("skelet_idle_anim", 4, 6));

        addSkill(new Skill("Кістяний удар", Target.ENEMY, 0, (user, target) ->
                user.getName() + " завдає кістяного удару " + target.getName() + ": "
                        + target.takeDamage(random(8, 12)) + " шкоди"));
    }

    // Пасивка «Воскресіння»: перший удар, який мав би вбити скелета, залишає йому 1 HP
    @Override
    protected int modifyIncomingDamage(int damage) {
        if (!resurrected && damage >= getHp()) {
            resurrected = true;
            return Math.max(0, getHp() - 1);
        }
        return damage;
    }
}