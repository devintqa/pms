package com.psk.pms.model;

public class Permission {
private String label;
private int value;
private boolean selected;
public String getLabel() {
	return label;
}
public void setLabel(String label) {
	this.label = label;
}
public int getValue() {
	return value;
}
public void setValue(int value) {
	this.value = value;
}
public boolean isSelected() {
	return selected;
}
public void setSelected(boolean selected) {
	this.selected = selected;
}

}
