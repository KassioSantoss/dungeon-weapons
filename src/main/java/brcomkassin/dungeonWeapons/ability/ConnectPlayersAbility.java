package brcomkassin.dungeonWeapons.ability;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.utils.KCooldownManagerUtils;
import brcomkassin.dungeonWeapons.utils.KTrigUtils;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ConnectPlayersAbility implements WeaponAbility {

    @Override
    public void execute(AbilityContext context) {
        Player player = context.player();
        Entity target = context.target();
        WeaponParticleMetadata particleMetadata = context.weapon().getParticleMetadata();

        KCooldownManagerUtils.apply(player.getUniqueId(), KCooldownManagerUtils.CooldownType.ABILITY,
                context.weapon().getCurrentAbility().getName(), 1000L * 5L);

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks++ >= 20 * 20 || player.isDead() || target.isDead()) {
                    cancel();
                    return;
                }
                KTrigUtils.drawParticleLine(player, target, particleMetadata, 0.5);
                ticks++;
            }
        }.runTaskTimer(DungeonWeaponsPlugin.getInstance(), 0, 1);
    }

    @Override
    public String getName() {
        return "Connect_Players";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public boolean requiresRightClick() {
        return true;
    }

}
