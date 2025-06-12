package brcomkassin.dungeonWeapons.event;

import brcomkassin.dungeonWeapons.weapon.Weapon;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class WeaponUseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Entity target;
    private final Location location;
    private final Weapon weapon;
    private boolean cancelled;

    public WeaponUseEvent(Player player, Entity target, Location location, Weapon weapon) {
        this.player = player;
        this.target = target;
        this.location = location;
        this.weapon = weapon;
    }

    public WeaponUseEvent(Player player, Entity target, Weapon weapon) {
        this.player = player;
        this.target = target;
        this.location = null;
        this.weapon = weapon;
    }

    public WeaponUseEvent(Player player, Location location, Weapon weapon) {
        this.player = player;
        this.target = null;
        this.location = location;
        this.weapon = weapon;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

