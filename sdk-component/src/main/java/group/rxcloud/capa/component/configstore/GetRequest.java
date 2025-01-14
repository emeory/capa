package group.rxcloud.capa.component.configstore;

import java.util.List;
import java.util.Map;

/**
 * GetRequest is the object describing a get configuration request
 */
public class GetRequest {

    /**
     * The application id which
     * Only used for admin, Ignored and reset for normal client
     */
    private String appId;
    /**
     * The group of keys.
     */
    private String group;
    /**
     * The label for keys.
     */
    private String label;
    /**
     * The keys to get.
     */
    private List<String> keys;
    /**
     * The metadata which will be sent to configuration store components.
     */
    private Map<String, String> metadata;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
