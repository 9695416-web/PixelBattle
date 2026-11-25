package pixelbattle.enemies;

import pixelbattle.core.Enemy;
import pixelbattle.core.Fighter;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.core.Ultimate;
import pixelbattle.engine.Sprite;

public class BigDemon extends Enemy {

    public BigDemon() {
        super("Демон", 140, new Sprite("big_demon_idle_anim", 4, 4));

        addSkill(new Skill("Кігті хаосу", Target.ENEMY, 0, (user, target) ->
                user.getName() + " рве кігтями " + target.getName() + ": "
                        + target.takeDamage(random(11, 16)) + " шкоди"));

        addSkill(new Skill("Пекельний подих", Target.ENEMY, 2, (user, target) -> {
            int dmg = target.takeDamage(random(18, 24));
            return user.getName() + " обпалює " + target.getName() + ": " + dmg + " шкоди";
        }));

        setUltimate(new Ultimate() {
            @Override
            public String getName() {
                return "Вибух безодні";
            }

            @Override
            public String getDescription() {
                return "Завдає 14 шкоди всім живим героям";
            }

            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int total = 0;
                for (Fighter hero : enemies) {
                    if (hero.isAlive()) {
                        total += hero.takeDamage(14);
                    }
                }
                return user.getName() + " активує Вибух безодні! Загальна шкода: " + total;
            }
        });
    }
}