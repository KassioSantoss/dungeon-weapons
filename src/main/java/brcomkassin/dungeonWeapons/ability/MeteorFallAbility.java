package brcomkassin.dungeonWeapons.ability;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.utils.KCooldownManagerUtils;
import brcomkassin.dungeonWeapons.utils.MeteorFall;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MeteorFallAbility implements WeaponAbility {

    @Override
    public void execute(AbilityContext context) {
        Player player = context.player();
        Location location = context.location();

        if (location == null) {
            location = context.target().getLocation();
        }

        KCooldownManagerUtils.apply(player.getUniqueId(), KCooldownManagerUtils.CooldownType.ABILITY,
                context.weapon().getCurrentAbility().getName(), 1000L * 5L);

        new MeteorFall(player.getWorld(), location, player).runTaskTimer(DungeonWeaponsPlugin.getInstance(), 0L, 1L);
    }

    @Override
    public String getName() {
        return "Meteor_Fall";
    }

    @Override
    public boolean requiresRightClick() {
        return true;
    }

}