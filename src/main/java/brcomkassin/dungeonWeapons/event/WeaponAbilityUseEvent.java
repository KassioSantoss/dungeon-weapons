package brcomkassin.dungeonWeapons.event;

import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@Getter
public class WeaponAbilityUseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Entity target;
    private final Weapon weapon;
    private final Location location;
    private final WeaponAbility ability;
    private boolean cancelled;

    public WeaponAbilityUseEvent(Player player, Entity target, Weapon weapon, Location location, WeaponAbility ability) {
        this.player = player;
        this.target = target;
        this.weapon = weapon;
        this.location = location;
        this.ability = ability;
    }

    public WeaponAbilityUseEvent(Player player, Entity target, Weapon weapon, WeaponAbility ability) {
        this.player = player;
        this.target = target;
        this.weapon = weapon;
        this.location = null;
        this.ability = ability;
    }

    public WeaponAbilityUseEvent(Player player, Weapon weapon, Location location, WeaponAbility ability) {
        this.player = player;
        this.target = null;
        this.weapon = weapon;
        this.location = location;
        this.ability = ability;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
