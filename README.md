# Tool-Drops

A fabric mod that allows you to configure loot tables for when an item breaks due to durability.

Example: if you want a wooden hoe to drop 2 sticks when it breaks, create the file "config/tool drops/loot tables/item.minecraft.wooden_hoe.json"

```
{
  "pools": [
    {
      "rolls": 2,
      "entries": [
        {
          "type": "minecraft:item",
          "name": "minecraft:stick"
        }
      ]
    }
  ]
}
```

The config folder will be created when minecraft is first started with this mod installed.

The name of every loot table has to be the Translation key used for the item. See https://minecraft.gamepedia.com/ for vanilla items.

Finally, the content of the file is a normal loot table and can be created at https://misode.github.io/loot-table/
