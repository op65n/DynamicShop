package tech.op65n.dynamicshop.commands.actions;

import tech.op65n.dynamicshop.commands.components.CommandFactory;
import tech.op65n.dynamicshop.commands.components.ICmd;
import tech.op65n.dynamicshop.commands.components.ITab;
import tech.op65n.dynamicshop.messages.IMessage;
import tech.op65n.dynamicshop.messages.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class Price extends CommandFactory implements ICmd, ITab {

    @Override
    public String command() {
        return "price";
    }

    @Override
    public String permission() {
        return null;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            IMessage.builder().recipient(sender).message(Message.get().console_cannot_execute).send();
            return;
        }
        Player player = (Player) sender;
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
