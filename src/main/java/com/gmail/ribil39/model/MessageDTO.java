package com.gmail.ribil39.model;

import java.util.Date;
import java.util.Objects;

public class MessageDTO {

    private Long id;

    private String text;

    private Date date;

    private Long secondUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Long secondUserId) {
        this.secondUserId = secondUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO that = (MessageDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(date, that.date) &&
                Objects.equals(secondUserId, that.secondUserId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, text, date, secondUserId);
    }
}
