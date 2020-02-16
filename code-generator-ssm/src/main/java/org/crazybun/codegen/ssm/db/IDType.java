package org.crazybun.codegen.ssm.db;

import org.crazybun.codegen.ssm.assist.AbstractFinals;

/**
 * {@link AbstractFinals#ENTITY_PATH}
 */
public enum IDType {
    StringUUIDEntity("StringUUIDEntity"), IntAutoIdEntity("IntAutoIdEntity"), LongAutoIdEntity("LongAutoIdEntity"), BaseEntity("BaseEntity"), IntIdEntity("IntIdEntity"), LongIdEntity("LongIdEntity");

    private String className;

    private IDType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
