package brcomkassin.dungeonWeapons;

import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.skills.WeaponAbility;
import brcomkassin.dungeonWeapons.utils.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Weapon {

    protected String name;
    protected String id;
    protected WeaponType type;
    protected List<WeaponAbility> abilities;
    protected ItemStack weaponItem;

    public Weapon(String name, String id, WeaponType type) {
        this.name = name;
        this.id = WeaponIds.PREFIX + id;
        this.type = type;
        this.abilities = new ArrayList<>();
        this.weaponItem = ItemBuilder.of(Material.NETHERITE_SWORD)
                .setName(name)
                .setNamespacedData(WeaponIds.WEAPON_KEY, PersistentDataType.STRING, this.id)
                .build();
    }

    public void addAbility(WeaponAbility ability) {
        this.abilities.add(ability);
    }

    public void removeAbility(WeaponAbility ability) {
        this.abilities.remove(ability);
    }

    public void removeAllAbilities() {
        this.abilities.clear();
    }

    public boolean hasAbility() {
        return !this.abilities.isEmpty();
    }

    public void performSkill(WeaponAbility weaponAbility, AbilityContext context) {
        if (weaponAbility == null || context == null) return;
        if (!hasAbility()) return;
        weaponAbility.execute(context);
    }

}
