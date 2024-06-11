package lab2.fleet.back.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Password {
    private String hash;
    private String salt;
}