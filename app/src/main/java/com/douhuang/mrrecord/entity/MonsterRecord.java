package com.douhuang.mrrecord.entity;

import java.util.Date;

public class MonsterRecord  implements Comparable<MonsterRecord>{

    private String monsterName;
    private int index;
    private String circuit;
    private String monsterRefreshTime;


    public MonsterRecord() {}

    public MonsterRecord(String monsterName, int index,String circuit, String monsterRefreshTime) {
        this.monsterName = monsterName;
        this.index = index;
        this.monsterRefreshTime = monsterRefreshTime;
        this.circuit = circuit;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMonsterRefreshTime() {
        return monsterRefreshTime;
    }

    public void setMonsterRefreshTime(String monsterRefreshTime) {
        this.monsterRefreshTime = monsterRefreshTime;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    @Override
    public int compareTo(MonsterRecord record) {
        Date monsterRefreshTimeDate  = new Date(monsterRefreshTime);
        return monsterRefreshTimeDate.compareTo( new Date(record.getMonsterRefreshTime()));
    }
}
