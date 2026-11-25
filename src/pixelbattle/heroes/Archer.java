package pixelbattle.heroes;

import pixelbattle.core.Fighter;
import pixelbattle.core.Hero;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.core.Ultimate;
import pixelbattle.engine.Sprite;

/** Лучниця — швидка й спритна. */
public class Archer extends Hero {

    public Archer() {
        super("Лучниця", 80, new Sprite("elf_f_idle_anim", 4, 4));

        addSkill(new Skill("Постріл", Target.ENEMY, 0, (user, target) ->
                user.getName() + " стріляє в " + target.getName() + ": "
                        + target.takeDamage(random(10, 16)) + " шкоди"));

        // TODO 3: вміння «Подвійний постріл»
        addSkill(new Skill("Подвійний постріл", Target.ENEMY, 1, (user, target) -> {
            int dmg1 = target.takeDamage(random(7, 11));
            int dmg2 = target.isAlive() ? target.takeDamage(random(7, 11)) : 0;
            int total = dmg1 + dmg2;
            return user.getName() + " робить подвійний постріл у " + target.getName() + ": " + total + " шкоди";
        }));

        // TODO 9: ультимейт «Злива стріл» з полем-лічильником
        setUltimate(new Ultimate() {
            private int arrows = 3;

            @Override
            public String getName() {
                return "Злива стріл";
            }

            @Override
            public String getDescription() {
                return "Випускає " + arrows + " стріл(и) по 4–8 шкоди в кожного живого ворога";
            }

            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int currentArrows = arrows++;
                int totalDmg = 0;
                for (Fighter e : enemies) {
                    if (e.isAlive()) {
                        for (int i = 0; i < currentArrows; i++) {
                            if (e.isAlive()) {
                                totalDmg += e.takeDamage(random(4, 8));
                            }
                        }
                    }
                }
                return user.getName() + " випускає зливу з " + currentArrows + " стріл! Загальна шкода: " + totalDmg;
            }
        });
    }

    // TODO 5: пасивка «Ухилення»
    @Override
    protected int modifyIncomingDamage(int damage) {
        return chance(25) ? 0 : damage;
    }
}