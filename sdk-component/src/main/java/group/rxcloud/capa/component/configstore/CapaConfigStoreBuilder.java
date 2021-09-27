package group.rxcloud.capa.component.configstore;


import group.rxcloud.capa.infrastructure.config.CapaProperties;
import group.rxcloud.capa.infrastructure.serializer.CapaObjectSerializer;
import group.rxcloud.capa.infrastructure.serializer.DefaultObjectSerializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * A builder for the {@link CapaConfigStore} implementor.
 */
public class CapaConfigStoreBuilder {

    /**
     * Serializer used for request and response objects in CapaClient.
     */
    private CapaObjectSerializer objectSerializer;

    private final StoreConfig storeConfig;

    /**
     * Creates a constructor for CapaConfigStore.
     * <p>
     * {@link DefaultObjectSerializer} is used for object and state serializers by default but is not recommended
     * for production scenarios.
     */
    public CapaConfigStoreBuilder(StoreConfig storeConfig) {
        this.objectSerializer = new DefaultObjectSerializer();
        this.storeConfig = storeConfig;
    }

    /**
     * Sets the serializer for objects to be sent and received from Capa.
     * See {@link DefaultObjectSerializer} as possible serializer for non-production scenarios.
     *
     * @param objectSerializer Serializer for objects to be sent and received from Capa.
     * @return This instance.
     */
    public CapaConfigStoreBuilder withObjectSerializer(CapaObjectSerializer objectSerializer) {
        if (objectSerializer == null) {
            throw new IllegalArgumentException("Object serializer is required");
        }
        if (objectSerializer.getContentType() == null
                || objectSerializer.getContentType().isEmpty()) {
            throw new IllegalArgumentException("Content Type should not be null or empty");
        }
        this.objectSerializer = objectSerializer;
        return this;
    }

    /**
     * Build an instance of the client based on the provided setup.
     *
     * @return an instance of {@link CapaConfigStore}
     * @throws IllegalStateException if any required field is missing
     */
    public CapaConfigStore build() {
        CapaConfigStore capaConfigStore = buildCapaConfigStore();
        capaConfigStore.init(this.storeConfig);
        return capaConfigStore;
    }

    /**
     * Creates an instance of the {@link CapaConfigStore} implementor.
     *
     * @return Instance of {@link CapaConfigStore} implementor
     */
    private CapaConfigStore buildCapaConfigStore() {
        // load spi capa config store impl
        try {
            Properties properties = CapaProperties.COMPONENT_PROPERTIES_SUPPLIER.get();
            String capaConfigStoreClassPath = properties.getProperty(CapaConfigStore.class.getName());
            Class<? extends CapaConfigStore> aClass = (Class<? extends CapaConfigStore>) Class.forName(capaConfigStoreClassPath);
            Constructor<? extends CapaConfigStore> constructor = aClass.getConstructor(CapaObjectSerializer.class);
            Object newInstance = constructor.newInstance(this.objectSerializer);
            return (CapaConfigStore) newInstance;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("No CapaConfigStore Client supported.");
        }
    }
}