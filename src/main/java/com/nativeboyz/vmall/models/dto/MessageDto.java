package com.nativeboyz.vmall.models.dto;

public class MessageDto extends TransactionDto {

    private long timeStamp;

    public MessageDto(String message, long timeStamp) {
        super(message);
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "timeStamp=" + timeStamp +
                ", message='" + message + '\'' +
                '}';
    }
}
