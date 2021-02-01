package tech.op65n.dynamicshop.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InventoryUtils {

    public static boolean hasSpaceFor(Player player, ItemStack iStack) {
        List<ItemStack> stacks = IntStream.range(0, 36).boxed().map(player.getInventory()::getItem).collect(Collectors.toList());

        int freeInventorySlots = 0;
        int freeFiledCapacity = 0;
        for (ItemStack invIStack : stacks) {
            if (invIStack == null) {
                freeInventorySlots++;
                continue;
            }
            if (invIStack.getType() != iStack.getType()) continue;
            if (invIStack.getType().getMaxStackSize() == invIStack.getAmount()) continue;
            freeFiledCapacity += invIStack.getMaxStackSize() - invIStack.getAmount();
        }

        int totalFreeSpace = (freeInventorySlots * iStack.getMaxStackSize()) + freeFiledCapacity;

        System.out.println(totalFreeSpace);

        return totalFreeSpace >= iStack.getAmount();
    }

    public static void insertIntoInventory(Player player, ItemStack iStack) {
        HashMap<Integer, Integer> freeInventorySpaceMap = new HashMap<>();

        for (int i = 0; i < 36; i++) {
            ItemStack invStack = player.getInventory().getItem(i);
            if (invStack == null) {
                freeInventorySpaceMap.put(i, 0);
                continue;
            }
            if (invStack.getType() != iStack.getType()) continue;
            if (invStack.getAmount() >= invStack.getMaxStackSize()) continue;
            freeInventorySpaceMap.put(i, invStack.getMaxStackSize()-invStack.getAmount());
        }
        int totalInsertedItems = iStack.getAmount();
        for (Map.Entry<Integer, Integer> space : freeInventorySpaceMap.entrySet()) {
            if (totalInsertedItems <= 0) break;
            if (space.getValue() == 0) {
                if (totalInsertedItems > iStack.getMaxStackSize()) {
                    iStack.setAmount(iStack.getMaxStackSize());
                    totalInsertedItems -= iStack.getMaxStackSize();
                    insert(player, space.getKey(), iStack);
                    continue;
                }
                iStack.setAmount(totalInsertedItems);
                totalInsertedItems -= totalInsertedItems;
                insert(player, space.getKey(), iStack);
                continue;
            }
            if (totalInsertedItems > space.getValue()) {
                iStack.setAmount(iStack.getMaxStackSize());
                totalInsertedItems -= space.getValue();
                insert(player, space.getKey(), iStack);
                continue;
            }
            iStack.setAmount(iStack.getMaxStackSize() - space.getValue() + totalInsertedItems);
            totalInsertedItems -= totalInsertedItems;
            insert(player, space.getKey(), iStack);
        }
    }

    private static void insert(Player player, int index, ItemStack stack) {
        ItemStack stack1 = new ItemStack(stack.getType());
        stack1.setAmount(stack.getAmount());
        player.getInventory().setItem(index, stack1);
    }

    public static boolean hasEnoughItems(Player player, ItemStack iStack) {
        List<ItemStack> stacks = IntStream.range(0, 36).boxed().map(player.getInventory()::getItem).collect(Collectors.toList());

        int total = 0;

        for (ItemStack invIStack : stacks) {
            if (invIStack == null) continue;
            if (invIStack.getType() != iStack.getType()) continue;
            total += invIStack.getAmount();
        }

        return total >= iStack.getAmount();
    }

    public static void removeFromInventory(Player player, ItemStack iStack) {
        int toBeRemoved = iStack.getAmount();

        for (int i = 0; i < 36; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack == null) continue;
            if (stack.getType() != iStack.getType()) continue;
            if (stack.getAmount() > toBeRemoved) {
                stack.setAmount(stack.getAmount()-toBeRemoved);
                toBeRemoved = 0;
                player.getOpenInventory().setItem(i, stack);
                continue;
            }
            toBeRemoved -= stack.getAmount();
            player.getInventory().setItem(i, null);
        }
    }



}
