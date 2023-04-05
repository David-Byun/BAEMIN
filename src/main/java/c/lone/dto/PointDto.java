package c.lone.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PointDto {
    private long userId;
    private Date usedDate;
    private String info;
    private int point;
}
