package de.apileipzig;

import de.apileipzig.entity.Entity;

import javax.ws.rs.core.UriBuilder;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.function.Consumer;

/**
 * @author cpieloth
 */
public class EntityUrlSetter implements Consumer<Entity> {
    private final UriBuilder uriBuilder;

    public EntityUrlSetter(UriBuilder uriBuilder) {
        this.uriBuilder = uriBuilder;
    }

    @Override
    public void accept(Entity entity) {
        final URI uri = uriBuilder.clone().path(Integer.toString(entity.getId())).build();
        try {
            entity.setUrl(uri.toURL());
        } catch (MalformedURLException e) {
            entity.setUrl(null);
        }
    }

    @Override
    public Consumer<Entity> andThen(Consumer<? super Entity> consumer) {
        return null;
    }
}
