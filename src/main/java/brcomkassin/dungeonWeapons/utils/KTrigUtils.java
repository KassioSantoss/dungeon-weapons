package brcomkassin.dungeonWeapons.utils;

import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public final class KTrigUtils {

    private KTrigUtils() {
        // Utility class — no instantiation
    }

    /**
     * Gera pontos em uma circunferência no plano XZ (horizontal), no centro fornecido.
     *
     * @param center Localização central (Y é fixo)
     * @param radius Raio da circunferência
     * @param points Quantidade de pontos
     * @return Lista de Location correspondentes aos pontos
     */
    public static List<Location> generateCircle(Location center, double radius, int points) {
        List<Location> locations = new ArrayList<>(points);
        double increment = 2 * Math.PI / points;

        for (int i = 0; i < points; i++) {
            double angle = i * increment;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            Location loc = new Location(center.getWorld(), x, center.getY(), z);
            locations.add(loc);
        }

        return locations;
    }

    public static void spawnCircleParticles(Location center, double radius, int points, Particle particle) {
        double increment = 2 * Math.PI / points;

        for (int i = 0; i < points; i++) {
            double angle = i * increment;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);

            Location loc = new Location(center.getWorld(), x, center.getY(), z);
            center.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
        }
    }

    public static void spawnCircleParticles(Location center, double radius, int points, Particle.DustOptions dustOptions) {
        double increment = 2 * Math.PI / points;
        Particle particle = Particle.DUST;
        for (int i = 0; i < points; i++) {
            double angle = i * increment;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);

            Location loc = new Location(center.getWorld(), x, center.getY(), z);
            center.getWorld().spawnParticle(particle, loc, 1, dustOptions);
        }
    }

    /**
     * Gera uma espiral ascendente.
     *
     * @param center Localização central (base da espiral)
     * @param radius Raio da espiral
     * @param height Altura total da espiral
     * @param coils  Número de voltas (coils)
     * @param points Quantidade total de pontos
     * @return Lista de Location correspondentes aos pontos da espiral
     */
    public static List<Location> generateSpiral(Location center, double radius, double height, double coils, int points) {
        List<Location> locations = new ArrayList<>(points);

        double heightStep = height / points;
        double angleStep = coils * 2 * Math.PI / points;

        for (int i = 0; i < points; i++) {
            double angle = i * angleStep;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            double y = center.getY() + i * heightStep;

            Location loc = new Location(center.getWorld(), x, y, z);
            locations.add(loc);
        }

        return locations;
    }

    /**
     * Retorna um vetor unitário na direção de um ângulo em radianos (plano XZ).
     *
     * @param angleRads Ângulo em radianos
     * @return Vector unitário
     */
    public static Vector getUnitVector(double angleRads) {
        return new Vector(Math.cos(angleRads), 0, Math.sin(angleRads));
    }

    /**
     * Converte graus em radianos.
     */
    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * Converte radianos em graus.
     */
    public static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }

    /**
     * Calcula a distância 2D (plano XZ) entre dois pontos.
     */
    public static double distance2D(Location a, Location b) {
        double dx = a.getX() - b.getX();
        double dz = a.getZ() - b.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * Calcula a distância 3D entre dois pontos.
     */
    public static double distance3D(Location a, Location b) {
        return a.distance(b);
    }

    public static void drawParticleLine(Entity entity, Entity target, WeaponParticleMetadata particle, double spacing) {
        Location startPoint = entity.getLocation().add(0, 1, 0);
        Location endPoint = target.getLocation().add(0, 1, 0);
        World world = startPoint.getWorld();

        Particle.DustOptions dustOptions = new Particle.DustOptions(particle.getColor(), 1);

        if (world == null || !world.equals(endPoint.getWorld())) {
            return;
        }

        double distance = startPoint.distance(endPoint);

        if (distance < spacing) {
            return;
        }

        double increment = spacing / distance;

        for (double t = 0; t <= 1.0; t += increment) {
            double x = startPoint.getX() * (1 - t) + endPoint.getX() * t;
            double y = startPoint.getY() * (1 - t) + endPoint.getY() * t;
            double z = startPoint.getZ() * (1 - t) + endPoint.getZ() * t;

            Location particleLocation = new Location(world, x, y, z);

            world.spawnParticle(particle.getType(), particleLocation, 1, 0, 0, 0, 0, dustOptions);
        }
    }

    /**
     * Obtem a localização do alvo com base na localização do jogador e na distância máxima.
     */
    public static Location getTargetLocation(Player player, double maxRange) {
        World world = player.getWorld();
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult result = world.rayTraceBlocks(
                eyeLocation,
                direction,
                maxRange,
                FluidCollisionMode.NEVER,
                true
        );

        Location targetLocation;

        if (result != null && result.getHitBlock() != null) {
            Block hitBlock = result.getHitBlock();
            targetLocation = hitBlock.getLocation();
        } else {

            Location endPoint = eyeLocation.add(direction.multiply(maxRange));
            targetLocation = world.getHighestBlockAt(endPoint).getLocation();
        }
        return targetLocation.add(0.5, 0, 0.5);
    }

}
