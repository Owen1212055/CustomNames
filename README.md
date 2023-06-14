# CustomName
This is a POC plugin that allows plugins to register custom names on top of entities. These entities are fully client side, and 
correctly sync between players.

```java
CustomNameManager customNameManager = JavaPlugin.getPlugin(CustomNamePlugin.class).getCustomNameManager();

CustomName name = this.customNameManager.forEntity(player);
name.setName(MiniMessage.miniMessage().deserialize("<rainbow>%s</rainbow>".formatted(event.getPlayer().getName())));
```

*For more information on the implementation details, see [this gist](https://gist.github.com/Owen1212055/f5d59169d3a6a5c32f0c173d57eb199d).*