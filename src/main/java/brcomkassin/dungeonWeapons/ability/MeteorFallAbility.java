package brcomkassin.dungeonWeapons.ability;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.utils.KColoredLogger;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.Collection;

public class MeteorFallAbility implements WeaponAbility {

    private static final double FALL_START_HEIGHT = 70.0;
    private static final double HORIZONTAL_OFFSET = 45.0;
    private static final double FALL_SPEED_PER_TICK = 1.3;
    private static final double IMPACT_RADIUS = 5.0;
    private static final double DAMAGE_AMOUNT = 20.0;
    private static final float ROTATION_SPEED_DEGREES = 5.0f;
    private static final float METEOR_SCALE = 3.0f;

    @Override
    public void execute(AbilityContext context) {
        Player player = context.player();
        Location location = context.location();

        if (location == null) {
            location = context.target().getLocation();
        }

        new RefinedMeteorFallAnimation(player.getWorld(), location, player)
                .runTaskTimer(DungeonWeaponsPlugin.getInstance(), 0L, 1L);
    }

    private static class RefinedMeteorFallAnimation extends BukkitRunnable {

        private final World world;
        private final Location impactLocation;
        private final Player caster;
        private final Vector fallVector;
        private final Quaternionf lookRotation;
        private final Quaternionf tickSpin;
        private final BlockDisplay meteorDisplay;
        private final Quaternionf cumulativeSpin;

        public RefinedMeteorFallAnimation(World world, Location impactLocation, Player caster) {
            this.world = world;
            this.impactLocation = impactLocation.clone();
            this.caster = caster;

            double randomAngle = Math.random() * 2 * Math.PI;
            double offsetX = Math.cos(randomAngle) * HORIZONTAL_OFFSET;
            double offsetZ = Math.sin(randomAngle) * HORIZONTAL_OFFSET;
            Location startLocation = this.impactLocation.clone().add(offsetX, FALL_START_HEIGHT, offsetZ);

            this.fallVector = this.impactLocation.toVector()
                    .subtract(startLocation.toVector())
                    .normalize()
                    .multiply(FALL_SPEED_PER_TICK);

            Vector3f fallVector3f = new Vector3f((float) fallVector.getX(), (float) fallVector.getY(), (float) fallVector.getZ());
            this.lookRotation = new Quaternionf().lookAlong(fallVector3f, new Vector3f(0, 1, 0));

            float rads = (float) Math.toRadians(ROTATION_SPEED_DEGREES);
            this.tickSpin = new Quaternionf().rotateAxis(rads, 0.2f, 0.7f, -0.4f).normalize();

            this.cumulativeSpin = new Quaternionf();

            this.meteorDisplay = world.spawn(startLocation, BlockDisplay.class, display -> {
                display.setBlock(new ItemStack(Material.NETHERITE_BLOCK).getType().createBlockData());
                Transformation initialTransform = display.getTransformation();
                initialTransform.getScale().set(new Vector3f(METEOR_SCALE, METEOR_SCALE, METEOR_SCALE));
                initialTransform.getRightRotation().set(lookRotation);
                display.setTransformation(initialTransform);
                display.setBrightness(new Display.Brightness(15, 15));
                display.setGlowColorOverride(Color.fromRGB(50, 44, 51));
                display.setGlowing(true);
            });
        }

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(66, 66, 66), 3);

        @Override
        public void run() {
            if (meteorDisplay == null || !meteorDisplay.isValid()) {
                cancel();
                return;
            }
            if (meteorDisplay.getLocation().distanceSquared(impactLocation) < (FALL_SPEED_PER_TICK * FALL_SPEED_PER_TICK)) {
                onImpact();

                Collection<Entity> entities = meteorDisplay.getLocation().getNearbyEntities(IMPACT_RADIUS, IMPACT_RADIUS, IMPACT_RADIUS);
                entities.forEach(entity -> impulse(impactLocation, entity));
                return;
            }

            meteorDisplay.teleport(meteorDisplay.getLocation().add(fallVector));

            Location currentLocation = meteorDisplay.getLocation();
            world.spawnParticle(Particle.DUST, currentLocation, 5, 0.3, 0.3, 0.3, 0.01, dustOptions);

            cumulativeSpin.mul(tickSpin);
            Quaternionf finalRotation = new Quaternionf(lookRotation).mul(cumulativeSpin);

            Transformation transformation = meteorDisplay.getTransformation();
            transformation.getRightRotation().set(finalRotation);
            meteorDisplay.setTransformation(transformation);

            world.playSound(meteorDisplay.getLocation(), Sound.ENTITY_WARDEN_DIG, 0.5F, 0.5F);
        }

        private void onImpact() {
            cleanup();
            world.createExplosion(impactLocation, 0F, false, false);
            world.playSound(impactLocation, Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 0.7F);
            world.spawnParticle(Particle.LAVA, impactLocation, 150, 0.8, 0.5, 0.8, 0);
            world.spawnParticle(Particle.EXPLOSION, impactLocation, 50, 2, 1, 2, 0.01);
            world.getNearbyEntities(impactLocation, IMPACT_RADIUS, IMPACT_RADIUS, IMPACT_RADIUS)
                    .stream()
                    .filter(entity -> entity instanceof LivingEntity && !entity.getUniqueId().equals(caster.getUniqueId()))
                    .forEach(entity -> ((LivingEntity) entity).damage(DAMAGE_AMOUNT, caster));
        }

        private void cleanup() {
            if (meteorDisplay != null && meteorDisplay.isValid()) {
                meteorDisplay.remove();
            }
            this.cancel();
        }

        private void impulse(Location center, Entity entity) {
            Vector direction = center.toVector().subtract(entity.getLocation().toVector()).normalize();
            direction.setY(0.5);
            Vector impulse = direction.multiply(2);
            entity.setVelocity(impulse);
            entity.setFireTicks(5 * 20);
        }

    }

    @Override
    public String getName() {
        return "Meteor Fall";
    }

    @Override
    public boolean requiresRightClick() {
        return true;
    }

    @Override
    public AbilityType getType() {
        return AbilityType.METEOR_FALL;
    }
}