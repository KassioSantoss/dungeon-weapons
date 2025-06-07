package brcomkassin.dungeonWeapons.listeners;

import brcomkassin.dungeonWeapons.Weapon;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.skills.WeaponAbility;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponListener implements Listener {

    private final WeaponManager weaponManager;

    public WeaponListener(WeaponManager weaponManager) {
        this.weaponManager = weaponManager;
    }

    @EventHandler
    public void onUse(EntityDamageByEntityEvent event) {
        Entity target = event.getEntity();

        if (!(event.getDamager() instanceof Player player)) return;

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (itemInMainHand.isEmpty()) return;

        boolean isWeapon = weaponManager.isWeapon(itemInMainHand);

        ColoredLogger.info("&aIs weapon: " + isWeapon);

        if (isWeapon) {
            Weapon weapon = weaponManager.getWeapon(itemInMainHand);
            AbilityContext abilityContext = new AbilityContext(player, target, weapon);
            ColoredLogger.info("&aAbilities: " + weapon.getAbilities());
            WeaponAbility ability = weapon.getAbilities().getFirst();
            weapon.performSkill(ability, abilityContext);
        }
    }

}
