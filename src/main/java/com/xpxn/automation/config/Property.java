package com.xpxn.automation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Container class that stores a reference to a specific given property.
 */
public class Property {
    private static final Logger logger = LoggerFactory.getLogger(Property.class);

    private String target;
    private Properties properties;
    private String description = null;

    /**
     * Stores the desired property for future reference
     * Properties object defaults to {@code System.getProperties()}
     *
     * @param target Desired property.
     */
    public Property(String target) {
        this.target = target;
        this.properties = System.getProperties();
    }

    /**
     * Stores the desired property for future reference
     * Takes a custom Properties object to pull from
     *
     * @param target     Desired property.
     * @param properties Properties object to use
     */
    public Property(String target, Properties properties) {
        this.target = target;
        if (properties == null)
            properties = new Properties();
        this.properties = properties;
    }

    /**
     * Attach an optional description to the property
     *
     * @param description Given description
     * @return itself
     */
    public Property describe(String description) {
        this.description = description;
        return this;
    }

    /**
     * Getter for property description
     *
     * @return Description of property
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Proxy for {@code Properties.getProperty}
     *
     * @return String
     */
    public String get() {
        return this.properties.getProperty(target);
    }

    /**
     * Proxy for {@code Properties.setProperty}
     *
     * @param value Value to set the property to.
     * @return Property
     */
    public Property set(String value) {
        this.properties.setProperty(target, value);
        return this;
    }

    /**
     * Proxy for {@code Properties.getProperty} with a default value. Helps avoid null/empty issues.
     *
     * @param defaultValue Fallback value when property is null or empty.
     * @return String
     */
    public String get(String defaultValue) {
        String out = this.properties.getProperty(target);
        if (out == null || out.length() == 0) {
            out = defaultValue;
            logger.debug("No value present for the '" + target + "' property. Using given default '" + defaultValue + "'.");
        }
        return out;
    }

    /**
     * Proxy for {@code Properties.getProperty}. Will force a NullPointerException if stored property doesn't exist.
     *
     * @return String
     * @throws NullPointerException Only when value is null
     */
    public String expectNonNull() {
        String val = this.properties.getProperty(target);
        if (val == null)
            throw new NullPointerException("Expected the '" + target + "' property to be present and not null.");
        return val;
    }

    /**
     * Proxy for {@code Properties.getProperty}. Will force a NullPointerException if stored property doesn't exist OR is empty.
     *
     * @return String
     * @throws NullPointerException When value is null or empty
     */
    public String expect() {
        String val = this.properties.getProperty(target);
        if (val == null)
            throw new NullPointerException("Expected the '" + target + "' property to be present and not null.");
        if (val.length() == 0)
            throw new NullPointerException("Expected the '" + target + "' property to be present and not empty.");
        return val;
    }

    /**
     * Makes a new {@code Property} object and implicitly calls {@link Property#expect() expect()}.
     * Will force a StringIndexOutOfBoundsException if value is not one of the provided values.
     * Simulates calling {@code System.getProperty}, but will throw an exception.
     *
     * @param allowedValues Values that are allowed to be used.
     * @return String
     * @throws NullPointerException     When property is null or empty.
     * @throws IllegalArgumentException When value does not match a given option
     */
    public String expect(String... allowedValues) {
        try {
            String given = this.expect();
            for (String check : allowedValues) {
                if (check.equals(given)) return given;
            }
            throw new IllegalArgumentException("The prop '" + target + "' has value '" + given +
                    "' which is not one of the allowed values: " + String.join(", ", allowedValues));
        } catch (NullPointerException e) {
            throw new NullPointerException("The prop '" + target + "' is not present. " +
                    "The property must be one of these values: " + String.join(", ", allowedValues));
        }
    }

    /**
     * Checks if stored property exists and contains some value.
     *
     * @return boolean
     */
    public boolean hasValue() {
        String val = this.properties.getProperty(target);
        return val != null && val.length() > 0;
    }

    /**
     * Checks if stored property exists but is empty.
     *
     * @return boolean
     */
    public boolean isEmpty() {
        String val = this.properties.getProperty(target);
        return val != null && val.length() == 0;
    }

    /**
     * Checks if stored property does not exist.
     *
     * @return boolean
     */
    public boolean isNull() {
        String val = this.properties.getProperty(target);
        return val == null;
    }

    /**
     * Changes the stored target reference.
     *
     * @param target Desired property to reference
     * @return Current {@code Property} instance
     */
    public Property switchTo(String target) {
        this.target = target;
        return this;
    }

    /**
     * Changes the stored properties context.
     *
     * @param props Desired properties to use as context
     * @return Current {@code Property} instance
     */
    public Property switchTo(Properties props) {
        this.properties = props;
        return this;
    }

    /**
     * FluentAPI usage: Creates a new {@code Property} object with the desired target reference.
     *
     * @param target Desired property to reference
     * @return New {@code Property} instance
     */
    public Property using(String target) {
        return new Property(target, this.properties);
    }

    /**
     * FluentAPI usage: Creates a new {@code Property} object with the desired properties context.
     *
     * @param props Desired properties to use as context
     * @return New {@code Property} instance
     */
    public Property using(Properties props) {
        return new Property(this.target, props);
    }

    /**
     * Transforms property value into an integer type
     *
     * @return int
     */
    public int asInt() {
        return Integer.parseInt(expect());
    }

    /**
     * Transforms value into a boolean value
     *
     * @return boolean
     */
    public boolean asBool() {
        return Boolean.parseBoolean(get());
    }

    /**
     * Proxy for object equivalence.
     *
     * @param anyOf Values to compare against
     * @return boolean
     */
    public boolean is(String... anyOf) {
        for (String option : anyOf)
            if (expect().equals(option)) {
                return true;
            }
        return false;
    }

    /**
     * Proxy for object equivalence negation.
     *
     * @param anyOf Values to compare against
     * @return boolean
     */
    public boolean isNot(String... anyOf) {
        for (String option : anyOf)
            if (expect().equals(option)) {
                return false;
            }
        return true;
    }

    public String toString() {
        return get();
    }

    public boolean contains(String string) {
        return expectNonNull().contains(string);
    }
}
