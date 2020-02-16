package org.crazybun.codegen.ssm.assist;

/**
 * 实体属性
 */
public class Field {
    /**
     * 名称
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
    /**
     * 是否为Calendar
     */
    private boolean calendar;
    /**
     * 不为空
     */
    private boolean notNull;

    public Field(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isCalendar() {
        return calendar;
    }

    public void setCalendar(boolean calendar) {
        this.calendar = calendar;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }
}
