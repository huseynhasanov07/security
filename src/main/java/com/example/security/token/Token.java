package com.example.security.token;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@RedisHash(value = "token", timeToLive = 60)
public class Token implements Serializable {

    @Id
    @Indexed
    private Integer id;
    @Indexed
    private String accessToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(id, token.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
