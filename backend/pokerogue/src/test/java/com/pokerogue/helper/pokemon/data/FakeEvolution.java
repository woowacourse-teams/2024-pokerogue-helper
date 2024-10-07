package com.pokerogue.helper.pokemon.data;


public class FakeEvolution extends Evolution {
    private String from;
    private Integer level;
    private String to;
    private String item;
    private String condition;

    public FakeEvolution(String from, Integer level, String to, String item, String condition) {
        this.from = from;
        this.level = level;
        this.to = to;
        this.item = item;
        this.condition = condition;
    }

    public static FakeEvolution from(String from, String to) {
        return new FakeEvolution(from, 0, to, "", "");
    }

    @Override
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
