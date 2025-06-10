package brcomkassin.dungeonWeapons.ability;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.utils.CooldownUtils;
import brcomkassin.dungeonWeapons.utils.TrigUtils;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class RepulsionWaveAbility implements WeaponAbility {

    @Override
    public void execute(AbilityContext abilityContext) {
        Player player = abilityContext.player();
        WeaponParticleMetadata particleMetadata = abilityContext.weapon().getParticleMetadata();
        Particle.DustOptions dustOptions = new Particle.DustOptions(particleMetadata.getColor(), particleMetadata.getSize());
        TrigUtils.spawnCircleParticles(player.getLocation().add(0, 0.4, 0), 3, 50, dustOptions);

        List<Entity> nearbyEntities = player.getNearbyEntities(4, 3, 4).stream()
                .filter(e -> e instanceof LivingEntity)
                .filter(entity -> entity.getType() != EntityType.PLAYER)
                .filter(entity -> entity.getType() != EntityType.TEXT_DISPLAY)
                .filter(entity -> entity.getType() != EntityType.BLOCK_DISPLAY)
                .filter(entity -> entity.getType() != EntityType.ITEM_DISPLAY)
                .toList();

        CooldownUtils.setCooldown(this.getName() + ":" + player.getName(), 1000L * 5);

        if (nearbyEntities.isEmpty()) return;

        impulse(player, nearbyEntities);

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks++ >= 25 || nearbyEntities.stream().allMatch(Entity::isDead)) {
                    cancel();
                    return;
                }

                nearbyEntities.forEach(entity -> {
                    if (entity.isValid() && !entity.isDead()) {
                        entity.getWorld().spawnParticle(
                                Particle.DUST,
                                entity.getLocation().add(0, 1, 0),
                                5,
                                dustOptions
                        );
                    }
                });
            }
        }.runTaskTimer(DungeonWeaponsPlugin.getInstance(), 1L, 3L);
    }

    private void impulse(Player player, List<Entity> entities) {
        for (Entity entity : entities) {
            Vector playerToEntity = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
            playerToEntity.setY(0.5);
            Vector impulseDirection = playerToEntity.multiply(2);
            entity.setVelocity(impulseDirection);
        }
    }

    @Override
    public String getName() {
        return "Repulsion Wave Ability";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.REPULSION_WAVE;
    }

    @Override
    public boolean requiresRightClick() {
        return true;
    }
}
