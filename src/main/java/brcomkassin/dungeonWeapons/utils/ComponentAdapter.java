package brcomkassin.dungeonWeapons.utils;

import com.google.gson.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import java.lang.reflect.Type;

public class ComponentAdapter implements JsonSerializer<Component>, JsonDeserializer<Component> {
    public static final ComponentAdapter INSTANCE = new ComponentAdapter();

    private ComponentAdapter() {}

    @Override
    public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return GsonComponentSerializer.gson().deserializeFromTree(json);
    }

    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        return GsonComponentSerializer.gson().serializeToTree(src);
    }
}
