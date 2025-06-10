package brcomkassin.dungeonWeapons.ability;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.utils.CooldownUtils;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public class JumpAbility implements WeaponAbility {

    @Override
    public void execute(AbilityContext abilityContext) {
        Entity target = abilityContext.target();
        Player player = abilityContext.player();
        WeaponParticleMetadata particleMetadata = abilityContext.weapon().getParticleMetadata();
        Particle.DustOptions dustOptions = new Particle.DustOptions(particleMetadata.getColor(), particleMetadata.getSize());
        target.setVelocity(new Vector(0, 1.1, 0));
        player.setVelocity(new Vector(0, 1, 0));

        player.sendMessage("Você arremessou o alvo para cima");

        if (target instanceof Player p) {
            p.sendMessage("Você foi arremessado para cima");
        }

        AtomicInteger counter = new AtomicInteger(0);
        CooldownUtils.setCooldown(this.getName() + ":" + player.getName(), 1000L * 5);

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
        return "Jump Ability";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.JUMP;
    }
}
