package me.UdeCCraft.UdeCKITS;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin implements Listener{
	
	public static Inventory kits; 

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		createInventory();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
	private void createInventory() {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Kits");
		ItemStack item =  new ItemStack(Material.WHITE_WOOL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "Kit Mechon");
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(ChatColor.WHITE + "Click aquí para obtener el kit!");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		inv.setItem(2, item);
		
		item.setType(Material.BLUE_WOOL);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Kit Alpha Tester");
		item.setItemMeta(meta);
		inv.setItem(3, item);
		
		item.setType(Material.IRON_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Kit VIP");
		item.setItemMeta(meta);
		inv.setItem(4, item);
		
		item.setType(Material.GOLD_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GREEN + "Kit VIP+");
		item.setItemMeta(meta);
		inv.setItem(5, item);
		
		item.setType(Material.DIAMOND_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Kit MVP");
		item.setItemMeta(meta);
		inv.setItem(6, item);
		
		kits = inv;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("Kits")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "La consola no puede obtener Kits!");
				return true;
			}
			Player player = (Player) sender;
			player.openInventory(kits);
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().contains("Kits"))
			return;
		if (event.getCurrentItem() == null)
			return;
		if (event.getCurrentItem().getItemMeta() == null)
			return;
		
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		
		if (event.getClickedInventory().getType() == InventoryType.PLAYER)
			return;
		
		if (event.getSlot() == 2) {
			
			if (!player.hasPermission("kits.mechon")) {
				player.sendMessage(ChatColor.DARK_RED + "Necesitas ser MECHON para obtener este kit!");
				return;
			}
			
			this.dropChest(player, this.getKitMechon());
			player.closeInventory();
			player.updateInventory();
			return;
	    }

		
		if (event.getSlot() == 3) {
			
			if (!player.hasPermission("kits.alpha")) {
				player.sendMessage(ChatColor.DARK_RED + "Tienes que haber sido ALPHA TESTER para obtener este kit!");
				return;
			}
			
			this.dropChest(player, this.getKitAlpha());
			player.closeInventory();
			player.updateInventory();
			return;
	    }

		
		if (event.getSlot() == 4) {
			
			if (!player.hasPermission("kits.vip")) {
				player.sendMessage(ChatColor.DARK_RED + "Necesitas ser VIP para obtener este kit!");
				return;
			}
			
			this.dropChest(player, this.getKitVIP());
			player.closeInventory();
			player.updateInventory();
			return;
	    }

		
		if (event.getSlot() == 5) {
			
			if (!player.hasPermission("kits.vip2")) {
				player.sendMessage(ChatColor.DARK_RED + "Necesitas ser VIP+ para obtener este kit!");
				return;
			}
			
			this.dropChest(player, this.getKitVIP2());
			player.closeInventory();
			player.updateInventory();
			return;
	    }

		
		if (event.getSlot() == 6) {
			
			if (!player.hasPermission("kits.mvp")) {
				player.sendMessage(ChatColor.DARK_RED + "Necesitas ser MVP para obtener este kit!");
				return;
			}
			
			this.dropChest(player, this.getKitMVP());
			player.closeInventory();
			player.updateInventory();
			return;
	    }
	}
	
	private void dropChest(Player player, ItemStack[] items) {
		Location chest = null;
		if(player.getFacing() == BlockFace.NORTH)
			chest = player.getLocation().add(0,0,-1);
		if(player.getFacing() == BlockFace.WEST)
			chest = player.getLocation().add(-1,0,0);
		if(player.getFacing() == BlockFace.SOUTH)
			chest = player.getLocation().add(0,0,1);
		if(player.getFacing() == BlockFace.EAST)
			chest = player.getLocation().add(1,0,0);
		
		if(chest.getBlock().getType()  != Material.AIR) {
			player.sendMessage(ChatColor.DARK_RED + "No puedes obtener el kit aquí, algo obstruye el cofre!");
			return;
		}
		
		Block block = chest.getBlock();
		block.setType(Material.CHEST);
		Chest c = (Chest) block.getState();
		c.getInventory().addItem(items);
				
	}
	
	private ItemStack[] getKitMechon() {
		ItemStack[] items = {new ItemStack(Material.STONE_SWORD),
				             new ItemStack(Material.STONE_PICKAXE),
				             new ItemStack(Material.BREAD,16),
				             new ItemStack(Material.LEATHER_HELMET),
				             new ItemStack(Material.LEATHER_CHESTPLATE),
				             new ItemStack(Material.LEATHER_LEGGINGS),
				             new ItemStack(Material.LEATHER_BOOTS),
				            };
		return items;
		}
	
	private ItemStack[] getKitAlpha() {
		ItemStack[] items = {new ItemStack(Material.IRON_SWORD),
				             new ItemStack(Material.IRON_PICKAXE),
				             new ItemStack(Material.POTATO,16),
				             new ItemStack(Material.BREAD,12),
				             new ItemStack(Material.MELON_SLICE,16),
				             new ItemStack(Material.CHAINMAIL_HELMET),
				             new ItemStack(Material.CHAINMAIL_CHESTPLATE),
				             new ItemStack(Material.CHAINMAIL_LEGGINGS),
				             new ItemStack(Material.CHAINMAIL_BOOTS),
				            };
		return items;
		}
	
	private ItemStack[] getKitVIP() {
		ItemStack[] items = {new ItemStack(Material.STONE_SWORD),
				             new ItemStack(Material.STONE_PICKAXE),
				             new ItemStack(Material.BREAD,16),
				             new ItemStack(Material.LEATHER_HELMET),
				             new ItemStack(Material.LEATHER_CHESTPLATE),
				             new ItemStack(Material.LEATHER_LEGGINGS),
				             new ItemStack(Material.LEATHER_BOOTS),
				            };
		return items;
		}
	
	private ItemStack[] getKitVIP2() {
		ItemStack[] items = {new ItemStack(Material.STONE_SWORD),
				             new ItemStack(Material.STONE_PICKAXE),
				             new ItemStack(Material.BREAD,16),
				             new ItemStack(Material.LEATHER_HELMET),
				             new ItemStack(Material.LEATHER_CHESTPLATE),
				             new ItemStack(Material.LEATHER_LEGGINGS),
				             new ItemStack(Material.LEATHER_BOOTS),
				            };
		return items;
		}
	
	private ItemStack[] getKitMVP() {
		ItemStack[] items = {new ItemStack(Material.STONE_SWORD),
				             new ItemStack(Material.STONE_PICKAXE),
				             new ItemStack(Material.BREAD,16),
				             new ItemStack(Material.LEATHER_HELMET),
				             new ItemStack(Material.LEATHER_CHESTPLATE),
				             new ItemStack(Material.LEATHER_LEGGINGS),
				             new ItemStack(Material.LEATHER_BOOTS),
				            };
		return items;
		}
	

}
			