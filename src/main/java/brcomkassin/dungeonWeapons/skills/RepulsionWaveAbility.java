package brcomkassin.dungeonWeapons.skills;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.utils.TrigUtils;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class RepulsionWaveAbility implements WeaponAbility {

    @Override
    public void execute(AbilityContext abilityContext) {
        Entity target = abilityContext.target();
        Player player = abilityContext.player();

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 2);
        TrigUtils.spawnCircleParticles(target.getLocation(), 3, 50, dustOptions);
        Vector direction = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
        Vector knockback = direction.multiply(0.8);
        knockback.setY(1.1);

        List<Entity> nearbyEntities = player.getNearbyEntities(8, 5, 8);
        nearbyEntities.forEach(entity -> entity.setVelocity(knockback));

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks++ >= 20 || target.isDead()) {
                    cancel();
                }
                nearbyEntities.forEach(entity ->
                        entity.getWorld().spawnParticle(Particle.DUST, entity.getLocation(), 20, dustOptions));
            }
        }.runTaskTimer(DungeonWeaponsPlugin.getInstance(), 0L, 1L);
    }
}
