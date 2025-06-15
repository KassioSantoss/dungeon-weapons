package brcomkassin.dungeonWeapons.ability;

import brcomkassin.dungeonWeapons.utils.KCooldownManagerUtils;
import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public class JumpAbility implements WeaponAbility {

    @Override
    public void execute(AbilityContext context) {
        Entity target = context.target();
        Player player = context.player();
        WeaponParticleMetadata particleMetadata = context.weapon().getParticleMetadata();
        Particle.DustOptions dustOptions = new Particle.DustOptions(particleMetadata.getColor(), particleMetadata.getSize());
        target.setVelocity(new Vector(0, 1.1, 0));
        player.setVelocity(new Vector(0, 1, 0));

        player.sendMessage("Você arremessou o alvo para cima");

        if (target instanceof Player p) {
            p.sendMessage("Você foi arremessado para cima");
        }

        AtomicInteger counter = new AtomicInteger(0);

        KCooldownManagerUtils.apply(player.getUniqueId(), KCooldownManagerUtils.CooldownType.ABILITY,
                context.weapon().getCurrentAbility().getName(), 1000L * 5L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (counter.get() == 20) {
                    this.cancel();
                    return;
                }
                target.getWorld().spawnParticle(particleMetadata.getType(), target.getLocation(), 1, 0, 0, 0, 0, dustOptions);
                counter.getAndAdd(1);
            }
        }.runTaskTimer(DungeonWeaponsPlugin.getInstance(), 0, 2);
    }

    @Override
    public String getName() {
        return "Jump_Ability";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

}
