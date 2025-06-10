package brcomkassin.dungeonWeapons.listeners;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.Weapon;
import brcomkassin.dungeonWeapons.cache.PlayerAbilityInUseCache;
import brcomkassin.dungeonWeapons.event.WeaponAbilityUseEvent;
import brcomkassin.dungeonWeapons.event.WeaponUseEvent;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.utils.CooldownUtils;
import brcomkassin.dungeonWeapons.utils.DecimalFormatUtil;
import brcomkassin.dungeonWeapons.utils.Message;
import brcomkassin.dungeonWeapons.utils.MessageText;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import java.util.HashSet;
import java.util.Set;

public class WeaponListener implements Listener {

    private final Set<Weapon> register = new HashSet<>();
    private final WeaponManager weaponManager;

    public WeaponListener(WeaponManager weaponManager) {
        this.weaponManager = weaponManager;
    }

    @EventHandler
    public void weaponUse(WeaponUseEvent event) {
        Weapon weapon = event.getWeapon();
        Player player = event.getPlayer();
        Entity target = event.getTarget();

        WeaponAbility ability = weapon.getCurrentAbility();
        WeaponAbilityUseEvent useEvent = new WeaponAbilityUseEvent(player, target, weapon, ability);
        Bukkit.getPluginManager().callEvent(useEvent);
    }

    @EventHandler
    public void activeAbility(WeaponAbilityUseEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getTarget();
        Weapon weapon = event.getWeapon();
        WeaponAbility ability = event.getAbility();

        if (CooldownUtils.isOnCooldown(ability.getName() + ":" + player.getName())) {
            double remaining = (double) CooldownUtils.getRemaining(ability.getName() + ":" + player.getName()) / 1000;
            Message.ActionBar.send(player, "&4Habilidade recarregando: &f" + DecimalFormatUtil.format(remaining) + "s restantes");
            return;
        }
        AbilityContext context = new AbilityContext(player, target, weapon);
        weapon.performSkill(ability, context);
        PlayerAbilityInUseCache.add(ability.getName(), player.getName());
    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.getAction().isRightClick()) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.isEmpty()) return;
        Weapon weapon = weaponManager.getWeapon(player,item);
        if (weapon == null) return;
        WeaponAbility currentAbility = weapon.getCurrentAbility();
        if (currentAbility == null || !currentAbility.requiresRightClick()) return;
        WeaponUseEvent useEvent = new WeaponUseEvent(player, null, weapon);
        Bukkit.getPluginManager().callEvent(useEvent);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.isEmpty()) return;

        Weapon weapon = weaponManager.getWeapon(player, item);

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
        Weapon weapon = weaponManager.getWeapon(player,item);
        if (weapon == null) return;

        weapon.cycleAbility();

        WeaponAbility ability = weapon.getCurrentAbility();
        if (ability == null) {
            Component message = MessageText.create()
                    .text("Você não possui nenhuma habilidade ativa no momento").color(56, 131, 194)
                    .build();
            Message.Chat.send(player, message);
            return;
        }
        Component message = MessageText.create()
                .text("Habilidade ativada: ").color(56, 131, 194)
                .text(ability.getName()).color(78, 166, 240)
                .build();
        Message.ActionBar.send(player, message);
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
