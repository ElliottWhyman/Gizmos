package me.itselliott.gizmos.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

/**
 * Created by Elliott on 05/12/2015.
 */
public class ItemBuilder {

    private ItemStack itemStack;
    private Material material;
    private int amount = 1;
    private short damage = 0;
    private String name;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setDamage(short damage) {
        this.damage = damage;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemStack createItem() {
        this.itemStack = new ItemStack(this.material, this.amount, this.damage);
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (this.name != null)
            itemMeta.setDisplayName(this.name);
        if (this.lore != null)
            itemMeta.setLore(this.lore);
        if (this.enchantments != null) {
            for (Enchantment enchantment : this.enchantments.keySet()) {
                itemMeta.addEnchant(enchantment, this.enchantments.get(enchantment), true);
            }
        }
        this.itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }

}