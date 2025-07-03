package brcomkassin.dungeonWeapons.listeners;

import brcomkassin.dungeonWeapons.utils.KCooldownManagerUtils;
import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.utils.*;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.cache.PlayerAbilityInUseCache;
import brcomkassin.dungeonWeapons.event.WeaponAbilityUseEvent;
import brcomkassin.dungeonWeapons.event.WeaponUseEvent;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class WeaponListener implements Listener {

    private final WeaponManager weaponManager;

    public WeaponListener(WeaponManager weaponManager) {
        this.weaponManager = weaponManager;
    }

    @EventHandler
    public void weaponUse(WeaponUseEvent event) {
        Weapon weapon = event.getWeapon();
        Player player = event.getPlayer();
        Entity target = event.getTarget();
        Location location = event.getLocation();
        WeaponAbility ability = weapon.getCurrentAbility();

        WeaponAbilityUseEvent useEvent = new WeaponAbilityUseEvent(player, target, weapon, location, ability);
        Bukkit.getPluginManager().callEvent(useEvent);
    }

    @EventHandler
    public void activeAbility(WeaponAbilityUseEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getTarget();
        Weapon weapon = event.getWeapon();
        WeaponAbility ability = event.getAbility();

        if (KCooldownManagerUtils.isOnCooldown(player.getUniqueId(), KCooldownManagerUtils.CooldownType.ABILITY, ability.getName())) {
            double remaining = KCooldownManagerUtils.getRemaining(player.getUniqueId(),
                    KCooldownManagerUtils.CooldownType.ABILITY, ability.getName()) / 1000.0;
            KMessage.ActionBar.send(player, "&4Habilidade recarregando: &f" + KDecimalFormatUtil.format(remaining) + "s restantes");
            return;
        }

        AbilityContext context = new AbilityContext(player, target, weapon, event.getLocation());
        weapon.performSkill(ability, context);
        PlayerAbilityInUseCache.add(ability.getName(), player.getName());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!event.getAction().isRightClick()) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.isEmpty()) return;
        Weapon weapon = weaponManager.getWeapon(item);
        if (weapon == null) return;

        WeaponAbility currentAbility = weapon.getCurrentAbility();
        if (currentAbility == null || !currentAbility.requiresRightClick()) return;

        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        Location location = KTrigUtils.getTargetLocation(player, 200);
        WeaponUseEvent useEvent = new WeaponUseEvent(player, location, weapon);
        Bukkit.getPluginManager().callEvent(useEvent);
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getHand() != EquipmentSlot.HAND) return;

        if (item.isEmpty()) return;

        Weapon weapon = weaponManager.getWeapon(item);
        if (weapon == null) return;

        WeaponAbility currentAbility = weapon.getCurrentAbility();
        if (currentAbility == null || !currentAbility.requiresRightClick() || !currentAbility.requiresTarget()) return;

        WeaponUseEvent useEvent = new WeaponUseEvent(player, target, weapon);
        Bukkit.getPluginManager().callEvent(useEvent);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.isEmpty()) return;

        Weapon weapon = weaponManager.getWeapon(item);

        if (weapon == null) return;
        WeaponAbility currentAbility = weapon.getCurrentAbility();
        if (currentAbility == null || currentAbility.requiresRightClick()) return;

        if (PlayerAbilityInUseCache.contains(currentAbility.getName(), player.getName())) {
            impulse(player, event.getEntity());
            Bukkit.getScheduler().runTaskLater(DungeonWeaponsPlugin.getInstance(), () -> {
                PlayerAbilityInUseCache.remove(currentAbility.getName(), player.getName());
            }, 20 * 2);
            return;
        }
        event.setCancelled(true);
        Entity target = event.getEntity();
        WeaponUseEvent weaponUseEvent = new WeaponUseEvent(player, target, weapon);
        Bukkit.getPluginManager().callEvent(weaponUseEvent);
    }

    @EventHandler
    public void onChange(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.isEmpty() || !weaponManager.isWeapon(item)) return;
        event.setCancelled(true);
        Weapon weapon = weaponManager.getWeapon(item);
        if (weapon == null) return;

        weapon.cycleAbility();

        WeaponAbility ability = weapon.getCurrentAbility();
        if (ability == null) {
            Component message = KMessageText.create()
                    .text("Você não possui nenhuma habilidade ativa no momento").color(56, 131, 194)
                    .build();

            KMessage.Chat.send(player, message);
            return;
        }
        Component message = KMessageText.create().text("Habilidade ativa: ")
                .color(0, 138, 255)
                .text(ability.getName()).color(217, 171, 0)
                .build();

        KMessage.ActionBar.send(player, message);
    }

    private void impulse(Player player, Entity entity) {
        Vector direction = entity.getLocation().toVector()
                .subtract(player.getLocation().toVector())
                .normalize()
                .multiply(1.5);
        direction.setY(0.2);
        entity.setVelocity(direction);
    }

}
