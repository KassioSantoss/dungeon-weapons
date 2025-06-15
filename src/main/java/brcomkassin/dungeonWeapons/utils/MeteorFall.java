package brcomkassin.dungeonWeapons.utils;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.Collection;

public class MeteorFall extends BukkitRunnable {

    public static final double FALL_START_HEIGHT = 85.0;
    public static final double HORIZONTAL_OFFSET = 50.0;
    public static final double FALL_SPEED_PER_TICK = 1.5;
    public static final double IMPACT_RADIUS = 10.0;
    public static final double DAMAGE_AMOUNT = 20.0;
    public static final float ROTATION_SPEED_DEGREES = 3.0f;
    public static final float METEOR_SCALE = 5.0f;
    private final World world;
    private final Location impactLocation;
    private final Player caster;
    private final Vector fallVector;
    private final Quaternionf lookRotation;
    private final Quaternionf tickSpin;
    private final BlockDisplay meteorDisplay;
    private final Quaternionf cumulativeSpin;
    private final Particle.DustOptions dustOptions;

    public MeteorFall(World world, Location impactLocation, Player caster) {
        this.world = world;
        this.impactLocation = impactLocation.clone();
        this.caster = caster;
        this.dustOptions = new Particle.DustOptions(Color.fromRGB(66, 66, 66), 8);
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
            display.setBlock(new ItemStack(Material.PLAYER_HEAD).getType().createBlockData());

            Transformation initialTransform = display.getTransformation();

            initialTransform.getScale().set(new Vector3f(METEOR_SCALE, METEOR_SCALE, METEOR_SCALE));

            initialTransform.getRightRotation().set(lookRotation);

            display.setTransformation(initialTransform);
            display.setBrightness(new Display.Brightness(15, 15));
            display.setGlowColorOverride(Color.fromRGB(50, 44, 51));
            display.setGlowing(true);
        });
    }

    @Override
    public void run() {
        if (meteorDisplay == null || !meteorDisplay.isValid()) {
            cancel();
            return;
        }

        if (meteorDisplay.getLocation().distanceSquared(impactLocation) < (FALL_SPEED_PER_TICK * FALL_SPEED_PER_TICK)) {
            onImpact();
            Collection<Entity> entities = meteorDisplay.getLocation().getNearbyEntities(IMPACT_RADIUS, IMPACT_RADIUS, IMPACT_RADIUS);
            entities.forEach(entity -> applyRepulsionImpulse(impactLocation, entity));
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
        world.spawnParticle(Particle.EXPLOSION, impactLocation.add(0, 1, 0), 50, 2, 1, 2, 0.01);

        world.getNearbyEntities(impactLocation, IMPACT_RADIUS, IMPACT_RADIUS, IMPACT_RADIUS)
                .stream()
                .filter(entity -> entity instanceof LivingEntity && !entity.getUniqueId().equals(caster.getUniqueId()))
                .forEach(entity -> ((LivingEntity) entity).damage(DAMAGE_AMOUNT, caster));

        Particle.DustOptions options = new Particle.DustOptions(dustOptions.getColor(), 15);
        KTrigUtils.spawnCircleParticles(impactLocation, 4, 50, options);
    }

    private void cleanup() {

        if (meteorDisplay != null && meteorDisplay.isValid()) {

            meteorDisplay.remove();

        }

        this.cancel();

    }


    private void applyRepulsionImpulse(Location center, Entity entity) {
        if (!center.getWorld().equals(entity.getWorld())) return;

        Vector direction = entity.getLocation().toVector().subtract(center.toVector());


        if (direction.lengthSquared() == 0) return;


        direction.normalize();

        double strength = 2.0;

        double verticalBoost = 0.8;


        direction.multiply(strength);

        direction.setY(verticalBoost);

        entity.setVelocity(direction);
        entity.setFireTicks(5 * 20);
    }

}



